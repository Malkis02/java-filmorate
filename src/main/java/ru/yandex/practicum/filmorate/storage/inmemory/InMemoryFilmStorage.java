package ru.yandex.practicum.filmorate.storage.inmemory;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.IdValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.db.FilmStorage;

import java.util.*;

@Slf4j
@Repository
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Integer,Film> filmsById = new HashMap<>();
    private int curId = 1;


    @Override
    public Film create(Film film) {
        film.setId(curId);
        filmsById.put(curId,film);
        log.info("Фильму: {}, Присвоен id {}",film.getName(),film.getId());
        curId++;
        return film;
    }

    @Override
    public void delete(int id) {
        if(!filmsById.containsKey(id)){
            log.warn("Фильм с id = {} отсутствует в базе",id);
            throw new IdValidationException("Фильм с id: " + id + " отсутствует в базе");
        }
        filmsById.remove(id);
        log.info("Фильм: {}, Удален",filmsById.get(id).getName());
    }

    @Override
    public Film update(Film film) {
        if(filmsById.containsKey(film.getId())){
            filmsById.put(film.getId(),film);
            return film;
        }else {
            log.warn("Фильм с id = {} отсутствует в базе",film.getId());
            throw new IdValidationException("Фильм с id: " + film.getId() + " отсутствует в базе");
        }
    }

    @Override
    public List<Film> getAllFilms() {
        return new ArrayList<>(filmsById.values());
    }

    @Override
    public Film findFilmById(Integer id){
        if(filmsById.containsKey(id)){
            return filmsById.get(id);
        }else {
            log.warn("Фильм с id = {} отсутствует в базе",id);
            throw new IdValidationException("Фильм с id: " + id + " отсутствует в базе");
        }

    }
}
