package com.hirekarma.controller;

import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hirekarma.beans.InternshipApplyResponseBean;
import com.hirekarma.beans.Response;
import com.hirekarma.beans.WebinarRequest;
import com.hirekarma.model.Corporate;
import com.hirekarma.model.Student;
import com.hirekarma.model.University;
import com.hirekarma.model.UserProfile;
import com.hirekarma.model.Webinar;
import com.hirekarma.repository.CorporateRepository;
import com.hirekarma.repository.UserRepository;
import com.hirekarma.service.WebinarService;
import com.hirekarma.serviceimpl.UniversityUserServiceImpl;
import com.hirekarma.utilty.Validation;

@RestController
@CrossOrigin
@RequestMapping("/hirekarma/")
public class WebinarController {

	private static final Logger logger = LoggerFactory.getLogger(WebinarController.class);

	
	@Autowired
	WebinarService webinarService;
	
	@Autowired
	CorporateRepository corporateRepository;
	
	@Autowired
	UserRepository userRepository;
	
//	webinar creation can be done by student and admin
	
	@PreAuthorize("hasAnyRole('admin','corporate')")
	@PostMapping("/corporate/create-webinar")
	public ResponseEntity<Response> create(@RequestBody WebinarRequest webinarRequest,@RequestHeader("Authorization")String token) {
		
		logger.debug(" - create()");
		try {
			String email = Validation.validateToken(token);
			Corporate corporate = this.corporateRepository.findByEmail(email);
			Map<String,Object> response = null;
//			for admin
			if(corporate == null) {
				response = this.webinarService.addForAdmin(webinarRequest);
			}
//			for corporate
			else {
				response = this.webinarService.addForCorporate(webinarRequest, corporate);
			}
			return new ResponseEntity<Response>(new Response("success", 200, "", response, null), HttpStatus.OK);

		}
		catch(NoSuchElementException ne) {
			return new ResponseEntity(new Response("error", HttpStatus.NOT_FOUND, ne.getMessage(), null, null),
					HttpStatus.NOT_FOUND);
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	}
	
//	for updating webinar before accepting
	@PreAuthorize("hasRole('corporate')")
	@PutMapping("/corporate/update-webinar")
	public ResponseEntity<Response> updateWebinar(@RequestParam("webinarId")String webinarId,@RequestBody WebinarRequest webinarRequest,@RequestHeader("Authorization")String token) {
		
		try {
			String email = Validation.validateToken(token);
			Corporate corporate = this.corporateRepository.findByEmail(email);
			Map<String,Object> response = null;
			response = this.webinarService.update(webinarId, webinarRequest, corporate);

			return new ResponseEntity<Response>(new Response("success", 200, "", response, null), HttpStatus.OK);

		}
		catch(NoSuchElementException ne) {
			return new ResponseEntity(new Response("error", HttpStatus.NOT_FOUND, ne.getMessage(), null, null),
					HttpStatus.NOT_FOUND);
		}
		catch(DateTimeParseException de) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, "dateTime format not proper", null, null),
					HttpStatus.BAD_REQUEST);
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	}
	@PreAuthorize("hasRole('admin')")
	@PutMapping("/admin/webinar")
	public ResponseEntity<Response> updateIsAcceptedStatus(@RequestParam("webinarId")String webinarId,@RequestParam("status")boolean status, @RequestHeader("Authorization")String token) {
		
		try {
			Map<String,Object> response = null;
			response = this.webinarService.updateIsAcceptedStatus(status, webinarId);
			
			return new ResponseEntity<Response>(new Response("success", 200, "", response, null), HttpStatus.OK);

		}
		catch(NoSuchElementException ne) {
			return new ResponseEntity(new Response("error", HttpStatus.NOT_FOUND, ne.getMessage(), null, null),
					HttpStatus.NOT_FOUND);
		}
		catch (Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	}
	
//	delete webinar  - disbaled
	@PreAuthorize("hasRole('corporate')")
	@DeleteMapping("/corporate/webinar")
	public ResponseEntity<Response> deleteWebinar(@RequestParam("webinarId")String webinarId,@RequestParam("status")boolean status, @RequestHeader("Authorization")String token) {
		
		try {
			String email = Validation.validateToken(token);
			Corporate corporate = this.corporateRepository.findByEmail(email);
			Map<String,Object> response = null;
			response = this.webinarService.updateIsDisableStatus(status, webinarId, corporate);
			
			return new ResponseEntity<Response>(new Response("success", 200, "", response, null), HttpStatus.OK);

		}
		catch(NoSuchElementException ne) {
			return new ResponseEntity(new Response("error", HttpStatus.NOT_FOUND, ne.getMessage(), null, null),
					HttpStatus.NOT_FOUND);
		}
		catch (Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	}
	
//	get all webinar for student , admin , corporate
	@PreAuthorize("hasAnyRole('student','admin','corporate')")
	@GetMapping("/webinars")
	public ResponseEntity<Response> getWebinar(@RequestHeader("Authorization")String token) {
		Map<String,Object> response = null;
		try {
			String email = Validation.validateToken(token);
			List<Object[]> userData = this.userRepository.findUserAndAssociatedEntity(email);
			UserProfile userProfile = (UserProfile) userData.get(0)[0];
			Corporate corporate  = (Corporate) userData.get(0)[1];
			University university  = (University) userData.get(0)[2];
			Student student = (Student) userData.get(0)[3];
			if(userProfile.getUserType().equals("student")) {
				response = this.webinarService.findAllForStudent();
			}
			else if(userProfile.getUserType().equals("admin")) {
				response = this.webinarService.findAllForAdmin();
			}
			else {
				response = this.webinarService.findAllForCorporate(corporate);
			}
			
			return new ResponseEntity<Response>(new Response("success", 200, "", response, null), HttpStatus.OK);

		}
		catch(NoSuchElementException ne) {
			return new ResponseEntity(new Response("error", HttpStatus.NOT_FOUND, ne.getMessage(), null, null),
					HttpStatus.NOT_FOUND);
		}
		catch (Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	}
	

	
}
