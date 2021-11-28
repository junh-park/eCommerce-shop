package com.jun.ecommerce.exceptions;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ResourceExceptionHandler {
	@Value(value = "${data.exception.message1}")
	private String message1;

	@ExceptionHandler(value = ResourceNotFoundException.class)
	public ResponseEntity resourceNotFoundException(ResourceNotFoundException resourceNotFoundException) {
		log.error("Requested resource not found");
		return new ResponseEntity<>(message1, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value = ResourceAlreadyExistsException.class)
	public ResponseEntity resourceNotFoundException(ResourceAlreadyExistsException resourceAlreadyExistsException) {
		log.warn("Requested resource already exists");
		return new ResponseEntity<>(HttpStatus.CONFLICT);
	}
}
