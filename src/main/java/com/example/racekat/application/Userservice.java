package com.example.racekat.application;


import com.example.racekat.domain.User;
import com.example.racekat.infrastucture.Userrepo;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.List;



@Service
public class Userservice {

    private final Userrepo userrepo;
    private final BCryptPasswordEncoder passwordEncoder;




    public Userservice(Userrepo userrepo) {
        this.userrepo = userrepo;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }




    // Replace only the Login method in your Userservice.java
    public User Login(String email, String password) {
        // First try hardcoded test user
        String testEmail = "test@test.com";
        String testPassword = "123";

        System.out.println("Login attempt with email: " + email);

        if (email.equals(testEmail) && password.equals(testPassword)) {
            System.out.println("Hardcoded user login successful");
            User user = new User();
            user.setEmail(testEmail);
            // Don't set the actual password in the user object for security
            user.setPassword("[PROTECTED]");
            user.setFirstname("Test");
            user.setLastname("User");
            user.setId(1);
            return user;
        }

        // If not the test user, try database login
        try {
            User user = userrepo.findByEmail(email);
            System.out.println("Database lookup result: " + (user != null ? "User found" : "User not found"));

            if (user != null && passwordEncoder.matches(password, user.getPassword())) {
                System.out.println("Database user authentication successful");
                return user;
            } else if (user != null) {
                System.out.println("Password did not match");
            }
        } catch (Exception e) {
            System.err.println("Login failed: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("Login failed");
        return null;
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
        if (!user.getPassword().startsWith("$2a$)")){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userrepo.update(user);
    }

    public void deleteUser(String Email) {
        userrepo.delete(Email);
    }
}





