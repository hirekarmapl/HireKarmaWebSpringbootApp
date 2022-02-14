package com.hirekarma.utilty;

import java.io.IOException;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.Base64;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Pattern;
import java.nio.charset.*;

import org.springframework.web.multipart.MultipartFile;

public class Utility {
	public static byte[] readFile(MultipartFile file) throws IOException {

		byte[] arr = file.getBytes();
		return arr;
	}

	static String getAlphaNumericString(int n) {

		// length is bounded by 256 Character
		byte[] array = new byte[256];
		new Random().nextBytes(array);

		String randomString = new String(array, Charset.forName("UTF-8"));

		// Create a StringBuffer to store the result
		StringBuffer r = new StringBuffer();

		// Append first 20 alphanumeric characters
		// from the generated random String into the result
		for (int k = 0; k < randomString.length(); k++) {

			char ch = randomString.charAt(k);

			if (((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || (ch >= '0' && ch <= '9')) && (n > 0)) {

				r.append(ch);
				n--;
			}
		}

		// return the resultant string
		return r.toString();
	}
	public static String passwordTokenGenerator() {
		return getAlphaNumericString(20);
		
	}
	public static String createSlug(String input) {
		input = input.replaceAll("[^a-zA-Z0-9]", "");
		if (input.length() >= 10) {
			input = input.substring(0, 9);
		}
		input = input.substring(0);
		input += getAlphaNumericString(10);
		return input;
	}
	public static String getEncriptedString(String password) {
		return Base64.getEncoder().encodeToString(password.getBytes());
	}
	
	public  static String getDecoderString(String encriptedPassword) {
		return new String(Base64.getMimeDecoder().decode(encriptedPassword));
	}
}
