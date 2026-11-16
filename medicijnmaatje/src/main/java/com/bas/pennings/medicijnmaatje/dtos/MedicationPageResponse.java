package com.bas.pennings.medicijnmaatje.dtos;

import org.springframework.data.domain.Page;

import java.util.List;

public record MedicationPageResponse(
	List<MedicationResponse> content,
	int page,
	int size,
	long totalElements,
	int totalPages,
	boolean first,
	boolean last
) {
	public static MedicationPageResponse fromPage(Page<MedicationResponse> page) {
		return new MedicationPageResponse(
			page.getContent(),
			page.getNumber(),
			page.getSize(),
			page.getTotalElements(),
			page.getTotalPages(),
			page.isFirst(),
			page.isLast()
		);
	}
}