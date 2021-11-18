package com.jun.ecommerce.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;

import lombok.NoArgsConstructor;

@NoArgsConstructor

public class ResourceAlreadyExistsException extends RuntimeException {
	private String message;
	
	public ResourceAlreadyExistsException(String message) {
		super(message);
		this.message = message;
	}
}
