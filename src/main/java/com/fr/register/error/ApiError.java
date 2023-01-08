package com.fr.register.error;

import java.util.Arrays;
import java.util.List;

import lombok.Data;

@Data
public class ApiError {
	private String status;
	private String message;
	private List<String> errors;

	public ApiError(String status, String message, List<String> errors) {

		super();
		this.status = status;
		this.message = message;
		this.errors = errors;
	}

	public ApiError(String status, String message, String error) {

		super();
		this.status = status;
		this.message = message;
		errors = Arrays.asList(error);
	}

}
