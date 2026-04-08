package com.virtualcareerfair.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.virtualcareerfair.dto.AdminLoginRequest;
import com.virtualcareerfair.dto.AuthRequest;
import com.virtualcareerfair.dto.AuthResponse;
import com.virtualcareerfair.dto.LoginRequest;
import com.virtualcareerfair.service.InMemoryStore;

import jakarta.validation.Valid;

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

    @PostMapping("/signup")
    public AuthResponse signup(@Valid @RequestBody AuthRequest request) {
        return store.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {
        return store.login(request);
    }

    @PostMapping("/signin")
    public AuthResponse signin(@Valid @RequestBody LoginRequest request) {
        return store.login(request);
    }

    @PostMapping("/admin-login")
    public AuthResponse adminLogin(@Valid @RequestBody AdminLoginRequest request) {
        return store.adminLogin(request);
    }

    @GetMapping("/me")
    public AuthResponse me(@RequestHeader(value = "Authorization", required = false) String authorizationHeader) {
        return store.authenticate(extractBearerToken(authorizationHeader));
    }

    private String extractBearerToken(String authorizationHeader) {
        if (authorizationHeader == null || authorizationHeader.isBlank()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing Authorization header");
        }

        if (!authorizationHeader.startsWith("Bearer ")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Authorization header");
        }

        return authorizationHeader.substring(7).trim();
    }
}