package com.chatApp.controller;

import com.chatApp.model.User;
import com.chatApp.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/login")
public class LoginController {
    private final AuthenticationManager authenticationManager;
    public LoginController(UserService userService, AuthenticationManager authenticationManager){
        this.authenticationManager = authenticationManager;
    }
    @GetMapping
    public String showLoginPage(Model model){
        model.addAttribute("user",new User());
        return "login";
    }

    @PostMapping
    public String login(@ModelAttribute User user, Model model) {
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(user.getLogin(), user.getPassword());

        Authentication authentication = authenticationManager.authenticate(authToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "redirect:/chats";
    }
}
