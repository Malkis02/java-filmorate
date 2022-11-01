package ru.yandex.practicum.filmorate.storage.inmemory;

import org.springframework.data.relational.core.sql.In;
import ru.yandex.practicum.filmorate.model.MPA;

import java.util.List;

public interface MpaStorage {
    public List<MPA> getAll();
    public MPA findById(Integer id);
}
