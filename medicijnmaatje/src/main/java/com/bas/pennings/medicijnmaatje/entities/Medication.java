package com.bas.pennings.medicijnmaatje.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "medications")
public class Medication {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(nullable = false, unique = true, length = 255)
	private String name;

	@Column(nullable = false, length = 100)
	private String dosage;

	@Column(nullable = false, length = 100)
	private String frequency;

	public Medication(String name, String dosage, String usage) {
		this.name = name;
		this.dosage = dosage;
		this.frequency = usage;
	}
}
