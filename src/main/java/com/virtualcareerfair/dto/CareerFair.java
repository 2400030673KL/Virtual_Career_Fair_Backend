package com.virtualcareerfair.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public class CareerFair {
        public String id;

        @NotBlank
        public String name;

        @NotBlank
        public String date;

        @NotBlank
        public String time;

        @NotBlank
        public String location;

        @NotBlank
        public String description;

        @NotEmpty
        public List<String> companies;

        @NotBlank
        public String status;

        public int registrations;

        @NotBlank
        public String category;

        public CareerFair() {
        }

        public CareerFair(String id, String name, String date, String time, String location, String description, List<String> companies, String status, int registrations, String category) {
                this.id = id;
                this.name = name;
                this.date = date;
                this.time = time;
                this.location = location;
                this.description = description;
                this.companies = companies;
                this.status = status;
                this.registrations = registrations;
                this.category = category;
        }

        public String id() { return id; }
        public String name() { return name; }
        public String date() { return date; }
        public String time() { return time; }
        public String location() { return location; }
        public String description() { return description; }
        public List<String> companies() { return companies; }
        public String status() { return status; }
        public int registrations() { return registrations; }
        public String category() { return category; }
}