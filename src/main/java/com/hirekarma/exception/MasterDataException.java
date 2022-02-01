package com.hirekarma.exception;

public class MasterDataException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	public MasterDataException() {
		
	}
	public MasterDataException(String msg) {
		super(msg);
	}
}