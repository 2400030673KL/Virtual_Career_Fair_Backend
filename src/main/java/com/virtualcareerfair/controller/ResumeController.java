package com.virtualcareerfair.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.virtualcareerfair.dto.ResumeApplication;
import com.virtualcareerfair.dto.ResumeCreateRequest;
import com.virtualcareerfair.dto.ResumeStatusRequest;
import com.virtualcareerfair.service.InMemoryStore;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/resumes")
public class ResumeController {
    private final InMemoryStore store;

    public ResumeController(InMemoryStore store) {
        this.store = store;
    }

    @GetMapping
    public List<ResumeApplication> list(@RequestParam(required = false) String company) {
        if (company == null || company.isBlank()) {
            return store.listResumes();
        }
        return store.listResumes().stream()
                .filter(resume -> resume.company().equalsIgnoreCase(company))
                .toList();
    }

    @PostMapping
    public ResumeApplication create(@Valid @RequestBody ResumeCreateRequest request) {
        return store.saveResume(request);
    }

    @PatchMapping("/{id}/status")
    public ResumeApplication updateStatus(@PathVariable String id, @Valid @RequestBody ResumeStatusRequest request) {
        return store.updateResumeStatus(id, request);
    }
}