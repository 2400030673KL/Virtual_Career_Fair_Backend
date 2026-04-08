package com.virtualcareerfair.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class ResumeCreateRequest {
        @NotBlank
        public String name;

        @Email
        @NotBlank
        public String email;

        @NotBlank
        public String position;

        @NotBlank
        public String company;

        @NotBlank
        public String summary;

        @NotBlank
        public String fileName;

        public ResumeCreateRequest() {
        }

        public ResumeCreateRequest(String name, String email, String position, String company, String summary, String fileName) {
                this.name = name;
                this.email = email;
                this.position = position;
                this.company = company;
                this.summary = summary;
                this.fileName = fileName;
        }

        public String name() { return name; }
        public String email() { return email; }
        public String position() { return position; }
        public String company() { return company; }
        public String summary() { return summary; }
        public String fileName() { return fileName; }
}