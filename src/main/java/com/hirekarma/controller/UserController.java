package com.hirekarma.controller;

import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hirekarma.beans.Response;
import com.hirekarma.service.UserService;

@RestController
@CrossOrigin
@RequestMapping("/hirekarma/account")
public class UserController {

	@Autowired
	UserService userService;

	@PostMapping("/resetPassword")
	public ResponseEntity<Response> resetPassword(@RequestBody Map<String, String> map) {
		try {
			boolean ans = userService.resetPassword(map.get("new"), map.get("old"), map.get("email"));
			return new ResponseEntity(new Response("success", HttpStatus.OK, "password reset succesfull", null, null),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}

	}
	

	@GetMapping("/google")
	public String googleSignin() {
		return "done";
	}
	
	
	@RequestMapping(value="/validate", method = RequestMethod.GET)
	public ResponseEntity<Response> validateToken(@RequestParam("token") String token,@RequestParam("email") String email) {
		try{
			boolean ans = userService.validateToken(token,email);
			return new ResponseEntity(new Response("success", HttpStatus.OK, "", null, null),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}

	}
	
//	(String email) 
	@PostMapping("/reset")
	public ResponseEntity<Response> resetPasswordToken(@RequestBody Map<String, String> map) {
		try {
			boolean ans = userService.resetPasswordToken(map.get("email"));
			return new ResponseEntity(new Response("success", HttpStatus.OK, "", null, null),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}

	}
	
	@PostMapping("/update")
	public ResponseEntity<Response> updatePassword(@RequestBody Map<String, String> map) {
		try {
			boolean ans = userService.updatePassword(map.get("new"),map.get("email"),map.get("token"));
			return new ResponseEntity(new Response("success", HttpStatus.OK, "password changed successfully", null, null),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}

	}
}
