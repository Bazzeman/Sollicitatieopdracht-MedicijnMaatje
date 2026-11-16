package com.bas.pennings.medicijnmaatje.repositories;

import com.bas.pennings.medicijnmaatje.entities.Medication;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedicationRepository extends JpaRepository<Medication, Long> {

	Optional<Medication> findByNameIgnoreCase(String name);

	boolean existsByNameIgnoreCase(String name);

	@NonNull Page<Medication> findAll(@NonNull Pageable pageable);
}