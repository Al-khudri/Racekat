package com.example.racekat.infrastucture;

import com.example.racekat.domain.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class Userrepo {

    private JdbcTemplate jdbcTemplate;

    public Userrepo (JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public User save (User user) {
        String sql = "INSERT INTO Members (Email, Password, Firstname, Lastname) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getEmail(), user.getPassword(), user.getFirstname(), user.getLastname());
        return user;

    }
    public User findByEmail(String Email) {
        try {
            String sql = "select * from Members where Email= ?";
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), Email);
        } catch (Exception e) {
            return null;
        }


    }
    public List<User> findAll() {
        String sql = "select * from Members";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));

    }
    public void update(User user) {
        String sql = "update Members set Password=?, FirstName=?, Lastname=? where Email=?";
        jdbcTemplate.update(sql, user.getPassword(), user.getFirstname(), user.getLastname(), user.getEmail());
    }

    public void delete(String Email) {
        String sql = "delete from Members where Email=?";
        jdbcTemplate.update(sql,Email);

    }


}
