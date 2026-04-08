package com.virtualcareerfair.controller;

import com.virtualcareerfair.dto.CareerFair;
import com.virtualcareerfair.service.InMemoryStore;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/fairs")
public class CareerFairController {
    private final InMemoryStore store;

    public CareerFairController(InMemoryStore store) {
        this.store = store;
    }

    @GetMapping
    public List<CareerFair> list() {
        return store.listCareerFairs();
    }

    @GetMapping("/{id}")
    public CareerFair get(@PathVariable String id) {
        return store.getCareerFair(id);
    }

    @PostMapping
    public CareerFair create(@Valid @RequestBody CareerFair fair) {
        return store.saveCareerFair(fair);
    }

    @PutMapping("/{id}")
    public CareerFair update(@PathVariable String id, @Valid @RequestBody CareerFair fair) {
        return store.saveCareerFair(new CareerFair(
                id,
                fair.name(),
                fair.date(),
                fair.time(),
                fair.location(),
                fair.description(),
                fair.companies(),
                fair.status(),
                fair.registrations(),
                fair.category()));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        store.deleteCareerFair(id);
    }
}