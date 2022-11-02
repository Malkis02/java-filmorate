package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.db.FilmStorage;
import ru.yandex.practicum.filmorate.storage.db.GenreStorage;
import ru.yandex.practicum.filmorate.storage.db.LikesStorage;
import ru.yandex.practicum.filmorate.storage.db.UserStorage;

import java.util.Collections;
import java.util.List;

@Service
public class FilmService {

    private final FilmStorage filmStorage;
    private final UserStorage userStorage;
    private final LikesStorage likesStorage;
    private final GenreStorage genreStorage;

    public FilmService(@Qualifier("FilmDbStorage") FilmStorage filmStorage,
                       @Qualifier("UserDbStorage") UserStorage userStorage,
                       LikesStorage likesStorage, GenreStorage genreStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
        this.likesStorage = likesStorage;
        this.genreStorage = genreStorage;
    }

    public void addLike(Integer id, Integer userId) {
        final Film film = filmStorage.findFilmById(id);
        final User user = userStorage.findUserById(userId);
        likesStorage.addLike(film.getId(), user.getId());
    }

    public void deleteLike(Integer id, Integer userId) {
        final Film film = filmStorage.findFilmById(id);
        final User user = userStorage.findUserById(userId);
        likesStorage.removeLike(film.getId(), user.getId());
    }

    public List<Film> getPopularFilms(Integer count) {
        final List<Film> films = likesStorage.getPopular(count);
        genreStorage.load(films);
        return films;
    }

    public Film create(Film film) {
        film.setRate(0);
        return filmStorage.create(film);
    }

    public void delete(int id) {
        filmStorage.delete(id);
    }

    public Film update(Film film) {
        final Film savedFilm = filmStorage.findFilmById(film.getId());
        film.setRate(savedFilm.getRate());
        filmStorage.update(film);
        return findFilmById(film.getId());
    }

    public List<Film> getAllFilms() {
        final List<Film> films = filmStorage.getAllFilms();
        genreStorage.load(films);
        return films;
    }

    public Film findFilmById(Integer id) {
        final Film film = filmStorage.findFilmById(id);
        genreStorage.load(Collections.singletonList(film));
        return film;
    }
}
