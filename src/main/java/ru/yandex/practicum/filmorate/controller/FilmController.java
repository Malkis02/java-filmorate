package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.IdValidationException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {


    Map<Integer,Film> filmsById = new HashMap<>();
    private int curId = 1;

    private void setCurId(){
        this.curId++;
    }

    @GetMapping
    public Collection<Film> getAllFilms(){
        return filmsById.values();
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film)throws ValidationException{
        log.info("Получен POST - запрос к /films, переданное значение Film = {}",film);
        if(film.getReleaseDate().isBefore(LocalDate.of(1895,12,28))){
            throw new ValidationException("дата релиза — не раньше 28 декабря 1895 года");
        }
        film.setId(curId);
        filmsById.put(curId,film);
        log.info("Фильму: {}, Присвоен id {}",film.getName(),film.getId());
        setCurId();
        return film;
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film)throws ValidationException{
        log.info("Получен PUT - запрос к /films, переданное значение Film = {}",film);
        if(filmsById.containsKey(film.getId())){
            filmsById.put(film.getId(),film);
            return film;
        }else {
            log.warn("Фильм с id = {} отсутствует в базе",film.getId());
            throw new IdValidationException("Фильм с id: " + film.getId() + "отсутствует в базе");
        }

    }
}
