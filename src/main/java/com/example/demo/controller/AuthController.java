package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // POST /auth/register
    @PostMapping("/register")
    public ResponseEntity<User> register(
            @RequestBody User user) {

        User savedUser = authService.register(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    // POST /auth/login
    @PostMapping("/login")
    public ResponseEntity<User> login(
            @RequestBody Map<String, String> payload) {

        String email = payload.get("email");
        String password = payload.get("password");

        User user = authService.login(email, password);
        return ResponseEntity.ok(user);
    }
}
