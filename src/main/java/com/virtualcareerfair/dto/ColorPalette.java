package com.virtualcareerfair.dto;

import jakarta.validation.constraints.NotBlank;

public class ColorPalette {
	@NotBlank
	public String primary;

	@NotBlank
	public String light;

	@NotBlank
	public String border;

	public ColorPalette() {
	}

	public ColorPalette(String primary, String light, String border) {
		this.primary = primary;
		this.light = light;
		this.border = border;
	}

	public String primary() { return primary; }
	public String light() { return light; }
	public String border() { return border; }
}