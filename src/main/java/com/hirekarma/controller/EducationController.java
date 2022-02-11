package com.hirekarma.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirekarma.beans.Response;
import com.hirekarma.model.Education;
import com.hirekarma.model.Project;
import com.hirekarma.repository.EducationRepository;
import com.hirekarma.service.EducationService;
@RestController
@CrossOrigin
@RequestMapping("/education/")
public class EducationController {
	@Autowired
EducationService educationService;
	
	@Autowired
	EducationRepository educationRepository;
	
	@PreAuthorize("hasRole('student')")
	@PostMapping("/addEducation")
	public ResponseEntity<Response> addEducation(@RequestHeader(value = "Authorization") String token,@RequestBody Education education) {
		try {
			Education educationSaved =this.educationService.addEducation(education, token);
			
			return new ResponseEntity<Response>(new Response("success", 201, "added succesfully", educationSaved, null),
					HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
		
	}
	@PreAuthorize("hasRole('student')")
	@GetMapping("/dummy")
	public Education getDummy() {
		return new Education();
	}
	@PreAuthorize("hasRole('student')")
	@GetMapping("/")
	public ResponseEntity<?> getEducations() {
		try {
			List<Education> educations = this.educationRepository.findAll();
			return new ResponseEntity<Response>(new Response("success", 200, "", educations, null), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasRole('student')")
	@GetMapping("/{id}")
	public ResponseEntity<Response> getEducation(@PathVariable("id") int educationId) {
		try {

			Optional<Education> education = educationRepository.findById(educationId);
			if (!education.isPresent()) {
				throw new Exception("check id");
			}

			return new ResponseEntity<Response>(new Response("success", 200, "", education.get(), null), HttpStatus.OK);

		} catch (Exception e) {

			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasRole('student')")
	@GetMapping("/user/{id}")
	public ResponseEntity<Response> getEducationById(@RequestHeader(value = "Authorization") String token,@PathVariable("id") int educationId) {
		try {
			Education education = this.educationService.getEducationById(educationId,token);		

			return new ResponseEntity<Response>(new Response("success", 200, "", education, null), HttpStatus.OK);

		} catch (Exception e) {

			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasRole('student')")
	@DeleteMapping("/user/{id}")
	public ResponseEntity<Response> deleteProjectById(@RequestHeader(value = "Authorization") String token,@PathVariable("id") int educationId) {
		try {
			this.educationService.deleteById(educationId, token);
			return new ResponseEntity<Response>(new Response("success", 200, "deleted succesfully", null, null),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasRole('student')")
	@GetMapping("/user/")
	public ResponseEntity<Response> getEducationUsingToken(@RequestHeader(value = "Authorization") String token) {
		try {

			List<Education> education =educationService.getAllByUser(token); 
			

			return new ResponseEntity<Response>(new Response("success", 200, "", education, null), HttpStatus.OK);

		} catch (Exception e) {

			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasRole('student')")
	@PutMapping("/user/{id}")
	public ResponseEntity<Response> getEducationUsingToken(@RequestHeader(value = "Authorization") String token,@PathVariable("id") int educationId,@RequestBody Education education) {
		try {

			Education educationSaved =educationService.updateById(educationId, token, education);
			
education.setId(educationId);
			return new ResponseEntity<Response>(new Response("success", 200, "", educationSaved, null), HttpStatus.OK);

		} catch (Exception e) {

			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	}
	
}
