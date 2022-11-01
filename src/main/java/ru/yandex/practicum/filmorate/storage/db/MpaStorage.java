package ru.yandex.practicum.filmorate.storage.db;

import ru.yandex.practicum.filmorate.model.MPA;

import java.util.List;

public interface MpaStorage {
    List<MPA> getAll();

    MPA findById(Integer id);
}
