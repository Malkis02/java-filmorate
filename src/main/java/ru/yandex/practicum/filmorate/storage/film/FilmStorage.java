package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmStorage {

     Film create(Film film);
     void delete(Film film);
     Film update(Film film);
     Collection<Film> getAllFilms();
     Film findFilmById(Integer id);
}
