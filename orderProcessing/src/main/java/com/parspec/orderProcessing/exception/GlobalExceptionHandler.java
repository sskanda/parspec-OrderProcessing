package com.parspec.orderProcessing.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(OrderNotFoundException.class)
	public ResponseEntity<Map<String, Object>> handleOrderNotFoundException(OrderNotFoundException ex) {
		Map<String, Object> response = Map.of("timestamp", LocalDateTime.now(), "status", HttpStatus.NOT_FOUND.value(),
				"error", "Order Not Found", "message", ex.getMessage());
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
		Map<String, Object> response = Map.of("timestamp", LocalDateTime.now(), "status",
				HttpStatus.INTERNAL_SERVER_ERROR.value(), "error", "Internal Server Error", "message",
				"Something went wrong, please try again later.");
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
