package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FilmService {

private final FilmStorage filmStorage;
private final UserStorage userStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }
    public void addLike(Integer id,Integer userId){
        Film film = filmStorage.findFilmById(id);
        userStorage.findUserById(userId);
        film.addLike(userId);
    }

    public void deleteLike(Integer id,Integer userId){
        Film film = filmStorage.findFilmById(id);
        userStorage.findUserById(userId);
            film.removeLike(userId);
    }

    public Collection<Film> getPopularFilms(Integer count){
            return filmStorage.getAllFilms().stream()
                    .sorted((Comparator.comparing(Film::getRate)))
                    .limit(count)
                    .collect(Collectors.toList());
    }
}
