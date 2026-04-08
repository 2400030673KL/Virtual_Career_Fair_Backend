package com.virtualcareerfair.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String root() {
        return "redirect:http://localhost:5173/";
    }

    @GetMapping("/health")
    public org.springframework.http.ResponseEntity<String> health() {
        return org.springframework.http.ResponseEntity.ok("Backend is running");
    }
}