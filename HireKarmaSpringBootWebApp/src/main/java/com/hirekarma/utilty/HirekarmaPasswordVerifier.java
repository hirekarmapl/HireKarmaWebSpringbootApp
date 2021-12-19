package com.hirekarma.utilty;

import java.util.Base64;

public class HirekarmaPasswordVerifier {
	
	public String getEncriptedString(String password) {
		return Base64.getEncoder().encodeToString(password.getBytes());
	}
	
	public  String getDecoderString(String encriptedPassword) {
		return new String(Base64.getMimeDecoder().decode(encriptedPassword));
	}
}
