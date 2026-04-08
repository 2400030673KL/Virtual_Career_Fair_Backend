package com.virtualcareerfair.controller;

import com.virtualcareerfair.dto.AdminLoginRequest;
import com.virtualcareerfair.dto.AuthRequest;
import com.virtualcareerfair.dto.AuthResponse;
import com.virtualcareerfair.dto.LoginRequest;
import com.virtualcareerfair.service.InMemoryStore;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:4173"})
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final InMemoryStore store;

    public AuthController(InMemoryStore store) {
        this.store = store;
    }

    @PostMapping("/register")
    public AuthResponse register(@Valid @RequestBody AuthRequest request) {
        return store.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {
        return store.login(request);
    }

    @PostMapping("/admin-login")
    public AuthResponse adminLogin(@Valid @RequestBody AdminLoginRequest request) {
        return store.adminLogin(request);
    }
}