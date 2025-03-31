package com.example.racekat.presentation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RacekatController {

    @GetMapping("/login")
    public String showLogin(Model model){
        model.addAttribute("user", new )
    }
}
