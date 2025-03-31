package com.example.racekat.infrastucture;

import com.example.racekat.domain.Racekat;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class Racekatrepo {

    private JdbcTemplate jdbcTemplate;

    public Racekatrepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public Racekat save (Racekat racekat) {
        String sql = "insert into * Racekat() values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql,);
        return racekat;

    }
    public Racekat findById(int id) {
        String sql = "select * from Racekat where id=?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Racekat.class), id);

    }
    public List<Racekat> findAll() {
        String sql = "select * from Racekat";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Racekat.class));

    }
    public void update(Racekat racekat) {
        String sql = "update Racekat set name=?, description=? where id=?";
        jdbcTemplate.update(sql,);
    }


    public void delete(int id) {
        String sql = "delete from Racekat where Email=?";
        jdbcTemplate.update(sql,Email);

    }



}
