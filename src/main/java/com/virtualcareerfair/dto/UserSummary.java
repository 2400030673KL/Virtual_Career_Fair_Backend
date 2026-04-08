package com.virtualcareerfair.dto;

public class UserSummary {
	public String id;
	public String name;
	public String email;
	public String role;

	public UserSummary() {
	}

	public UserSummary(String id, String name, String email, String role) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.role = role;
	}

	public String id() {
		return id;
	}

	public String name() {
		return name;
	}

	public String email() {
		return email;
	}

	public String role() {
		return role;
	}
}