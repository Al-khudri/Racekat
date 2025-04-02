package com.example.racekat.application;


import com.example.racekat.domain.User;
import com.example.racekat.infrastucture.Userrepo;
import org.springframework.stereotype.Service;
import java.util.List;



@Service
public class Userservice {

    private final Userrepo userrepo;

    public Userservice(Userrepo userrepo) {
        this.userrepo = userrepo;
    }

    public User Login(String email, String password) {
        try {
            User user = userrepo.findByEmail(email);
            if (user.getPassword().equals(password)) {
                return user;
            }
        } catch (Exception e) {

        }

        return null;
    }

    public User createUser(User user) {
        return userrepo.save(user);


    }


    public User getUserByEmail(String Email) {
        return userrepo.findByEmail(Email);
    }

    public List<User> getAllUsers() {
        return userrepo.findAll();

    }

    public void updateUser(User user) {
        userrepo.update(user);
    }

    public void deleteUser(String Email) {
        userrepo.delete(Email);
    }
}





