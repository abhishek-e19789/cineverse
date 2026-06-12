package com.cinverse.backend.users.controller;

import com.cinverse.backend.users.entity.UserEntity;
import com.cinverse.backend.users.repository.UserRepository;
import com.cinverse.backend.users.service.JWTSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // 🧩 Registration
    @PostMapping("/register")
    public String registerUser(@RequestBody UserEntity user) {
        Optional<UserEntity> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            return "Email already registered!";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return "User registered successfully!";
    }

    // 🔑 Login with JWT
    @PostMapping("/login")
    public String loginUser(@RequestBody UserEntity user) {
        Optional<UserEntity> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent() &&
                passwordEncoder.matches(user.getPassword(), existingUser.get().getPassword())) {
            String token = JWTSecurity.generateToken(user.getEmail());
            return "Login successful! Token: " + token;
        }
        return "Invalid credentials!";
    }

    // 🔄 Password Reset
    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String email, @RequestParam String newPassword) {
        Optional<UserEntity> existingUser = userRepository.findByEmail(email);
        if (existingUser.isEmpty()) {
            return "User not found!";
        }

        UserEntity user = existingUser.get();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        return "Password reset successful!";
    }
}
