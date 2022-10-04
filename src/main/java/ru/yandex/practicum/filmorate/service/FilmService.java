package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FilmService {

    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    public void addLike(Integer id, Integer userId) {
        Film film = filmStorage.findFilmById(id);
        userStorage.findUserById(userId);
        film.addLike(userId);
    }

    public void deleteLike(Integer id, Integer userId) {
        Film film = filmStorage.findFilmById(id);
        userStorage.findUserById(userId);
        film.removeLike(userId);
    }

    public List<Film> getPopularFilms(Integer count) {
        return filmStorage.getAllFilms().stream()
                .sorted((Comparator.comparing(Film::getRate)))
                .limit(count)
                .collect(Collectors.toList());
    }

    public Film create(Film film) {
        return filmStorage.create(film);
    }

    public void delete(int id) {
        filmStorage.delete(id);
    }

    public Film update(Film film) {
        return filmStorage.update(film);
    }

    public List<Film> getAllFilms() {
        return filmStorage.getAllFilms();
    }

    public Film findFilmById(Integer id) {
        return filmStorage.findFilmById(id);
    }
}
