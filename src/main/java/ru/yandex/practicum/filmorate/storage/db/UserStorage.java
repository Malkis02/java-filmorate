package ru.yandex.practicum.filmorate.storage.db;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {

    User create(User user);

    void delete(int id);

    User update(User user);

    List<User> getAllUsers();

    User findUserById(Integer id);

}
