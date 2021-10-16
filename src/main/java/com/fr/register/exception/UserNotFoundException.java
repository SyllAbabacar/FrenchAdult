package com.fr.register.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "User not found")
public class UserNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public UserNotFoundException() {
		super();
	}
	public UserNotFoundException(Long id) {
		// TODO Auto-generated constructor stub
	}
	
}