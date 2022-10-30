package ru.yandex.practicum.filmorate.storage.inmemory;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface FriendStorage {
    public void addFriend(Integer userId,Integer friendId);
    public void removeFriend(Integer userId,Integer fiendId);
    public List<User> getFriends(Integer userId);
    public List<User> getCommonFriends(Integer userId,Integer otherId);
}
