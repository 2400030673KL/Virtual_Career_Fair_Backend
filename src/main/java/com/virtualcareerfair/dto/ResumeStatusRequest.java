package com.virtualcareerfair.dto;

import jakarta.validation.constraints.NotBlank;

public class ResumeStatusRequest {
	@NotBlank
	public String status;

	public ResumeStatusRequest() {
	}

	public ResumeStatusRequest(String status) {
		this.status = status;
	}

	public String status() {
		return status;
	}
}