package com.example.racekat.domain;

public class Racekat {
<<<<<<< HEAD
<<<<<<< HEAD
    public int getcatId() {
        return catId;
=======
=======
>>>>>>> 75d63842b146b0a72b1669e8540821318a30952e

    public int getId() {
        return id;
    }
    public void setId(int id){
        this.id = id;
>>>>>>> 75d63842b146b0a72b1669e8540821318a30952e
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

    private int catId;
    private String catname;
    private String breed;
    private int age;

    public Racekat(int catId, String catname, String breed, int age) {
        this.catId = catId;
        this.catname = catname;
        this.breed =breed;
        this.age = age;
    }


}
