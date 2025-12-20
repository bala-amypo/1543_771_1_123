package com.example.demo.controller;

import com.example.demo.model.Employee;
import com.example.demo.service.EmployeeAuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final EmployeeAuthService authService;

    public AuthController(EmployeeAuthService authService) {
        this.authService = authService;
    }

    // POST /auth/register
    @PostMapping("/register")
    public ResponseEntity<Employee> register(
            @RequestBody Employee employee) {

        Employee saved = authService.register(employee);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    // POST /auth/login
    @PostMapping("/login")
    public ResponseEntity<Employee> login(
            @RequestBody Map<String, String> payload) {

        Employee employee = authService.login(
                payload.get("email"),
                payload.get("password")
        );

        return ResponseEntity.ok(employee);
    }
}
