package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    private void validate(User user){
        if(user.getLogin().contains(" ")){
            throw new ValidationException("логин не может быть пустым и содержать пробелы");
        }
        if(user.getName()==null || user.getName().isBlank()){
            user.setName(user.getLogin());
        }
    }


    @GetMapping
    public Collection<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @PostMapping
    public User create(@Valid @RequestBody User user){
        validate(user);
        return userService.create(user);
    }

    @PutMapping
    public User update(@Valid @RequestBody User user){
        validate(user);
        return userService.update(user);
    }

    @DeleteMapping
    public void deleteUser(@RequestBody int id){
        userService.delete(id);
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
    public User getUserById( @PathVariable Integer id){
        return userService.findUserById(id);
    }
    @GetMapping("{id}/friends")
    public List<User> getUserFriend( @PathVariable Integer id){
        return userService.getAllFriends(id);
    }

    @GetMapping("{id}/friends/common/{otherId}")
    public List<User> getUserCommonFriends( @PathVariable Integer id, @PathVariable Integer otherId){
        return userService.getCommonFriends(id,otherId);
    }
}
