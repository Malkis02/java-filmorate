package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Optional;

public interface FilmStorage {

     Film create(Film film);
     void delete(int id);
     Film update(Film film);
     List<Film> getAllFilms();
     Film findFilmById(Integer id);
}
