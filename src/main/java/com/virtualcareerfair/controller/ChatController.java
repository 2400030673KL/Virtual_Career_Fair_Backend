package com.virtualcareerfair.controller;

import com.virtualcareerfair.dto.ChatMessage;
import com.virtualcareerfair.dto.ChatMessageRequest;
import com.virtualcareerfair.service.InMemoryStore;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatController {
    private final InMemoryStore store;

    public ChatController(InMemoryStore store) {
        this.store = store;
    }

    @GetMapping("/{boothId}")
    public List<ChatMessage> list(@PathVariable String boothId) {
        return store.listMessages(boothId);
    }

    @PostMapping("/{boothId}")
    public ChatMessage send(@PathVariable String boothId, @Valid @RequestBody ChatMessageRequest request) {
        return store.addMessage(boothId, request);
    }
}