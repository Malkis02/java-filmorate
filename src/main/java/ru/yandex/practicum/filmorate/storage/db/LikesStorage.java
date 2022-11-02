package ru.yandex.practicum.filmorate.storage.db;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface LikesStorage {

    void addLike(Integer filmId, Integer userId);

    void removeLike(Integer filmId, Integer userId);

    List<Film> getPopular(int count);
}
