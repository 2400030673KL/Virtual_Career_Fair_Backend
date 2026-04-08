package com.virtualcareerfair.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class AuthRequest {
        @NotBlank
        public String name;

        @Email
        @NotBlank
        public String email;

        @NotBlank
        public String password;

        @NotBlank
        public String role;

        public AuthRequest() {
        }

        public AuthRequest(String name, String email, String password, String role) {
                this.name = name;
                this.email = email;
                this.password = password;
                this.role = role;
        }

        public String name() {
                return name;
        }

        public String email() {
                return email;
        }

        public String password() {
                return password;
        }

        public String role() {
                return role;
        }
}