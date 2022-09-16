package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.IdValidationException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    Map<Integer,User> usersById = new HashMap<>();
    private int curId = 1;

    @GetMapping
    public Collection<User> getAllUsers(){
        return usersById.values();
    }

    @PostMapping
    public User create(@Valid @RequestBody User user){
        log.info("Получен POST - запрос к /users, переданное значение User = {}",user);
        if(user.getLogin().contains(" ")){
            throw new ValidationException("логин не может быть пустым и содержать пробелы");
        }
        if(user.getName()==null || user.getName().isBlank()){
            user.setName(user.getLogin());
        }
        user.setId(curId);
        usersById.put(curId,user);
        log.info("Пользователю: {}, Присвоен id {}",user.getName(),user.getId());
        curId++;
        return user;
    }

    @PutMapping
    public User update(@Valid @RequestBody User user){
        log.info("Получен PUT - запрос к /users, переданное значение User = {}",user);
            if (usersById.containsKey(user.getId())){
                usersById.put(user.getId(), user);
                return user;
            }else {
                log.warn("Пользователь с id = {} отсутствует в базе",user.getId());
                throw new IdValidationException("Пользователь с id: " + user.getId() + "отсутствует в базе");
            }
    }
}
