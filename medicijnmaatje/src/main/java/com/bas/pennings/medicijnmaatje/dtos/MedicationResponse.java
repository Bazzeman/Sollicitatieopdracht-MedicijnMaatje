package com.bas.pennings.medicijnmaatje.dtos;

import com.bas.pennings.medicijnmaatje.entities.Medication;

public record MedicationResponse(
	Long id,
	String name,
	String dosage,
	String frequency
) {
	public static MedicationResponse fromEntity(Medication medication) {
		return new MedicationResponse(
			medication.getId(),
			medication.getName(),
			medication.getDosage(),
			medication.getFrequency()
		);
	}
}