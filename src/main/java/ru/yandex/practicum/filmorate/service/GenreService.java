package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.db.GenreStorage;

import java.util.List;

@Service
public class GenreService {
    private final GenreStorage genreStorage;

    public GenreService(GenreStorage genreStorage) {
        this.genreStorage = genreStorage;
    }

    public Genre findGenreById(Integer id) {
        return genreStorage.findById(id);
    }

    public List<Genre> getAllGenres() {
        return genreStorage.getAll();
    }
}
