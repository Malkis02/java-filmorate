package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("mpa")
public class MPAController {
    private final FilmService filmService;

    @GetMapping("{id}")
    public MPA getMPA(@PathVariable Integer id){
        return filmService.findMpaById(id);
    }

    @GetMapping
    public List<MPA> getAll(){
        return filmService.getAllMPA();
    }
}
