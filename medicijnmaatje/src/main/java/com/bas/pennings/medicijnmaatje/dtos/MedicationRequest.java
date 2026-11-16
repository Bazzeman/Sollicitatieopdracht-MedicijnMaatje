package com.bas.pennings.medicijnmaatje.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedicationRequest {

	@NotBlank(message = "Medication name is required.")
	@Size(max = 255, message = "Medication name cannot exceed 255 characters.")
	private String name;

	@NotBlank(message = "Dosage is required.")
	@Pattern(
		regexp = "^(?=.*[1-9])\\d+(?:\\.\\d+)?\\s*(mg|g|ml|l)$",
		flags = Pattern.Flag.CASE_INSENSITIVE,
		message = "Dosage must be a positive amount followed by a supported unit (mg, g, ml or l)."
	)
	private String dosage;

	@NotBlank(message = "Frequency is required.")
	@Pattern(
		regexp = "^[1-9]\\d*\\s+per\\s+(day|week)$",
		flags = Pattern.Flag.CASE_INSENSITIVE,
		message = "Frequency must be a positive number followed by 'per day' or 'per week'."
	)
	private String frequency;
}