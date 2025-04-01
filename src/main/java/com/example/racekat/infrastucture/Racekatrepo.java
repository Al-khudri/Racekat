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
        String sql = "INSERT INTO Racekat (Catname,  Breed , Age) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql,racekat.getCatName(),racekat.getBreed(),racekat.getAge());
        return racekat;
    }
    public List<Racekat> findAll() {
        String sql = "select * from Racekat";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Racekat.class));

    }
    public void update(Racekat racekat) {
        String sql = "update Racekat set Catname=?, Breed=?, Age=? where id=?";
        jdbcTemplate.update(sql,racekat.getCatName(),racekat.getBreed(),racekat.getAge());
    }


    public void delete(String Email) {
        String sql = "delete from Racekat where Email=?";
        jdbcTemplate.update(sql,Email);

    }



}
