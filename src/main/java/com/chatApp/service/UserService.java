package com.chatApp.service;

import com.chatApp.model.User;
import com.chatApp.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(User user) {
        if (userRepository.existsByLogin(user.getLogin())) {
            throw new IllegalArgumentException("Username already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public static boolean isUserLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated();
    }

    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }
    public Optional<User> findUserByLogin(String login){
        return userRepository.findByLogin(login);
    }

    @Transactional
    public User findUserWithChats(String login) {
        User user = userRepository.findByLogin(login).orElseThrow();
        user.getChats().size();
        return user;
    }
}
