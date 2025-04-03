package com.example.racekat.domain;

public class User {


    public User() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return Email;
    }
    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname( String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    private int id;
    private String Email;
    private String password;
    private String firstname;
    private String lastname;


    public User(int id, String Email, String password, String firstname, String lastname) {
        this.id = id;
        this.Email = Email;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
    }
}
