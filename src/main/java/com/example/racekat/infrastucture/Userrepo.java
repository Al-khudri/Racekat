package com.example.racekat.infrastucture;

import com.example.racekat.domain.User;
import com.example.racekat.domain.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

public class Userrepo {
    private JdbcTemplate jdbcTemplate;

    public Userrepo (JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public User save (User user) {
        String sql = "INSERT INTO Members (Email, Password, Firstname, Lastname) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql,user.getFirstname(),user.getLastname(),user.getEmail(),user.getPassword());
        return user;

    }
    public User findByEmail(String Email) {
        String sql = "select * from Members where Email=?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), Email);

    }
    public List<User> findAll() {
        String sql = "select * from Members";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));

    }
    public void update(User user) {
        String sql = "update Members set Password=?, FirstName=?, Lastname=? where id=?";
        jdbcTemplate.update(sql);
    }


    public void delete(String Email) {
        String sql = "delete from Members where Email=?";
        jdbcTemplate.update(sql,Email);

    }


}
