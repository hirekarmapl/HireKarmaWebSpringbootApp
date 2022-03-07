package com.hirekarma.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
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

import com.hirekarma.beans.OnlineAssessmentBean;
import com.hirekarma.beans.Response;
import com.hirekarma.model.Blog;
import com.hirekarma.model.OnlineAssessment;
import com.hirekarma.model.QuestionANdanswer;
import com.hirekarma.service.OnlineAssessmentService;

@RestController
@CrossOrigin
@RequestMapping("/hirekarma/")
public class OnlineAssesmentController {

	@Autowired
	OnlineAssessmentService onlineAssessmentService;
	@PreAuthorize("hasRole('corporate')")
	@GetMapping("/corporate/assessment/dummy")
	public OnlineAssessment dummy() {
		return new OnlineAssessment();
	}
	
	
	@PreAuthorize("hasRole('corporate')")
	@GetMapping("/corporate/assessment/dummyBean")
	public OnlineAssessmentBean dummyBean() {
		return new OnlineAssessmentBean();
	}
	

	
	@PreAuthorize("hasRole('corporate')")
	@GetMapping("/corporate/assessment")
	public ResponseEntity<Response> getOnlineAssesmentsAddedByCorporatedWithoutQNA(@RequestHeader("Authorization")String token) {

		try {
			return new ResponseEntity(new Response("success", HttpStatus.OK, "", this.onlineAssessmentService.getOnlineAssesmentsAddedByCorporatedWithoutQNA(token), null),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasRole('corporate')")
	@PostMapping("/corporate/assessment")
	public ResponseEntity<Response> addOnlineAssessmentByCorporate(
			@RequestBody OnlineAssessmentBean onlineAssessmentBean, @RequestHeader("Authorization") String token) {
		try {
			OnlineAssessment onlineAssessment = this.onlineAssessmentService
					.addOnlineAssessmentByCorporate(onlineAssessmentBean, token);

			return new ResponseEntity<Response>(
					new Response("success", 201, "added succesfully", onlineAssessment, null), HttpStatus.CREATED);

		} catch (Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasRole('corporate')")
	@PostMapping("/corporate/assessment/student")
	public ResponseEntity<Response> sendOnlineAssessmentToStudents(
			@RequestBody OnlineAssessmentBean onlineAssessmentBean, @RequestHeader("Authorization") String token) {
		try {
			OnlineAssessment onlineAssessment = this.onlineAssessmentService
					.sendOnlineAssessmentToStudents(onlineAssessmentBean, token);

			return new ResponseEntity<Response>(
					new Response("success", 200, "", onlineAssessment, null), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasRole('student')")
	@GetMapping("/student/assessment")
	public ResponseEntity<Response> getAllOnlineAssessmentForStudent(
			 @RequestHeader("Authorization") String token) {
		try {
			List<OnlineAssessment> onlineAssessments = this.onlineAssessmentService
					.getAllOnlineAssessmentForStudent(token);

			return new ResponseEntity<Response>(
					new Response("success", 200, "", onlineAssessments, null), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasRole('corporate')")
	@PutMapping("/corporate/assessment/{slug}")
	public ResponseEntity<Response> updateOnlineAssessment(@RequestBody OnlineAssessmentBean onlineAssessmentBean, @RequestHeader("Authorization") String token,@PathVariable("slug")String slug){
		try {
			OnlineAssessment onlineAssessment = this.onlineAssessmentService
					.updateOnlineAssessment(onlineAssessmentBean, token,slug);
			return new ResponseEntity<Response>(
					new Response("success", 201, "added succesfully", onlineAssessment, null), HttpStatus.CREATED);

		} catch (Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	}
	@PreAuthorize("hasRole('corporate')")
	@GetMapping("/corporate/assessment/{slug}")
	public ResponseEntity<Response> getOnlineAssessmentBySlug( @RequestHeader("Authorization") String token,@PathVariable("slug")String slug){
		try {
			OnlineAssessment onlineAssessment = this.onlineAssessmentService
					.getOnlineAssessmentBySlug( token,slug);
			return new ResponseEntity<Response>(
					new Response("success", 201, "added succesfully", onlineAssessment, null), HttpStatus.CREATED);

		} catch (Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasRole('corporate')")
	@PostMapping("/corporate/assessment/questionaries")
	public ResponseEntity<Response> addQuestionToOnlineAssesmentByCorporate(
			@RequestBody OnlineAssessmentBean onlineAssessmentBean, @RequestHeader("Authorization") String token) {
		try {
			OnlineAssessment onlineAssessment = this.onlineAssessmentService
					.addQuestionToOnlineAssesmentByCorporate(onlineAssessmentBean.getOnlineAssessmentSlug(),onlineAssessmentBean.getQuestions(), token);
			System.out.println(onlineAssessment.toString());
			return new ResponseEntity<Response>(
					new Response("success", 201, "added succesfully", onlineAssessment, null), HttpStatus.CREATED);

		} catch (Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasRole('corporate')")
	@PutMapping("/corporate/assessment/questionaries")
	public ResponseEntity<Response> updateQuestionOfOnlineAssessmentByCorporate(
			@RequestBody OnlineAssessmentBean onlineAssessmentBean, @RequestHeader("Authorization") String token) {
		try {
			OnlineAssessment onlineAssessment = this.onlineAssessmentService
					.updateQuestionOfOnlineAssessmentByCorporate(onlineAssessmentBean.getOnlineAssessmentSlug(),onlineAssessmentBean.getQuestions(), token);
			System.out.println(onlineAssessment.toString());
			for(QuestionANdanswer q: onlineAssessment.getQuestionANdanswers()) {
				System.out.println(q.getType());
			}
			return new ResponseEntity<Response>(
					new Response("success", 201, "added succesfully", onlineAssessment, null), HttpStatus.CREATED);

		} catch (Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasRole('corporate')")
	@DeleteMapping("/corporate/assessment/questionaries")
	public ResponseEntity<Response> deleteQuestionofOnlineAssessmentByCorporate(
			@RequestBody OnlineAssessmentBean onlineAssessmentBean, @RequestHeader("Authorization") String token) {
		try {
			 this.onlineAssessmentService
					.deleteQuestionofOnlineAssessment(onlineAssessmentBean, token);
			
			return new ResponseEntity<Response>(
					new Response("success", 200, "deleted succesfully", null, null), HttpStatus.CREATED);

		} catch (Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	}
}
