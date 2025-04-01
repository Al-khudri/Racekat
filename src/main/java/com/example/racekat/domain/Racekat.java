package com.example.racekat.domain;

public class Racekat {

    public int getId() {
        return id;
    }
    public void setId(int id){
        this.id = id;
    }

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

    private int id;
    private String catname;
    private String breed;
    private int age;

    public Racekat(int id, String catname, String breed, int age) {
        this.id = id;
        this.catname = catname;
        this.breed =breed;
        this.age = age;
    }


}
