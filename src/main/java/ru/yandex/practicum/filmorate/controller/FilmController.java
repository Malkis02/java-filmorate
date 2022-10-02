package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/films")
@Validated
@RequiredArgsConstructor
public class FilmController {

    private final FilmService filmService;

    @GetMapping
    public List<Film> getAllFilms(){
        return filmService.getAllFilms();
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film){
        return filmService.create(film);
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film){
        return filmService.update(film);
    }

    @DeleteMapping
    public void delete(@RequestBody int id){
        filmService.delete(id);
    }

    @GetMapping("{id}")
    public Film findFilmById(@Valid @PathVariable Integer id){
        return filmService.findFilmById(id);
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
    public List<Film> getPopular(@RequestParam(defaultValue = "10")@Positive int count ){
        return filmService.getPopularFilms(count);
    }

}
