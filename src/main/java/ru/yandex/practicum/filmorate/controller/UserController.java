package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    public Collection<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @PostMapping
    public User create(@Valid @RequestBody User user){
        userService.validate(user);
        return userService.create(user);
    }

    @PutMapping
    public User update(@Valid @RequestBody User user){
        userService.validate(user);
        return userService.update(user);
    }

    @DeleteMapping
    public void deleteUser(@RequestBody User user){
        userService.delete(user);
    }

    @PutMapping("{id}/friends/{friendId}")
    public void addFriends(@PathVariable Integer id,@PathVariable Integer friendId){
        userService.addFriends(id,friendId);
    }

    @DeleteMapping("{id}/friends/{friendId}")
    public void deleteFriends(@PathVariable Integer id,@PathVariable Integer friendId){
        userService.deleteFriends(id,friendId);
    }
    @GetMapping("{id}")
    public User getUserById(@Valid @PathVariable Integer id){
        return userService.findUserById(id);
    }
    @GetMapping("{id}/friends")
    public Collection<User> getUserFriend(@Valid @PathVariable Integer id){
        return userService.getAllFriends(id);
    }

    @GetMapping("{id}/friends/common/{otherId}")
    public Collection<User> getUserCommonFriends(@Valid @PathVariable Integer id, @PathVariable Integer otherId){
        return userService.getCommonFriends(id,otherId);
    }
}
