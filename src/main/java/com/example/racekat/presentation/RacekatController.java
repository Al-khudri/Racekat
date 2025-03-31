package com.example.racekat.presentation;

import com.example.racekat.application.RacekatService;
import jakarta.servlet.http.HttpSession;
import com.example.racekat.application.Userservice;
import com.example.racekat.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RacekatController {


    @Autowired
    private Userservice userService;

    @GetMapping("/login")
    public String showLogin(Model model){
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user,HttpSession session, Model model){
        User loggedIn = Userservice.Login(user.getEmail(),user.getPassword());
        if(loggedIn == null){
            session.setAttribute("user", loggedIn);
            return "startskærm";
        } else {
            model.addAttribute("error", "Forkert brugernavn eller adgangskode.");
            return "login";
        }

    }
}
