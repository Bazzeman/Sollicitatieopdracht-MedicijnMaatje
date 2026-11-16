package com.bas.pennings.medicijnmaatje.services;

import com.bas.pennings.medicijnmaatje.entities.Medication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.bas.pennings.medicijnmaatje.exceptions.DuplicateMedicationException;
import com.bas.pennings.medicijnmaatje.exceptions.MedicationNotFoundException;
import com.bas.pennings.medicijnmaatje.repositories.MedicationRepository;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class MedicationService {

	private final MedicationRepository repository;

	public MedicationService(MedicationRepository repository) {
		this.repository = repository;
	}

	public Medication createMedication(String name, String dosage, String frequency) {
		String normalizedName = name.trim().toLowerCase(Locale.ROOT);

		if (repository.existsByNameIgnoreCase(normalizedName)) {
			throw new DuplicateMedicationException("A medication with this name already exists.");
		}

		Medication medication = new Medication(normalizedName, dosage, frequency);

		return repository.save(medication);
	}

	public Page<Medication> getAllMedications(Pageable pageable) {
		return repository.findAll(pageable);
	}

	public Medication getMedicationById(Long id) {
		return repository.findById(id)
			.orElseThrow(() -> new MedicationNotFoundException(
				"Medication with ID " + id + " was not found."
			));
	}

	public Medication updateMedication(Long id, String name, String dosage, String frequency) {
		String normalizedName = name.trim().toLowerCase(Locale.ROOT);

		repository.findByNameIgnoreCase(normalizedName)
			.filter(m -> !m.getId().equals(id))
			.ifPresent(_ -> {
				throw new DuplicateMedicationException(
					"A medication with this name already exists."
				);
			});

		Medication medication = getMedicationById(id);

		medication.setName(normalizedName);
		medication.setDosage(dosage);
		medication.setFrequency(frequency);

		return repository.save(medication);
	}

	public void deleteMedication(Long id) {
		Medication medication = getMedicationById(id);
		repository.delete(medication);
	}
}