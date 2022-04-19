package com.hirekarma.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hirekarma.beans.BlogBean;
import com.hirekarma.beans.Response;
import com.hirekarma.beans.ScreeningEntityParentBean;
import com.hirekarma.model.Blog;
import com.hirekarma.model.Corporate;
import com.hirekarma.repository.CorporateRepository;
import com.hirekarma.repository.ScreeningEntityRepository;
import com.hirekarma.service.ScreeningEntityParentService;
import com.hirekarma.serviceimpl.OnlineAssessmentServiceImpl;
import com.hirekarma.utilty.Validation;


@RequestMapping("/hirekarma/")
@CrossOrigin
@RestController
public class ScreeningEntityParentController {
	
	private static final Logger logger = LoggerFactory.getLogger(ScreeningEntityParentController.class);
	@Autowired
	ScreeningEntityParentService screeningEntityParentService;
	
	@Autowired
	CorporateRepository corporateRepository;
	
	@Autowired
	ScreeningEntityRepository screeningEntityRepository;
	
	@PreAuthorize("hasRole('corporate')")
	@RequestMapping(value = "/corporate/screening",method = RequestMethod.POST)
	public ResponseEntity<Response> addScreeningByCorporate(@RequestBody ScreeningEntityParentBean screeningEntityParentBean,@RequestHeader("Authorization") String token) {
		logger.info("inside addScreeningByCorporate");
		try {
			String email = Validation.validateToken(token);
			Corporate corporate = this.corporateRepository.findByEmail(email);
			Map<String, Object> response = this.screeningEntityParentService.createByCorporate(screeningEntityParentBean.getTitle(), corporate);
			return new ResponseEntity<Response>(new Response("success", 200, "", response, null),
					HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	}
	@PreAuthorize("hasRole('corporate')")
	@RequestMapping(value = "/corporate/screening/questions", method = RequestMethod.POST)
	public ResponseEntity<Response> addQuestionToScreeningEntityParent(@RequestBody ScreeningEntityParentBean screeningEntityParentBean,@RequestHeader("Authorization") String token) {
		try {
			String email = Validation.validateToken(token);
			Corporate corporate = this.corporateRepository.findByEmail(email);
			Map<String, Object> response = this.screeningEntityParentService.addQuestionsByCorporate(screeningEntityParentBean,corporate );
			return new ResponseEntity<Response>(new Response("success", 200, "", response, null),
					HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasRole('corporate')")
	@RequestMapping(value = "/corporate/screening_questions", method = RequestMethod.GET)
	public ResponseEntity<Response> getAllScreeningQuestions(@RequestHeader("Authorization") String token) {
		try {
			String email = Validation.validateToken(token);
			Corporate corporate = this.corporateRepository.findByEmail(email);
//			Map<String, Object> response = this.screeningEntityParentService.addQuestionsByCorporate(screeningEntityParentBean,corporate );
			Map<String, Object> response = new HashMap();
			logger.info("corporate id {}",corporate.getCorporateId());
			response.put("screening_questions", this.screeningEntityRepository.findByCorporateId(corporate.getCorporateId()));
			return new ResponseEntity<Response>(new Response("success", 200, "", response, null),
					HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	}
}
