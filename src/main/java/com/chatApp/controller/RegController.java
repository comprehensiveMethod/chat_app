package com.chatApp.controller;

import com.chatApp.model.User;
import com.chatApp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/register")
public class RegController {
    public final UserService userService;
    public RegController(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    public String showRegistrationPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping
    public String registerUser(@ModelAttribute User user, Model model) {
        userService.registerUser(user);
        model.addAttribute("message", "Регистрация успешна");
        return "login";
    }
}
