package ru.yandex.practicum.filmorate.storage.db;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.IdValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Repository
@Slf4j
@Qualifier(value = "UserDbStorage")
public class UserDbStorage implements UserStorage {

    private final JdbcTemplate jdbcTemplate;

    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User create(User user) {
        String sqlQuery = "INSERT INTO USERS(email, login, name, birthday)"
                + "values (?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection ->{
            PreparedStatement stmt = connection.prepareStatement(sqlQuery,new String[]{"id"});
            stmt.setString(1, user.getEmail());
            stmt.setString(2,user.getLogin());
            stmt.setString(3,user.getName());
            final LocalDate birthday = user.getBirthday();
            if(birthday == null){
                stmt.setNull(4, Types.DATE);
            } else {
                stmt.setDate(4,Date.valueOf(birthday));
            }
            return stmt;
        },keyHolder);
        user.setId(Objects.requireNonNull(keyHolder.getKey()).intValue());
        log.info("Пользователю: {}, Присвоен id {}",user.getName(),user.getId());
        return findUserById((Integer) keyHolder.getKey());
    }

    @Override
    public void delete(int id) {
        String sqlQuery = "delete from users where id = ?";
        jdbcTemplate.update(sqlQuery,id);
    }

    @Override
    public User update(User user) {
        final String sqlQuery = "update users set email = ?, login = ?, name = ?, birthday = ? where id = ?";
        jdbcTemplate.update(sqlQuery,user.getEmail(),user.getLogin(),user.getName(),user.getBirthday(),user.getId());
        final String sqlQuery1 = "select * from users where id = ?";
        final List<User> users = jdbcTemplate.query(sqlQuery1,UserDbStorage::makeUser,user.getId());
        if (users.size() != 1){
            log.warn("Пользователь с id = {} отсутствует в базе",user.getId());
            throw new IdValidationException("Пользователь с id: " + user.getId() + " отсутствует в базе");
        }

        return user;
    }

    @Override
    public List<User> getAllUsers() {
        return jdbcTemplate.query("select * from users",UserDbStorage::makeUser);
    }

    @Override
    public User findUserById(Integer id) {
        final String sqlQuery = "select * from users where id = ?";
        final List<User> users = jdbcTemplate.query(sqlQuery,UserDbStorage::makeUser,id);
        if(users.size() != 1){
            log.warn("Пользователь с id = {} отсутствует в базе",id);
            throw new IdValidationException("Пользователь с id: " + id + " отсутствует в базе");
        }
        return users.get(0);
    }

    static User makeUser(ResultSet rs,int rowNum) throws SQLException {
        return new User(
                rs.getInt("id"),
                rs.getString("email"),
                rs.getString("login"),
                rs.getString("name"),
                rs.getDate("birthday").toLocalDate()
        );
    }
}
