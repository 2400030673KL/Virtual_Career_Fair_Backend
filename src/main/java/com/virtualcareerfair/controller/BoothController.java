package com.virtualcareerfair.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.virtualcareerfair.dto.Booth;
import com.virtualcareerfair.service.InMemoryStore;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/booths")
public class BoothController {
    private final InMemoryStore store;

    public BoothController(InMemoryStore store) {
        this.store = store;
    }

    @GetMapping
    public List<Booth> list() {
        return store.listBooths();
    }

    @GetMapping("/{id}")
    public Booth get(@PathVariable String id) {
        return store.getBooth(id);
    }

    @PostMapping
    public Booth create(@Valid @RequestBody Booth booth) {
        return store.saveBooth(booth);
    }

    @PutMapping("/{id}")
    public Booth update(@PathVariable String id, @Valid @RequestBody Booth booth) {
        return store.saveBooth(new Booth(
                id,
                booth.name(),
                booth.industry(),
                booth.logo(),
                booth.tagline(),
                booth.description(),
                booth.openRoles(),
                booth.location(),
                booth.type(),
                booth.rating(),
                booth.employees(),
                booth.positions(),
                booth.perks(),
                booth.color()));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        store.deleteBooth(id);
    }
}