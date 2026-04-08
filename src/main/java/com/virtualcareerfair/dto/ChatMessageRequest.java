package com.virtualcareerfair.dto;

import jakarta.validation.constraints.NotBlank;

public class ChatMessageRequest {
        @NotBlank
        public String sender;

        @NotBlank
        public String message;

        public ChatMessageRequest() {
        }

        public ChatMessageRequest(String sender, String message) {
                this.sender = sender;
                this.message = message;
        }

        public String sender() { return sender; }
        public String message() { return message; }
}