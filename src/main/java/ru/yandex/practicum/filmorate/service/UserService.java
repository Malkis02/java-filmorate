package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.inmemory.FriendStorage;
import ru.yandex.practicum.filmorate.storage.inmemory.UserStorage;

import java.util.List;

@Service
public class UserService {
    private final UserStorage userStorage;

    private final FriendStorage friendStorage;

    public UserService(@Qualifier("UserDbStorage") UserStorage userStorage, FriendStorage friendStorage) {
        this.userStorage = userStorage;
        this.friendStorage = friendStorage;
    }

    public void addFriends(Integer userId, Integer friendId){
        final User user = userStorage.findUserById(userId);
        final User friend = userStorage.findUserById(friendId);
        friendStorage.addFriend(user.getId(),friend.getId());
    }

    public void deleteFriends(Integer userId,Integer friendId){
        final User user = userStorage.findUserById(userId);
        final User friend = userStorage.findUserById(friendId);
        friendStorage.removeFriend(user.getId(),friend.getId());
    }

    public List<User> getAllFriends(Integer id){
        final User user = userStorage.findUserById(id);
        return friendStorage.getFriends(user.getId());
    }

    public List<User> getCommonFriends(Integer id,Integer otherId){
        final User user = userStorage.findUserById(id);
        final User other = userStorage.findUserById(otherId);
        return friendStorage.getCommonFriends(user.getId(), other.getId());
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
