package com.virtualcareerfair.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginRequest {
        @Email
        @NotBlank
        public String email;

        @NotBlank
        public String password;

        @NotBlank
        public String role;

        public LoginRequest() {
        }

        public LoginRequest(String email, String password, String role) {
                this.email = email;
                this.password = password;
                this.role = role;
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