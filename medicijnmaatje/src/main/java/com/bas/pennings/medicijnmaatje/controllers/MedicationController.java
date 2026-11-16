package com.bas.pennings.medicijnmaatje.controllers;

import com.bas.pennings.medicijnmaatje.dtos.MedicationRequest;
import com.bas.pennings.medicijnmaatje.dtos.MedicationResponse;
import com.bas.pennings.medicijnmaatje.entities.Medication;
import com.bas.pennings.medicijnmaatje.services.MedicationService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medications")
public class MedicationController {

	private final MedicationService service;

	public MedicationController(MedicationService service) {
		this.service = service;
	}

	@GetMapping
	public ResponseEntity<List<MedicationResponse>> getAllMedications() {
		List<MedicationResponse> medications = service
			.getAllMedications()
			.stream()
			.map(MedicationResponse::fromEntity)
			.toList();

		return ResponseEntity.ok(medications);
	}

	@GetMapping("/{id}")
	public ResponseEntity<MedicationResponse> getMedicationById(
		@PathVariable Long id) {

		Medication medication = service.getMedicationById(id);

		return ResponseEntity.ok(MedicationResponse.fromEntity(medication));
	}

	@PostMapping
	public ResponseEntity<MedicationResponse> createMedication(
		@Valid @RequestBody MedicationRequest request) {

		Medication medication = service.createMedication(
			request.getName(),
			request.getDosage(),
			request.getFrequency()
		);

		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(MedicationResponse.fromEntity(medication));
	}

	@PutMapping("/{id}")
	public ResponseEntity<MedicationResponse> updateMedication(
		@PathVariable Long id,
		@Valid @RequestBody MedicationRequest request) {

		Medication medication = service.updateMedication(
			id,
			request.getName(),
			request.getDosage(),
			request.getFrequency()
		);

		return ResponseEntity.ok(MedicationResponse.fromEntity(medication));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteMedication(@PathVariable Long id) {
		service.deleteMedication(id);

		return ResponseEntity.noContent().build();
	}
}