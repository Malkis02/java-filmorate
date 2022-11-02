package ru.yandex.practicum.filmorate.storage.db;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface FriendStorage {
    void addFriend(Integer userId, Integer friendId);

    void removeFriend(Integer userId, Integer fiendId);

    List<User> getFriends(Integer userId);

    List<User> getCommonFriends(Integer userId, Integer otherId);
}
