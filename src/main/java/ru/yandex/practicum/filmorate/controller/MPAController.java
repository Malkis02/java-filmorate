package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.service.MPAService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("mpa")
@Slf4j
public class MPAController {
    private final MPAService mpaService;

    @GetMapping("{id}")
    public MPA getMPA(@PathVariable Integer id) {
        log.info("Получен GET - запрос к /mpa, переданное значение Id = {}", id);
        return mpaService.findMpaById(id);
    }

    @GetMapping
    public List<MPA> getAll() {
        log.info("Получен GET - запрос к /mpa");
        return mpaService.getAllMPA();
    }
}
