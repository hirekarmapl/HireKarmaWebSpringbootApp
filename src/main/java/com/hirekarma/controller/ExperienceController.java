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
import com.hirekarma.model.Experience;
import com.hirekarma.repository.ExperienceRepository;
import com.hirekarma.service.ExperienceService;
@RestController
@CrossOrigin
@RequestMapping("/hirekarma/")
public class ExperienceController {

	@Autowired
	ExperienceService experienceService;
	@Autowired
	ExperienceRepository experienceRepository;
	
	@PreAuthorize("hasRole('student')")
	@PostMapping("/experience")
	public ResponseEntity<Response> addExperience(@RequestHeader(value = "Authorization") String token,@RequestBody Experience experience) {
		try {
			Experience experienceSaved =this.experienceService.addExperience(experience, token);
			
			return new ResponseEntity<Response>(new Response("success", 201, "added succesfully", experienceSaved, null),
					HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
		
	}
	@PreAuthorize("hasRole('student')")
	@GetMapping("/experience/dummy")
	public Experience getDummy() {
		return new Experience();
	}
	@PreAuthorize("hasRole('student')")
	@GetMapping("/")
	public ResponseEntity<?> getEducations() {
		try {
			List<Experience> experiences = this.experienceRepository.findAll();
			return new ResponseEntity<Response>(new Response("success", 200, "", experiences, null), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasRole('student')")
	@GetMapping("/{id}")
	public ResponseEntity<Response> getExperience(@PathVariable("id") int experienceId) {
		try {

			Optional<Experience> experience = experienceRepository.findById(experienceId);
			if (!experience.isPresent()) {
				throw new Exception("user not found");
			}

			return new ResponseEntity<Response>(new Response("success", 200, "", experience.get(), null), HttpStatus.OK);

		} catch (Exception e) {

			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasRole('student')")
	@GetMapping("/user/{id}")
	public ResponseEntity<Response> getExperienceById(@RequestHeader(value = "Authorization") String token,@PathVariable("id") int experienceId) {
		try {
			Experience experience = this.experienceService.getExperienceById(experienceId,token);		

			return new ResponseEntity<Response>(new Response("success", 200, "", experience, null), HttpStatus.OK);

		} catch (Exception e) {

			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasRole('student')")
	@DeleteMapping("/user/{id}")
	public ResponseEntity<Response> deleteExperienceById(@RequestHeader(value = "Authorization") String token,@PathVariable("id") int experienceId) {
		try {
			this.experienceService.deleteById(experienceId, token);
			return new ResponseEntity<Response>(new Response("success", 200, "deleted succesfully", null, null),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasRole('student')")
	@GetMapping("/user/")
	public ResponseEntity<Response> getExperienceUsingToken(@RequestHeader(value = "Authorization") String token) {
		try {

			List<Experience> experience =experienceService.getAllByUser(token); 
			

			return new ResponseEntity<Response>(new Response("success", 200, "", experience, null), HttpStatus.OK);

		} catch (Exception e) {

			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasRole('student')")
	@PutMapping("/user/{id}")
	public ResponseEntity<Response> getEducationUsingToken(@RequestHeader(value = "Authorization") String token,@PathVariable("id") int experienceId,@RequestBody Experience  experience) {
		try {

			Experience experience1 =experienceService.updateById(experienceId, token, experience);
			experience.setId(experienceId);
			return new ResponseEntity<Response>(new Response("success", 200, "", experience1, null), HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	}
	
}
