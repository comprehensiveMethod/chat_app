package com.chatApp.controller;

import com.chatApp.model.Chat;
import com.chatApp.model.User;
import com.chatApp.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("/chats")
public class ChatController {
    private final UserService userService;
    public ChatController(UserService userService){
        this.userService = userService;
    }
    @GetMapping
    public String showChatsPage(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            String login = ((UserDetails) principal).getUsername();
            User user = userService.findUserWithChats(login);

            List<Chat> chats = user.getChats();

            model.addAttribute("login", login);
            model.addAttribute("chats", chats);

            if (chats.isEmpty()) {
                model.addAttribute("noChatsMessage", "У вас пока нет чатов. Начните новый разговор!");
            }

        } else {
            model.addAttribute("login", "Гость");
            model.addAttribute("chats", List.of()); // Пустой список чатов для гостя
        }
        return "chats";
    }

}
