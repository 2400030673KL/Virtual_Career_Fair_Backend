package com.virtualcareerfair.controller;

import com.virtualcareerfair.dto.Registration;
import com.virtualcareerfair.service.InMemoryStore;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/registrations")
public class RegistrationController {
    private final InMemoryStore store;

    public RegistrationController(InMemoryStore store) {
        this.store = store;
    }

    @GetMapping
    public List<Registration> list() {
        return store.listRegistrations();
    }
}