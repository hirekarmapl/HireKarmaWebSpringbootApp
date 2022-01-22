package com.hirekarma.exception;

public class UniversityException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	public UniversityException() {
		
	}
	public UniversityException(String msg) {
		super(msg);
	}
}
