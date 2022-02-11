package com.hirekarma.exception;

public class UniversityJobShareToStudentException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	public UniversityJobShareToStudentException() {
		
	}
	public UniversityJobShareToStudentException(String msg) {
		super(msg);
	}
}