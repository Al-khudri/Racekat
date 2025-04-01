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


    @GetMapping("/register")
    public String showRegisterForm(Model model){
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user, Model model){
        if (userService(user)){
            return "register:/login";
        }else{
            model.addAttribute("ERROR","Brugernavn findes allerede");
            return "register";

        }
    }

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

    @GetMapping("/startskærm")
    public String showStartForm(HttpSession session, Model model){
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("username", user.getEmail());
        return "welcome";
    }
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/login";
    }


}
