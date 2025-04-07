package com.example.racekat.infrastucture;

import com.example.racekat.domain.Racekat;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.time.LocalDateTime;
import java.util.Map;

@Repository
public class Racekatrepo  {

    private JdbcTemplate jdbcTemplate;

    public Racekatrepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public Racekat save (Racekat racekat) {
        if (racekat.getCreatedAt() == null){
            racekat.setCreatedAt(LocalDateTime.now());
        }

        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate);
        insert.withTableName("Racekat");
        insert.usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("CatName", racekat.getCatName());
        parameters.put("Breed", racekat.getBreed());
        parameters.put("Age", racekat.getAge());
        parameters.put("imageURL", racekat.getImageUrl());
        parameters.put("Description", racekat.getDescription());
        parameters.put("createdAt", racekat.getCreatedAt());
        parameters.put("userId", racekat.getUserId());

        Number key = insert.executeAndReturnKey(parameters);
        racekat.setId(key.intValue());

        return racekat;
    }
    public List<Racekat> findAll() {
        String sql = "SELECT cp.*, CONCAT(m.FirstName, ' ', m.LastName) AS ownerName "
                + "FROM CatPosts cp " + "JOIN Members m ON cp.UserID = m.id "
                + "WHERE cp.UserID = ? " + "ORDER BY cp.CreatedAt DESC";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Racekat.class));

    }

    public List<Racekat> findByUserId(int userId) {
       try{
           String sql = "SELECT cp.*, CONCAT(m.FirstName, ' ', m.LastName) AS ownerName " +
                   "FROM CatPosts cp " +
                   "JOIN Members m ON cp.UserId = m.id " +
                   "WHERE cp.catId = ?";
           return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Racekat.class), userId);
       }catch (Exception e){
           return null;
       }

    }

    public Racekat findById(int catId) {
        try {
            String sql = "SELECT cp.*, CONCAT(m.FirstName, ' ', m.LastName) AS ownerName " +
                    "FROM CatPosts cp " +
                    "JOIN Members m ON cp.UserID = m.id " +
                    "WHERE cp.PostID = ?";
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Racekat.class), catId);
        } catch (Exception e) {
            return null;
        }
    }



    public void update(Racekat racekat) {
        String sql = "update Racekat set Catname=?, Breed=?, Age=?, imageURL=?, Description=?, createdAt=? where id=?";
        jdbcTemplate.update(sql,racekat.getCatName(),racekat.getBreed(),racekat.getAge());
    }


    public void delete(int catId) {
        String sql = "delete from Racekat where CatID=?";
        jdbcTemplate.update(sql,catId);

    }



}
