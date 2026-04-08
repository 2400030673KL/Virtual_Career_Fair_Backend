package com.virtualcareerfair.dto;

public class AuthResponse {
	public boolean success;
	public String message;
	public String token;
	public UserSummary user;

	public AuthResponse() {
	}

	public AuthResponse(boolean success, String message, String token, UserSummary user) {
		this.success = success;
		this.message = message;
		this.token = token;
		this.user = user;
	}

	public boolean success() {
		return success;
	}

	public String message() {
		return message;
	}

	public String token() {
		return token;
	}

	public UserSummary user() {
		return user;
	}
}