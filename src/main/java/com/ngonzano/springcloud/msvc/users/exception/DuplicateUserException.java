package com.ngonzano.springcloud.msvc.users.exception;

public class DuplicateUserException extends RuntimeException {

	public DuplicateUserException(String message) {
		super(message);
	}
}
