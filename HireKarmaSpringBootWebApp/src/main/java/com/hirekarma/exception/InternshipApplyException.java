package com.hirekarma.exception;

public class InternshipApplyException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	public InternshipApplyException() {
		
	}
	public InternshipApplyException(String msg) {
		super(msg);
	}
}