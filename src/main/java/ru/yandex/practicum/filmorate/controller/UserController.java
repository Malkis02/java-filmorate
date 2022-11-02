package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public Collection<User> getAllUsers() {
        log.info("Получен GET - запрос к /users");
        return userService.getAllUsers();
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        log.info("Получен POST - запрос к /users, переданное значение User = {}", user);
        validate(user);
        return userService.create(user);
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        log.info("Получен PUT - запрос к /users, переданное значение User = {}", user);
        validate(user);
        return userService.update(user);
    }

    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable int id) {
        log.info("Получен DELETE - запрос к /users, переданное значение User id = {}", id);
        userService.delete(id);
    }

    @PutMapping("{id}/friends/{friendId}")
    public void addFriends(@PathVariable Integer id, @PathVariable Integer friendId) {
        userService.addFriends(id, friendId);
    }

    @DeleteMapping("{id}/friends/{friendId}")
    public void deleteFriends(@PathVariable Integer id, @PathVariable Integer friendId) {
        userService.deleteFriends(id, friendId);
    }

    @GetMapping("{id}")
    public User getUserById(@PathVariable Integer id) {
        log.info("Получен GET - запрос к /users, переданное значение Id = {}", id);
        return userService.findUserById(id);
    }

    @GetMapping("{id}/friends")
    public List<User> getUserFriend(@PathVariable Integer id) {
        return userService.getAllFriends(id);
    }

    @GetMapping("{id}/friends/common/{otherId}")
    public List<User> getUserCommonFriends(@PathVariable Integer id, @PathVariable Integer otherId) {
        return userService.getCommonFriends(id, otherId);
    }

    private void validate(User user) {
        if (user.getLogin().contains(" ")) {
            throw new ValidationException("логин не может быть пустым и содержать пробелы");
        }
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }
}
