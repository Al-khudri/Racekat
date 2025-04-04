package com.example.racekat.presentation;


import com.example.racekat.application.RacekatService;
import jakarta.servlet.http.HttpSession;
import com.example.racekat.application.Userservice;
import com.example.racekat.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RacekatController {




    private Userservice userService;
    private RacekatService racekatService;

    public RacekatController() {
    }

    public RacekatController(Userservice userService, RacekatService racekatService) {
        this.userService = userService;
        this.racekatService = racekatService;
    }


    @GetMapping("/")
    public String index(Model model){
        return "redirect:/index";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model){
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user, Model model){
        try {
            User existingUser = userService.getUserByEmail(user.getEmail());
            if(existingUser != null){
                model.addAttribute("ERROR","Brugernavn findes allerede");
                return "register";
            }
            userService.createUser(user);
            return "redirect:/login";
        }catch(Exception e){
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
        User loggedInUser = userService.Login(user.getEmail(), user.getPassword());
        if(loggedInUser != null){
            session.setAttribute("loggedInUser", loggedInUser);
            return "redirect:/index";
        }else {
            model.addAttribute("ERROR","Brugernavn findes allerede");
            return "login";
        }
    }


    @GetMapping("/index")
    public String showStartForm(HttpSession session, Model model){
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("username", user.getEmail());
        return "index";
    }
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/login";
    }


}
