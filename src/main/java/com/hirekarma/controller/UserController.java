package com.hirekarma.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.security.sasl.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ResolvableType;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.model.PutObjectResult;
import com.hirekarma.beans.Response;
import com.hirekarma.model.Demo;
import com.hirekarma.model.UserProfile;
import com.hirekarma.repository.DemoRepository;
import com.hirekarma.service.UserService;
import com.hirekarma.serviceimpl.AWSS3Service;
import com.hirekarma.utilty.Utility;

@RestController
@CrossOrigin
@RequestMapping("/hirekarma/account")
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	AWSS3Service awss3Service;

	@Autowired
	DemoRepository demoRepository;

	@Autowired
	private OAuth2AuthorizedClientService authorizedClientService;

	private static String authorizationRequestBaseUri = "oauth2/authorization";
	Map<String, String> oauth2AuthenticationUrls = new HashMap<String, String>();

	@Autowired
	private ClientRegistrationRepository clientRegistrationRepository;

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

	

	@RequestMapping(value = "/verify", method = RequestMethod.GET)
	public ResponseEntity<Response> verifyEmail(@RequestParam("token") String token,
			@RequestParam("email") String email) {
		System.out.println("insdie verify email");
		try {
			boolean ans = userService.verifyEmailAddress(token, email);
			return new ResponseEntity(new Response("success", HttpStatus.OK, "", null, null), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}

	}

	@RequestMapping(value = "/validate", method = RequestMethod.GET)
	public ResponseEntity<Response> validateToken(@RequestParam("token") String token,
			@RequestParam("email") String email) {
		try {
			boolean ans = userService.validateToken(token, email);
			return new ResponseEntity(new Response("success", HttpStatus.OK, "", null, null), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}

	}

//	(String email) 
	@PostMapping("/reset")
	public ResponseEntity<Response> resetPasswordToken(@RequestBody Map<String, String> map) {
		try {
			System.out.println("inside reset");
			boolean ans = userService.resetPasswordToken(map.get("email"));
			return new ResponseEntity(new Response("success", HttpStatus.OK, "", null, null), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}

	}

	@PostMapping("/update")
	public ResponseEntity<Response> updatePassword(@RequestBody Map<String, String> map) {
		try {
			boolean ans = userService.updatePassword(map.get("new"), map.get("email"), map.get("token"));
			return new ResponseEntity(
					new Response("success", HttpStatus.OK, "password changed successfully", null, null), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}

	}

	@PutMapping("/profile/about")
	public ResponseEntity<Response> updateAbout(@RequestBody Map<String, String> map,
			@RequestHeader("Authorization") String token) {
		try {
			String about = this.userService.updateAbout(map.get("about"), token);
			return new ResponseEntity(new Response("success", HttpStatus.OK, "", about, null), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}

	}

	@GetMapping("/profile/about")
	public ResponseEntity<Response> updateAbout(@RequestHeader("Authorization") String token) {
		try {
			String about = this.userService.getAbout(token);
			return new ResponseEntity(new Response("success", HttpStatus.OK, "", about, null), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}

	}

	@PostMapping("/upload")
	public ResponseEntity<Map<String, String>> uploadFile(@RequestParam("file") MultipartFile file) {
		String publicURL = awss3Service.uploadFile(file);
		Map<String, String> response = new HashMap<>();
		response.put("publicURL", publicURL);
		return new ResponseEntity<Map<String, String>>(response, HttpStatus.CREATED);
	}

	@PostMapping("/request-a-demo")
	public ResponseEntity<Response> requestADemo(@ModelAttribute Demo demo) {
		try {
			this.demoRepository.save(demo);
			return new ResponseEntity(new Response("success", HttpStatus.OK, "succesfully submitted", null, null),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}

	}


	
}
