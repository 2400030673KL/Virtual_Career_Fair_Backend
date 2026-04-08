package com.virtualcareerfair.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class ResumeApplication {
        public String id;

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

        @NotBlank
        public String date;

        @NotBlank
        public String status;

        public ResumeApplication() {
        }

        public ResumeApplication(String id, String name, String email, String position, String company, String summary, String fileName, String date, String status) {
                this.id = id;
                this.name = name;
                this.email = email;
                this.position = position;
                this.company = company;
                this.summary = summary;
                this.fileName = fileName;
                this.date = date;
                this.status = status;
        }

        public String id() { return id; }
        public String name() { return name; }
        public String email() { return email; }
        public String position() { return position; }
        public String company() { return company; }
        public String summary() { return summary; }
        public String fileName() { return fileName; }
        public String date() { return date; }
        public String status() { return status; }
}