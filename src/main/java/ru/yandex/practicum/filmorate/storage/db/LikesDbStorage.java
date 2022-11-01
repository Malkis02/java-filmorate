package ru.yandex.practicum.filmorate.storage.db;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

@Component
@Slf4j
public class LikesDbStorage implements LikesStorage {

    private final JdbcTemplate jdbcTemplate;

    public LikesDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addLike(Integer filmId, Integer userId) {
        String sqlQuery = "merge into LIKES (FILM_ID,USER_ID) values (?,?)";
        jdbcTemplate.update(sqlQuery, filmId, userId);
        updateRate(filmId);
    }

    @Override
    public void removeLike(Integer filmId, Integer userId) {
        String sqlQuery = "delete from likes where FILM_ID = ? and USER_ID = ?";
        jdbcTemplate.update(sqlQuery, filmId, userId);
        updateRate(filmId);

    }

    private void updateRate(Integer filmId) {
        String sqlQuery = "update FILMS f set RATE = (select count(l.USER_ID) from LIKES l " +
                "where l.FILM_ID = f.id) where f.id = ?";
        jdbcTemplate.update(sqlQuery, filmId);
    }

    @Override
    public List<Film> getPopular(int count) {
        final String sqlQuery = "select * from films f, mpa m where f.mpa_id = m.id order by rate desc limit ?";
        final List<Film> films = jdbcTemplate.query(sqlQuery, FilmDbStorage::makeFilm, count);
        log.info("Найдено {} самых популярных фильмов: {}", count, films);
        return films;
    }
}
