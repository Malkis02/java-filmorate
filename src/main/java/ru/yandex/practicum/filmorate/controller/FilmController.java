package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;

import javax.validation.Valid;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    private final InMemoryFilmStorage filmStorage;

    private final FilmService filmService;
    @Autowired
    public FilmController(InMemoryFilmStorage filmStorage, FilmService filmService) {
        this.filmStorage = filmStorage;
        this.filmService = filmService;
    }

    @GetMapping
    public Collection<Film> getAllFilms(){
        return filmStorage.getAllFilms();
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film){
        return filmStorage.create(film);
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film){
        return filmStorage.update(film);
    }

    @DeleteMapping
    public void delete(@RequestBody Film film){
        filmStorage.delete(film);
    }

    @GetMapping("{id}")
    public Film findFilmById(@Valid @PathVariable Integer id){
        return filmStorage.findFilmById(id);
    }

    @PutMapping("{id}/like/{userId}")
    public void addLike(@PathVariable Integer id,@PathVariable Integer userId){
        filmService.addLike(id,userId);
    }

    @DeleteMapping("{id}/like/{userId}")
    public void deleteLike(@PathVariable Integer id,@PathVariable Integer userId){
        filmService.deleteLike(id,userId);
    }

    @GetMapping("/popular")
    public Collection<Film> getPopular(@RequestParam(defaultValue = "10")int count ){
        return filmService.getPopularFilms(count);
    }

}
