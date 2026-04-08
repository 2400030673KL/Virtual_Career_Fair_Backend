package com.virtualcareerfair.dto;

public class Registration {
        public String id;
        public String name;
        public String email;
        public String target;
        public String status;
        public String registeredAt;

        public Registration() {
        }

        public Registration(String id, String name, String email, String target, String status, String registeredAt) {
                this.id = id;
                this.name = name;
                this.email = email;
                this.target = target;
                this.status = status;
                this.registeredAt = registeredAt;
        }

        public String id() { return id; }
        public String name() { return name; }
        public String email() { return email; }
        public String target() { return target; }
        public String status() { return status; }
        public String registeredAt() { return registeredAt; }
}