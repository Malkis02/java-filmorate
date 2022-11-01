package ru.yandex.practicum.filmorate.storage.db;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.DataNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class GenreDbStorage implements GenreStorage {
    private final JdbcTemplate jdbcTemplate;

    public GenreDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Genre findById(Integer genreId) {
        final String sqlQuery = "select * from genres where id = ?";
        final List<Genre> films = jdbcTemplate.query(sqlQuery, GenreDbStorage::makeGenre, genreId);
        if (films.size() != 1) {
            throw new DataNotFoundException("Отсутствует жанр с id=" + genreId);
        }
        return films.get(0);
    }

    @Override
    public List<Genre> getAll() {
        return jdbcTemplate.query("select * from genres", GenreDbStorage::makeGenre);
    }

    @Override
    public void load(List<Film> films) {
        String inSql = String.join(",", Collections.nCopies(films.size(), "?"));
        final Map<Integer, Film> filmById = films.stream().collect(Collectors.toMap(Film::getId, Function.identity()));
        final String sqlQuery = "select * from genres g, film_genres fg where fg.genre_id = g.id and fg.film_id " +
                "in (" + inSql + ")";
        jdbcTemplate.query(sqlQuery, (rs) -> {
            final Film film = filmById.get(rs.getInt("film_id"));
            film.addGenre(makeGenre(rs, 0));
        }, films.stream().map(Film::getId).toArray());

    }

    static Genre makeGenre(ResultSet rs, int rowNum) throws SQLException {
        return new Genre(
                rs.getInt("id"),
                rs.getString("name"));
    }
}
