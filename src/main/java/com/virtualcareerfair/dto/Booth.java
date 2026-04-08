package com.virtualcareerfair.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public class Booth {
        public String id;

        @NotBlank
        public String name;

        @NotBlank
        public String industry;

        @NotBlank
        public String logo;

        @NotBlank
        public String tagline;

        @NotBlank
        public String description;

        public int openRoles;

        @NotBlank
        public String location;

        @NotBlank
        public String type;

        public double rating;

        @NotBlank
        public String employees;

        @NotEmpty
        public List<String> positions;

        @NotEmpty
        public List<String> perks;

        public ColorPalette color;

        public Booth() {
        }

        public Booth(String id, String name, String industry, String logo, String tagline, String description, int openRoles, String location, String type, double rating, String employees, List<String> positions, List<String> perks, ColorPalette color) {
                this.id = id;
                this.name = name;
                this.industry = industry;
                this.logo = logo;
                this.tagline = tagline;
                this.description = description;
                this.openRoles = openRoles;
                this.location = location;
                this.type = type;
                this.rating = rating;
                this.employees = employees;
                this.positions = positions;
                this.perks = perks;
                this.color = color;
        }

        public String id() { return id; }
        public String name() { return name; }
        public String industry() { return industry; }
        public String logo() { return logo; }
        public String tagline() { return tagline; }
        public String description() { return description; }
        public int openRoles() { return openRoles; }
        public String location() { return location; }
        public String type() { return type; }
        public double rating() { return rating; }
        public String employees() { return employees; }
        public List<String> positions() { return positions; }
        public List<String> perks() { return perks; }
        public ColorPalette color() { return color; }
}