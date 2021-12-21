package com.hirekarma.exception;

public class InternshipException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	public InternshipException() {
		
	}
	public InternshipException(String msg) {
		super(msg);
	}
}