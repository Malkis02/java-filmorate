package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/films")
@Validated
@RequiredArgsConstructor
@Slf4j
public class FilmController {

    private final FilmService filmService;

    @GetMapping
    public List<Film> getAllFilms() {
        log.info("Получен GET - запрос к /films");
        return filmService.getAllFilms();
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        log.info("Получен POST - запрос к /films, переданное значение Film = {}", film);
        return filmService.create(film);
    }

    @PutMapping
    public Film update(@Valid @RequestBody final Film film) {
        log.info("Получен PUT - запрос к /films, переданное значение Id = {}", film.getId());
        return filmService.update(film);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable int id) {
        log.info("Получен DELETE - запрос к /films, переданное значение Film id = {}", id);
        filmService.delete(id);
    }

    @GetMapping("{id}")
    public Film findFilmById(@PathVariable Integer id) {
        log.info("Получен GET - запрос к /films, переданное значение Id = {}", id);
        return filmService.findFilmById(id);
    }

    @PutMapping("{id}/like/{userId}")
    public void addLike(@PathVariable Integer id, @PathVariable Integer userId) {
        filmService.addLike(id, userId);
    }

    @DeleteMapping("{id}/like/{userId}")
    public void deleteLike(@PathVariable Integer id, @PathVariable Integer userId) {
        filmService.deleteLike(id, userId);
    }

    @GetMapping("/popular")
    public List<Film> getPopular(@RequestParam(defaultValue = "10") @Positive int count) {
        return filmService.getPopularFilms(count);
    }

}
