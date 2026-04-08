package com.virtualcareerfair.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class AdminLoginRequest {
        @Email
        @NotBlank
        public String email;

        @NotBlank
        public String password;

        public AdminLoginRequest() {
        }

        public AdminLoginRequest(String email, String password) {
                this.email = email;
                this.password = password;
        }

        public String email() {
                return email;
        }

        public String password() {
                return password;
        }
}