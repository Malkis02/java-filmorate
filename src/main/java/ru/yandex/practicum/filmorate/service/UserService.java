package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserStorage userStorage;

    public void addFriends(Integer userId, Integer friendId){
        final User user = userStorage.findUserById(userId);
        final User friend = userStorage.findUserById(friendId);
        user.getFriends().add(friendId);
        friend.getFriends().add(userId);
    }

    public void deleteFriends(Integer userId,Integer friendId){
        final User user = userStorage.findUserById(userId);
        final User friend = userStorage.findUserById(friendId);
        user.getFriends().remove(friendId);
        friend.getFriends().remove(userId);
    }

    public List<User> getAllFriends(Integer id){
        return userStorage.findUserById(id).getFriends().stream()
                .map(userStorage::findUserById)
                .collect(Collectors.toList());
    }

    public List<User> getCommonFriends(Integer id,Integer otherId){
        return userStorage.findUserById(id).getFriends().stream()
                .filter(t -> userStorage.findUserById(otherId).getFriends().contains(t))
                .map(userStorage::findUserById)
                .collect(Collectors.toList());
    }

    public User create(User user) {
        return userStorage.create(user);
    }

    public void delete(int id) {
        userStorage.delete(id);
    }

    public User update(User user) {
       return userStorage.update(user);
    }

    public List<User> getAllUsers() {
        return userStorage.getAllUsers();
    }

    public User findUserById(Integer id){
        return userStorage.findUserById(id);
    }
}
