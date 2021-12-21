package com.hirekarma.exception;

public class JobException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	public JobException() {
		
	}
	public JobException(String msg) {
		super(msg);
	}
}