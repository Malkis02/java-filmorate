package ru.yandex.practicum.filmorate.storage.db;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.IdValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.storage.inmemory.FilmStorage;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
@Slf4j
@Qualifier(value = "FilmDbStorage")
public class FilmDbStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;

    public FilmDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Film create(Film film) {
        log.info("Получен POST - запрос к /films, переданное значение Film = {}", film);
        String sqlQuery = "insert into films (name, description, release_date, duration, rate, mpa_id) " +
                "values (?, ?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"id"});
            stmt.setString(1, film.getName());
            stmt.setString(2, film.getDescription());
            stmt.setDate(3, Date.valueOf(film.getReleaseDate()));
            stmt.setLong(4, film.getDuration());
            stmt.setLong(5, film.getRate());
            stmt.setLong(6, film.getMpa().getId());
            return stmt;
        }, keyHolder);
        film.setId(Objects.requireNonNull(keyHolder.getKey()).intValue());
        saveGenres(film);
        log.info("Фильму: {}, Присвоен id {}", film.getName(), film.getId());
        return film;
    }

    private void saveGenres(Film film) {
        final Integer filmId = film.getId();
        jdbcTemplate.update("delete from film_genres where film_id = ?", filmId);
        final Set<Genre> genres = film.getGenres();
        if (genres == null || genres.isEmpty()) {
            return;
        }
        final ArrayList<Genre> genreList = new ArrayList<>(genres);
        jdbcTemplate.batchUpdate(
                "insert into film_genres (film_id, genre_id) values (?, ?)",
                new BatchPreparedStatementSetter() {
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setLong(1, filmId);
                        ps.setLong(2, genreList.get(i).getId());
                    }
                    public int getBatchSize() {
                        return genreList.size();
                    }
                });
    }

    @Override
    public void delete(int id) {
        log.info("Получен DELETE - запрос к /films, переданное значение Film id = {}", id);
        String sqlQuery = "delete from films where id =?";
        jdbcTemplate.update(sqlQuery, id);
        validate(id);
        log.info("Фильм с id: {}, Удален", id);
    }

    @Override
    public Film update(Film film) {
        log.info("Получен PUT - запрос к /films, переданное значение Id = {}", film.getId());
        String sqlQuery = " update films set name = ?, description = ?, release_date = ? " +
                " ,duration = ?,mpa_id = ? where id = ?";
        jdbcTemplate.update(sqlQuery, film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(),
                film.getMpa().getId(), film.getId());
        saveGenres(film);
        return findFilmById(film.getId());
    }

    @Override
    public List<Film> getAllFilms() {
        return jdbcTemplate.query("select * from films f , mpa m  where f.mpa_id = m.id",
                FilmDbStorage::makeFilm);
    }

    @Override
    public Film findFilmById(Integer id) {
        log.info("Получен GET - запрос к /films, переданное значение Id = {}", id);
        final String sqlQuery = "select * from films f, mpa m  where f.mpa_id = m.id and f.id = ?";
        final List<Film> films = jdbcTemplate.query(sqlQuery, FilmDbStorage::makeFilm, id);
        if (films.size() != 1) {
            throw new IdValidationException("Фильм с id: " + id + " отсутствует в базе");
        }
        return films.get(0);
    }

    static Film makeFilm(ResultSet rs, int rowNum) throws SQLException {
        return new Film(
                rs.getInt("id"),
                rs.getString("FILMS.name"),
                rs.getString("description"),
                rs.getDate("release_date").toLocalDate(),
                rs.getInt("duration"),
                rs.getInt("rate"),
                new MPA(rs.getInt("MPA.id"), rs.getString("MPA.name"))
        );
    }

    private void validate(int id) {
        final String sqlQuery = "select * from films f, mpa m  where f.mpa_id = m.id and f.id = ?";
        final List<Film> films = jdbcTemplate.query(sqlQuery, FilmDbStorage::makeFilm, id);
        if (films.size() != 1) {
            throw new IdValidationException("Фильм с id: " + id + " отсутствует в базе");
        }
    }
}
