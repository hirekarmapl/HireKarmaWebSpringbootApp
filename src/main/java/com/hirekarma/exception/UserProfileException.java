package com.hirekarma.exception;

public class UserProfileException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	public UserProfileException() {
		
	}
	public UserProfileException(String msg) {
		super(msg);
	}
}
