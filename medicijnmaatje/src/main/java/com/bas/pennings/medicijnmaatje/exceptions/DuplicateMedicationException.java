package com.bas.pennings.medicijnmaatje.exceptions;

public class DuplicateMedicationException extends RuntimeException {
	public DuplicateMedicationException(String message) {
		super(message);
	}
}
