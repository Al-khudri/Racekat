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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;

@Controller
public class RacekatController {

    private final Userservice userService;
    private final RacekatService racekatService;

    public RacekatController(Userservice userService, RacekatService racekatService) {
        this.userService = userService;
        this.racekatService = racekatService;
    }

    @GetMapping("/")
    public String root() {
        return "index";
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user, Model model) {
        try {
            User existingUser = userService.getUserByEmail(user.getEmail());
            if (existingUser != null) {
                model.addAttribute("error", "Brugernavn findes allerede");
                return "register";
            }
            userService.createUser(user);
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("error", "Fejl under registrering: " + e.getMessage());
            return "register";
        }
    }

    @GetMapping("/login")
    public String showLogin(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    // Manual login endpoint - this will bypass Spring Security
    @PostMapping("/login")
    public String login(@ModelAttribute User user, HttpSession session, Model model) {
        System.out.println("Custom login attempt with: " + user.getEmail() + " and password length: " + (user.getPassword() != null ? user.getPassword().length() : 0));

        User loggedInUser = userService.Login(user.getEmail(), user.getPassword());
        System.out.println("Login result: " + (loggedInUser != null ? "Success" : "Failed"));

        if (loggedInUser != null) {
            // Set in session
            session.setAttribute("LoggedInUser", loggedInUser);

            // Create a manual authentication for Spring Security
            Authentication auth = new UsernamePasswordAuthenticationToken(
                    loggedInUser.getEmail(),
                    "[PROTECTED]",
                    Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"))
            );
            SecurityContextHolder.getContext().setAuthentication(auth);

            System.out.println("Custom login successful, redirecting to /loggedin");
            return "redirect:/loggedin";
        } else {
            model.addAttribute("error", "Forkert e-mail eller adgangskode");
            return "login";
        }
    }

    @GetMapping("/loggedin")
    public String showLoggedIn(HttpSession session, Model model) {
        User user = (User) session.getAttribute("LoggedInUser");

        // If not in session, try to get from Spring Security context
        if (user == null) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.isAuthenticated() && !auth.getPrincipal().equals("anonymousUser")) {
                String email = auth.getName();
                user = userService.getUserByEmail(email);

                // If we found user from security context, add to session
                if (user != null) {
                    session.setAttribute("LoggedInUser", user);
                }
            }
        }

        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("email", user.getEmail());
        return "loggedin";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        SecurityContextHolder.clearContext();
        session.invalidate();
        return "redirect:/login";
    }
}