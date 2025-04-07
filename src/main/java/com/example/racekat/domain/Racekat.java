package com.example.racekat.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Racekat {

    public int getId() {
        return catId;
    }

    public void setId(int catId){
        this.catId = catId;}

    public String getCatName(){
        return catname;
    }
    public void setCatName(String catname){
        this.catname = catname;
    }
    public String getBreed(){
        return breed;
    }
    public void setBreed(String breed){
        this.breed = breed;
    }
    public int getAge(){
        return age;
    }
    public void setAge(int age){
        this.age = age;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getOwnerName() {
        return ownerName;
    }
    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    private int catId;
    private String catname;
    private String breed;
    private int age;
    private String imageUrl;
    private String description;
    private LocalDateTime createdAt;
    private int userId;
    private String ownerName;


    public Racekat(){}

    public Racekat(int catId, String catname, String breed, int age, String imageUrl, String description, LocalDateTime createdAt, int userId, String ownerName) {
        this.catId = catId;
        this.catname = catname;
        this.breed =breed;
        this.age = age;
        this.imageUrl = imageUrl;
        this.description = description;
        this.createdAt = LocalDateTime.now();
        this.userId = userId;
        this.ownerName = ownerName;

    }


}
