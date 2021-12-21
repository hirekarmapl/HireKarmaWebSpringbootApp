package com.hirekarma.exception;

public class JobApplyException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	public JobApplyException() {
		
	}
	public JobApplyException(String msg) {
		super(msg);
	}
}