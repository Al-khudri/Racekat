package com.example.racekat.application;


import com.example.racekat.domain.User;
import com.example.racekat.infrastucture.Userrepo;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.List;



@Service
public class Userservice {

    private final Userrepo userrepo;




    public Userservice(Userrepo userrepo) {
        this.userrepo = userrepo;
    }




    public User Login(String email, String password) {
        // Hardcoded test user credentials
        String testEmail = "test@test.com";
        String testPassword = "123"; // This should be the hashed version if using BCrypt

        // Check if the provided credentials match the hardcoded ones
        if (email.equals(testEmail) && password.equals(testPassword)) {
            // Create a new User object with hardcoded values
            User user = new User();
            user.setEmail(testEmail);
            user.setPassword(testPassword); // You may want to set a hashed password if needed
            user.setFirstname("Test");
            user.setLastname("User ");
            user.setId(1); // Set a test ID

            return user; // Return the hardcoded user
        }

        return null; // Return null if credentials do not match
    }

//    public User Login(String email, String password) {
//        try {
//            User user = userrepo.findByEmail(email);
//            if (user != null) {
//                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//                if (passwordEncoder.matches(password, user.getPassword())) {
//                    return user;
//                }
//            }
//        } catch (Exception e) {
//            System.err.println("login failed" + e.getMessage());
//
//        }
//
//        return null;
//    }

    public User createUser(User user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedpassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedpassword);
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





