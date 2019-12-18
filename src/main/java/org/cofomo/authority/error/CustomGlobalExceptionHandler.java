package org.cofomo.authority.error;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

	// Let Spring BasicErrorController handle the exception, we just override the
	// status code
	@ExceptionHandler(ConsumerNotFoundException.class)
	public ResponseEntity<CustomErrorResponse> customHandleNotFound(Exception ex, WebRequest request) {

		CustomErrorResponse errors = new CustomErrorResponse();
		errors.setTimestamp(LocalDateTime.now());
		errors.setError(ex.getMessage());
		errors.setStatus(HttpStatus.NOT_FOUND.value());

		return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
	}

	// Let Spring BasicErrorController handle the exception, we just override the
	// status code
	@ExceptionHandler(JwtTokenValidationError.class)
	public ResponseEntity<CustomErrorResponse> customValidationFailed(Exception ex, WebRequest request) {

		CustomErrorResponse errors = new CustomErrorResponse();
		errors.setTimestamp(LocalDateTime.now());
		errors.setError(ex.getMessage());
		errors.setStatus(HttpStatus.FORBIDDEN.value());

		return new ResponseEntity<>(errors, HttpStatus.FORBIDDEN);
	}

	// Let Spring BasicErrorController handle the exception, we just override the
	// status code
	@ExceptionHandler(JwtTokenCreationError.class)
	public ResponseEntity<CustomErrorResponse> customCreationFailed(Exception ex, WebRequest request) {

		CustomErrorResponse errors = new CustomErrorResponse();
		errors.setTimestamp(LocalDateTime.now());
		errors.setError(ex.getMessage());
		errors.setStatus(HttpStatus.FORBIDDEN.value());
		ex.printStackTrace();

		return new ResponseEntity<>(errors, HttpStatus.FORBIDDEN);
	}

	// Let Spring BasicErrorController handle the exception, we just override the
	// status code
	@ExceptionHandler(JwtTokenExpiredError.class)
	public ResponseEntity<CustomErrorResponse> customTokenExpired(Exception ex, WebRequest request) {

		CustomErrorResponse errors = new CustomErrorResponse();
		errors.setTimestamp(LocalDateTime.now());
		errors.setError(ex.getMessage());
		errors.setStatus(HttpStatus.FORBIDDEN.value());
		ex.printStackTrace();

		return new ResponseEntity<>(errors, HttpStatus.FORBIDDEN);
	}
}
