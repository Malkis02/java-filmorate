package ru.yandex.practicum.filmorate.storage.db;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.DataNotFoundException;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.storage.inmemory.MpaStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
@Slf4j
public class MpaDbStorage implements MpaStorage {
    private final JdbcTemplate jdbcTemplate;

    public MpaDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<MPA> getAll() {
        return jdbcTemplate.query("select * from mpa",MpaDbStorage::makeMpa);
    }

     static MPA  makeMpa(ResultSet rs,int rowNum) throws SQLException {
        return new MPA(
                rs.getInt("id"),
                rs.getString("name")
        );
    }

    @Override
    public MPA findById(Integer id) {
        log.info("Получен GET - запрос к /mpa, переданное значение Id = {}",id);
        final String sqlQuery = "select * from mpa where id = ?";
        final List<MPA> films = jdbcTemplate.query(sqlQuery,MpaDbStorage::makeMpa,id);
        if(films.size() != 1){
            log.warn("MPA с id = {} отсутствует в базе",id);
            throw new DataNotFoundException("MPA с id = {} отсутствует в базе" + id);
        }
        return films.get(0);
    }
}
