package com.hirekarma.exception;

public class CampusDriveResponseException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	public CampusDriveResponseException() {
		
	}
	public CampusDriveResponseException(String msg) {
		super(msg);
	}
}