package com.example.racekat.presentation;

import com.example.racekat.application.RacekatService;
import com.example.racekat.domain.Racekat;
import jakarta.servlet.http.HttpSession;
import com.example.racekat.application.Userservice;
import com.example.racekat.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

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

    @GetMapping("/posts")
    public String showAllPosts(Model model) {
        List<Racekat> posts = racekatService.getAllPosts();
        model.addAttribute("posts", posts);
        return "posts";

    }

    @GetMapping("/create")
    public String showCreateForm(Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("LoggedInUser");
        if (loggedInUser != null) {
            return "redirect:/login";
        }
        model.addAttribute("racekat", new Racekat());
        return "posts/create";
    }
    @PostMapping("/create")
    public String createPost(@ModelAttribute Racekat racekat, @RequestParam("imageFile")MultipartFile imageFile, HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        User loggedInUser = (User) session.getAttribute("LoggedInUser");
        if (loggedInUser != null) {
            return "redirect:/login";
        }
        try{
            racekat.setUserId(loggedInUser.getId());
            racekatService.createCat(racekat, imageFile);
            redirectAttributes.addFlashAttribute("message", "Post created successfully");
            return "redirect:/posts";
        }catch(Exception e){
            redirectAttributes.addFlashAttribute("error", "Fejl under posting: " + e.getMessage());
            return "redirect:/posts/create";
        }

    }
    @GetMapping("/my-posts")
    public String showMyPosts(Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("LoggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }

        List<Racekat> posts = RacekatService.getAllUsers(loggedInUser.getId());
        model.addAttribute("posts", posts);
        return "posts/my-posts";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") int postId,
                               Model model,
                               HttpSession session) {
        User loggedInUser = (User) session.getAttribute("LoggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }

        Racekat post = RacekatService.getPostById(postId);
        if (post == null || post.getUserId() != loggedInUser.getId()) {
            return "redirect:/posts";
        }

        model.addAttribute("catPost", post);
        return "posts/edit";
    }

    @PostMapping("/edit/{id}")
    public String updatePost(@PathVariable("id") int postId,
                             @ModelAttribute Racekat racekat,
                             @RequestParam("imageFile") MultipartFile imageFile,
                             HttpSession session,
                             RedirectAttributes redirectAttributes) {
        User loggedInUser = (User) session.getAttribute("LoggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }

        Racekat existingPost = RacekatService.getPostById(postId);
        if (existingPost == null || existingPost.getUserId() != loggedInUser.getId()) {
            return "redirect:/posts";
        }

        try {
            racekat.setPostId(postId);
            racekat.setUserId(loggedInUser.getId());
            if (imageFile.isEmpty() && existingPost.getImageUrl() != null) {
                racekat.setImageUrl(existingPost.getImageUrl());
            }

            RacekatService.updateRacecat(racekat, imageFile);
            redirectAttributes.addFlashAttribute("message", "Cat post updated successfully!");
            return "redirect:/posts/my-posts";
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", "Failed to update post: " + e.getMessage());
            return "redirect:/posts/edit/" + postId;
        }
    }

    @GetMapping("/delete/{id}")
    public String deletePost(@PathVariable("id") int postId,
                             HttpSession session,
                             RedirectAttributes redirectAttributes) {
        User loggedInUser = (User) session.getAttribute("LoggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }

        Racekat post = RacekatService.getPostById(postId);
        if (post == null || post.getUserId() != loggedInUser.getId()) {
            return "redirect:/posts";
        }

        RacekatService.deletePost(postId);
        redirectAttributes.addFlashAttribute("message", "Cat post deleted successfully!");
        return "redirect:/posts/my-posts";
    }

    @GetMapping("/view/{id}")
    public String viewPost(@PathVariable("id") int postId, Model model) {
        Racekat post = RacekatService.getPostById(postId);
        if (post == null) {
            return "redirect:/posts";
        }

        model.addAttribute("post", post);
        return "posts/view";
    }
}

}