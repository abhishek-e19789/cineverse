package com.cinverse.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StarterController {
    @GetMapping("/")
    public String welcomeEndpoint(){
        return "Your Springboot application is now live 🚀";
    }
}
