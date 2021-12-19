package com.hirekarma.exception;

public class StudentUserDefindException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	public StudentUserDefindException() {
		
	}
	public StudentUserDefindException(String msg) {
		super(msg);
	}
}