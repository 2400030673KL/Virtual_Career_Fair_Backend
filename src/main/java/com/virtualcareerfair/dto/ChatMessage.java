package com.virtualcareerfair.dto;

import jakarta.validation.constraints.NotBlank;

public class ChatMessage {
        public String id;

        @NotBlank
        public String boothId;

        @NotBlank
        public String sender;

        @NotBlank
        public String message;

        @NotBlank
        public String timestamp;

        public ChatMessage() {
        }

        public ChatMessage(String id, String boothId, String sender, String message, String timestamp) {
                this.id = id;
                this.boothId = boothId;
                this.sender = sender;
                this.message = message;
                this.timestamp = timestamp;
        }

        public String id() { return id; }
        public String boothId() { return boothId; }
        public String sender() { return sender; }
        public String message() { return message; }
        public String timestamp() { return timestamp; }
}