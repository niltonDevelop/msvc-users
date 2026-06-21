package com.ngonzano.springcloud.msvc.users.exception;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserExceptionHandler {

	private final Logger logger = LoggerFactory.getLogger(UserExceptionHandler.class);

	@ExceptionHandler(DuplicateUserException.class)
	public ResponseEntity<Map<String, String>> handleDuplicateUser(DuplicateUserException ex) {
		return buildErrorResponse(ex.getMessage(), HttpStatus.CONFLICT);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<Map<String, String>> handleDataIntegrity(DataIntegrityViolationException ex) {
		return buildErrorResponse("El username o email ya existe", HttpStatus.CONFLICT);
	}

	@ExceptionHandler(RoleNotFoundException.class)
	public ResponseEntity<Map<String, String>> handleRoleNotFound(RoleNotFoundException ex) {
		return buildErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private ResponseEntity<Map<String, String>> buildErrorResponse(String message, HttpStatus status) {
		return Optional.ofNullable(message)
				.map(logAndBuild(status))
				.orElseGet(() -> ResponseEntity.status(status).build());
	}

	private Function<String, ResponseEntity<Map<String, String>>> logAndBuild(HttpStatus status) {
		return message -> {
			logger.error(message);
			return ResponseEntity.status(status)
					.body(Collections.singletonMap("error", message));
		};
	}

}
