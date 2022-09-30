package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final InMemoryUserStorage userStorage;
    @Autowired
    public UserService(InMemoryUserStorage userStorage) {
        this.userStorage = userStorage;
    }
     public void validate(User user){
         if(user.getLogin().contains(" ")){
             throw new ValidationException("логин не может быть пустым и содержать пробелы");
         }
         if(user.getName()==null || user.getName().isBlank()){
             user.setName(user.getLogin());
         }
     }

    public void addFriends(Integer userId, Integer friendId){
        final User user = userStorage.findUserById(userId);
        final User friend = userStorage.findUserById(friendId);
        user.getFriends().add(friendId);
        friend.getFriends().add(userId);
    }

    public void deleteFriends(Integer userId,Integer friendId){
        final User user = userStorage.findUserById(userId);
        final User friend = userStorage.findUserById(friendId);
        userStorage.findUserById(friendId);
        user.getFriends().remove(friendId);
        friend.getFriends().remove(userId);
    }

    public Collection<User> getAllFriends(Integer id){
        return userStorage.findUserById(id).getFriends().stream()
                .map(t -> userStorage.getUsersById().get(t))
                .collect(Collectors.toList());
    }

    public Collection<User> getCommonFriends(Integer id,Integer otherId){
        return userStorage.findUserById(id).getFriends().stream()
                .filter(t -> userStorage.findUserById(otherId).getFriends().contains(t))
                .map(t -> userStorage.getUsersById().get(t))
                .collect(Collectors.toList());
    }
}
