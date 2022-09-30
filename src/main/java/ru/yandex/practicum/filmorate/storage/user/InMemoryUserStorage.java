package ru.yandex.practicum.filmorate.storage.user;

import lombok.Getter;
import lombok.extern.apachecommons.CommonsLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.IdValidationException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@Getter
public class InMemoryUserStorage implements UserStorage {

    private final Map<Integer,User> usersById = new HashMap<>();
    private int curId = 1;

    @Override
    public User create(User user) {
        log.info("Получен POST - запрос к /users, переданное значение User = {}",user);
        user.setId(curId);
        usersById.put(curId,user);
        log.info("Пользователю: {}, Присвоен id {}",user.getName(),user.getId());
        curId++;
        return user;
    }

    @Override
    public void delete(User user) {
        log.info("Получен DELETE - запрос к /users, переданное значение User = {}",user);
        if(!usersById.containsKey(user.getId())){
            log.warn("Пользователь с id = {} отсутствует в базе",user.getId());
            throw new IdValidationException("Пользователь с id: " + user.getId() + " отсутствует в базе");
        }
        usersById.remove(user.getId());
        log.info("Фильм: {}, Удален",user.getName());
    }

    @Override
    public User update(User user) {
        log.info("Получен PUT - запрос к /users, переданное значение User = {}",user);
        if (usersById.containsKey(user.getId())){
            usersById.put(user.getId(), user);
            return user;
        }else {
            log.warn("Пользователь с id = {} отсутствует в базе",user.getId());
            throw new IdValidationException("Пользователь с id: " + user.getId() + " отсутствует в базе");
        }
    }

    @Override
    public Collection<User> getAllUsers() {
        return usersById.values();
    }

    public User findUserById(Integer id){
        log.info("Получен GET - запрос к /users, переданное значение Id = {}",id);
        if (usersById.containsKey(id)){
            return usersById.get(id);
        }else {
            log.warn("Пользователь с id = {} отсутствует в базе",id);
            throw new IdValidationException("Пользователь с id: " + id + " отсутствует в базе");
        }

    }
}
