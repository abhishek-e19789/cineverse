package com.cinverse.backend.users.controller;
import com.cinverse.backend.users.entity.UserEntity;
import com.cinverse.backend.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    // Create a new user
    @PostMapping
    public UserEntity createUser(@RequestBody UserEntity user) {
        return userRepository.save(user);
    }

    // Get all users
    @GetMapping
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    // Find a user by email
    @GetMapping("/email/{email}")
    public UserEntity getUserByEmail(@PathVariable String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
