package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.storage.db.MpaStorage;

import java.util.List;

@Service
public class MPAService {
    private final MpaStorage mpaStorage;

    public MPAService(MpaStorage mpaStorage) {
        this.mpaStorage = mpaStorage;
    }

    public MPA findMpaById(Integer id) {
        return mpaStorage.findById(id);
    }

    public List<MPA> getAllMPA() {
        return mpaStorage.getAll();
    }
}
