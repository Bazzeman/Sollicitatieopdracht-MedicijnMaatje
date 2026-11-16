package com.bas.pennings.medicijnmaatje.exceptions;

public class MedicationNotFoundException extends RuntimeException {
	public MedicationNotFoundException(String message) {
		super(message);
	}
}
