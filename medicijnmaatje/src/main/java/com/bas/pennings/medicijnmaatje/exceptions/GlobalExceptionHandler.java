package com.bas.pennings.medicijnmaatje.exceptions;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Centralized exception handler for the application's REST controllers.
 *
 * <p>Using {@link RestControllerAdvice}, exceptions thrown by any REST
 * controller can be handled here instead of requiring each controller
 * to implement its own exception handling.</p>
 *
 * <p>Each handler translates a specific exception into an appropriate
 * HTTP response, allowing the API to return consistent error responses.</p>
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * Handles cases where a requested medication cannot be found.
	 *
	 * @param ex the exception containing the error message
	 * @return HTTP 404 Not Found with the corresponding error message
	 */
	@ExceptionHandler(MedicationNotFoundException.class)
	public ResponseEntity<Map<String, String>> handleNotFound(MedicationNotFoundException ex) {
		return ResponseEntity
			.status(HttpStatus.NOT_FOUND)
			.body(Map.of("error", ex.getMessage()));
	}

	/**
	 * Handles attempts to create or rename a medication to a name
	 * that is already in use.
	 *
	 * @param ex the exception containing the error message
	 * @return HTTP 409 Conflict with the corresponding error message
	 */
	@ExceptionHandler(DuplicateMedicationException.class)
	public ResponseEntity<Map<String, String>> handleDuplicate(DuplicateMedicationException ex) {
		return ResponseEntity
			.status(HttpStatus.CONFLICT)
			.body(Map.of("error", ex.getMessage()));
	}

	/**
	 * Handles validation errors raised when an incoming request
	 * does not satisfy the DTO's validation constraints.
	 *
	 * <p>Collects the validation errors by field name and returns
	 * them in the response body.</p>
	 *
	 * @param ex the exception containing the validation errors
	 * @return HTTP 400 Bad Request with the validation error details
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
		Map<String, String> errors = ex.getBindingResult()
			.getFieldErrors()
			.stream()
			.collect(Collectors.toMap(
				FieldError::getField,
				DefaultMessageSourceResolvable::getDefaultMessage,
				(first, _) -> first // Keep the first message if multiple errors with the same map key are produced.
			));

		return ResponseEntity
			.badRequest()
			.body(Map.of(
				"error", "Validation failed.",
				"details", errors
			));
	}

	/**
	 * Handles database constraint violations that were not caught
	 * by application-level validation.
	 *
	 * <p>This can occur when database constraints are violated,
	 * for example when two simultaneous requests attempt to create
	 * medications with the same name.</p>
	 *
	 * @param ex the exception containing information about the database violation
	 * @return HTTP 409 Conflict with a generic error message
	 */
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<Map<String, String>> handleDatabaseConflict(DataIntegrityViolationException ex) {
		return ResponseEntity
			.status(HttpStatus.CONFLICT)
			.body(Map.of(
				"error", "The requested operation violates a database constraint."
			));
	}
}