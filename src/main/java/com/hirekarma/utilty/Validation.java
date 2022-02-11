package com.hirekarma.utilty;

import java.util.regex.Pattern;

public class Validation {

	public static boolean validateEmail(String email)
	{
		if(email == null || email.isBlank() || email.isEmpty() || email.equalsIgnoreCase("null") || email == null)
		{
			return false;
		}
		
		String emailRegex = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" 
		        + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
		
		Pattern pattern = Pattern.compile(emailRegex);
		if(pattern.matcher(email).matches())
		{
			return true;
		}else {
			return false;
		}
	}
	public static boolean validatePassword(String password)
	{
		if(password == null || password.isBlank() || password.isEmpty() || password.equalsIgnoreCase("null") || password == null)
		{
			return false;
		}
		
		String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,20}$";
		
		Pattern pattern = Pattern.compile(passwordRegex);
		if(pattern.matcher(password).matches())
		{
			return true;
		}else {
			return false;
		}
	}
	
	public static boolean phoneNumberValidation(Long phone)
	{
		if(phone == null || String.valueOf(phone).isBlank() || String.valueOf(phone).isEmpty() || String.valueOf(phone).equalsIgnoreCase("null") || phone == 0L)
		{
			return false;
		}
		String ph = String.valueOf(phone);
		String phoneRegex = "^$|[0-9]{10}";
		
		Pattern pattern = Pattern.compile(phoneRegex);
		if(pattern.matcher(ph).matches())
		{
			return true;
		}
//		else if(Pattern.compile("^(\\d{3}[- .]?){2}\\d{4}$").matcher(ph).matches()) {
//			return true;
//		}
		else {
			return false;
		}
	}
	
}
