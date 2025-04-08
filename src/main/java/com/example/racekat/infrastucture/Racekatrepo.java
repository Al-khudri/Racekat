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
public class Racekatrepo {

    private JdbcTemplate jdbcTemplate;

    public Racekatrepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Racekat save(Racekat racekat) {
        if (racekat.getCreatedAt() == null) {
            racekat.setCreatedAt(LocalDateTime.now());
        }

        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate);
        insert.withTableName("CatPosts");
        insert.usingGeneratedKeyColumns("PostID");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("CatName", racekat.getCatName());
        parameters.put("Breed", racekat.getBreed());
        parameters.put("Age", racekat.getAge());
        parameters.put("ImageURL", racekat.getImageUrl());
        parameters.put("Description", racekat.getDescription());
        parameters.put("CreatedAt", racekat.getCreatedAt());
        parameters.put("UserID", racekat.getUserId());

        Number key = insert.executeAndReturnKey(parameters);
        racekat.setId(key.intValue());

        return racekat;
    }

    public List<Racekat> findAll() {
        String sql = "SELECT cp.PostID as catId, cp.CatName as catname, cp.Breed as breed, " +
                "cp.Age as age, cp.ImageURL as imageUrl, cp.Description as description, " +
                "cp.CreatedAt as createdAt, cp.UserID as userId, " +
                "CONCAT(m.FirstName, ' ', m.LastName) AS ownerName " +
                "FROM CatPosts cp " +
                "JOIN Members m ON cp.UserID = m.id " +
                "ORDER BY cp.CreatedAt DESC";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Racekat.class));
    }

    public List<Racekat> findByUserId(int userId) {
        try {
            String sql = "SELECT cp.PostID as catId, cp.CatName as catname, cp.Breed as breed, " +
                    "cp.Age as age, cp.ImageURL as imageUrl, cp.Description as description, " +
                    "cp.CreatedAt as createdAt, cp.UserID as userId, " +
                    "CONCAT(m.FirstName, ' ', m.LastName) AS ownerName " +
                    "FROM CatPosts cp " +
                    "JOIN Members m ON cp.UserID = m.id " +
                    "WHERE cp.UserID = ?";
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Racekat.class), userId);
        } catch (Exception e) {
            System.err.println("Error finding posts by user ID: " + e.getMessage());
            return null;
        }
    }

    public Racekat findById(int catId) {
        try {
            String sql = "SELECT cp.PostID as catId, cp.CatName as catname, cp.Breed as breed, " +
                    "cp.Age as age, cp.ImageURL as imageUrl, cp.Description as description, " +
                    "cp.CreatedAt as createdAt, cp.UserID as userId, " +
                    "CONCAT(m.FirstName, ' ', m.LastName) AS ownerName " +
                    "FROM CatPosts cp " +
                    "JOIN Members m ON cp.UserID = m.id " +
                    "WHERE cp.PostID = ?";
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Racekat.class), catId);
        } catch (Exception e) {
            System.err.println("Error finding post by ID: " + e.getMessage());
            return null;
        }
    }

    public void update(Racekat racekat) {
        String sql = "UPDATE CatPosts SET CatName=?, Breed=?, Age=?, ImageURL=?, Description=? WHERE PostID=?";
        jdbcTemplate.update(sql,
                racekat.getCatName(),
                racekat.getBreed(),
                racekat.getAge(),
                racekat.getImageUrl(),
                racekat.getDescription(),
                racekat.getId());
    }

    public void delete(int catId) {
        String sql = "DELETE FROM CatPosts WHERE PostID=?";
        jdbcTemplate.update(sql, catId);
    }
}