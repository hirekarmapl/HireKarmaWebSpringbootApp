package com.hirekarma.exception;

public class OrganizationUserDefindException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	public OrganizationUserDefindException() {
		
	}
	public OrganizationUserDefindException(String msg) {
		super(msg);
	}
}