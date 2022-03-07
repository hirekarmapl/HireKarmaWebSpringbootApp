package com.hirekarma.utilty;

import java.util.Base64;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class Validation {
	public static String validateToken(String token) throws ParseException {

		String[] chunks1 = token.split(" ");
		String[] chunks = chunks1[1].split("\\.");
		Base64.Decoder decoder = Base64.getUrlDecoder();

		String payload = new String(decoder.decode(chunks[1]));
		JSONParser jsonParser = new JSONParser();
		Object obj = jsonParser.parse(payload);

		JSONObject jsonObject = (JSONObject) obj;

		String email = (String) jsonObject.get("sub");

		return email;
	}
	public static boolean isValidMobileNo(String str)  
	{  
	//(0/91): number starts with (0/91)  
	//[7-9]: starting of the number may contain a digit between 0 to 9  
	//[0-9]: then contains digits 0 to 9  
	Pattern ptrn = Pattern.compile("(0/91)?[7-9][0-9]{9}");  
	//the matcher() method creates a matcher that will match the given input against this pattern  
	Matcher match = ptrn.matcher(str);  
	//returns a boolean value  
	return (match.find() && match.group().equals(str));  
	}  
	public static boolean validateEmail(String email) {
		if (email == null || email.isBlank() || email.isEmpty() || email.equalsIgnoreCase("null") || email == null) {
			return false;
		}

		String emailRegex = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
				+ "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

		Pattern pattern = Pattern.compile(emailRegex);
		if (pattern.matcher(email).matches()) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean validatePassword(String password) {
		if (password == null || password.isBlank() || password.isEmpty() || password.equalsIgnoreCase("null")
				|| password == null) {
			return false;
		}

		String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,20}$";

		Pattern pattern = Pattern.compile(passwordRegex);
		if (pattern.matcher(password).matches()) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean phoneNumberValidation(Long phone) {
		if (phone == null || String.valueOf(phone).isBlank() || String.valueOf(phone).isEmpty()
				|| String.valueOf(phone).equalsIgnoreCase("null") || phone == 0L) {
			return false;
		}
		String ph = String.valueOf(phone);
		String phoneRegex = "^$|[0-9]{10}";

		Pattern pattern = Pattern.compile(phoneRegex);
		if (pattern.matcher(ph).matches()) {
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
