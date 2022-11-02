package ru.yandex.practicum.filmorate.storage.db;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

@Component
public class FriendsDbStorage implements FriendStorage {
    private final JdbcTemplate jdbcTemplate;

    public FriendsDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addFriend(Integer userId, Integer friendId) {
        String sqlQuery = "merge into FRIENDS (USER_ID,FRIEND_ID) values (?,?)";
        jdbcTemplate.update(sqlQuery, userId, friendId);
    }

    @Override
    public void removeFriend(Integer userId, Integer fiendId) {
        String sqlQuery = "delete from FRIENDS where USER_ID = ? and FRIEND_ID = ?";
        jdbcTemplate.update(sqlQuery, userId, fiendId);
    }

    @Override
    public List<User> getFriends(Integer userId) {
        final String sqlQuery = "select* from USERS,FRIENDS where USERS.id = FRIENDS.FRIEND_ID and FRIENDS.user_id = ?";
        return jdbcTemplate.query(sqlQuery, UserDbStorage::makeUser, userId);
    }

    @Override
    public List<User> getCommonFriends(Integer userId, Integer otherId) {
        final String sqlQuery = "select * from USERS u, FRIENDS f, FRIENDS o " +
                "where u.id = f.FRIEND_ID and u.id = o.FRIEND_ID and f.USER_ID = ? and o.USER_ID = ?";
        return jdbcTemplate.query(sqlQuery, UserDbStorage::makeUser, userId, otherId);
    }
}
