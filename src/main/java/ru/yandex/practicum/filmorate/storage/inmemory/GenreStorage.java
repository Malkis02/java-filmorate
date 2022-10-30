package ru.yandex.practicum.filmorate.storage.inmemory;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

public interface GenreStorage {

    Genre findById(Integer genreId);
    List<Genre> getAll();
    void load(List<Film> films);
}
