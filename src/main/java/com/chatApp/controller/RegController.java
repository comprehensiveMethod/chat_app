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

    @GetMapping("/register")
    public String showRegistrationPage() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model) {
        userService.registerUser(user);
        model.addAttribute("message", "Регистрация успешна");
        return "login";
    }
}
