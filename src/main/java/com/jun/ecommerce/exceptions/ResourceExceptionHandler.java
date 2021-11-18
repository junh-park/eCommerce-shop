package com.jun.ecommerce.exceptions;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ResourceExceptionHandler {
	@Value(value = "${data.exception.message1}")
	private String message1;

	@ExceptionHandler(value = ResourceNotFoundException.class)
	public ResponseEntity resourceNotFoundException(ResourceNotFoundException resourceNotFoundException) {
		return new ResponseEntity<>(message1, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value = ResourceAlreadyExistsException.class)
	public ResponseEntity resourceNotFoundException(ResourceAlreadyExistsException resourceAlreadyExistsException) {
		return new ResponseEntity<>(HttpStatus.CONFLICT);
	}
}
