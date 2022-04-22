package com.hirekarma.controller;

import java.util.HashMap;
import java.util.List;
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
import com.hirekarma.model.Student;
import com.hirekarma.model.University;
import com.hirekarma.model.UserProfile;
import com.hirekarma.repository.CorporateRepository;
import com.hirekarma.repository.ScreeningEntityRepository;
import com.hirekarma.repository.UserRepository;
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
	
	@Autowired
	UserRepository userRepository;
	
	@PreAuthorize("hasAnyRole('university','corporate','admin')")
	@RequestMapping(value = "/screening",method = RequestMethod.POST)
	public ResponseEntity<Response> createScreening(@RequestBody ScreeningEntityParentBean screeningEntityParentBean,@RequestHeader("Authorization") String token) {
		logger.info("inside addScreeningByCorporate");
		try {
			Map<String, Object> response = new HashMap<>() ;
			String email = Validation.validateToken(token);
			List<Object[]> userData = this.userRepository.findUserAndAssociatedEntity(email);
			UserProfile userProfile = (UserProfile) userData.get(0)[0];
			Corporate corporate  = (Corporate) userData.get(0)[1];
			University university  = (University) userData.get(0)[2];
			Student student = (Student) userData.get(0)[3];
			if(userProfile.getUserType().equals("university")) {
				response = this.screeningEntityParentService.createByUniversity(email, university);
			}
			else if(userProfile.getUserType().equals("admin")) {
				response = this.screeningEntityParentService.create(screeningEntityParentBean.getTitle());
			}
			else if(userProfile.getUserType().equals("corporate")){
				response = this.screeningEntityParentService.createByCorporate(screeningEntityParentBean.getTitle(), corporate);
			}
			
			 
			return new ResponseEntity<Response>(new Response("success", 200, "", response, null),
					HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	}
	@PreAuthorize("hasAnyRole('university','corporate','admin')")
	@RequestMapping(value = "/screening/questions", method = RequestMethod.POST)
	public ResponseEntity<Response> addQuestionToScreeningEntityParent(@RequestBody ScreeningEntityParentBean screeningEntityParentBean,@RequestHeader("Authorization") String token) {
		try {
			String email = Validation.validateToken(token);

			Map<String, Object> response = new HashMap<>();
			List<Object[]> userData = this.userRepository.findUserAndAssociatedEntity(email);
			UserProfile userProfile = (UserProfile) userData.get(0)[0];
			Corporate corporate  = (Corporate) userData.get(0)[1];
			University university  = (University) userData.get(0)[2];
			Student student = (Student) userData.get(0)[3];
			if(userProfile.getUserType().equals("university")) {
				response = this.screeningEntityParentService.addQuestionsByUniversity(screeningEntityParentBean, university);
			}
			else if(userProfile.getUserType().equals("admin")) {
				response =  this.screeningEntityParentService.addQuestions(screeningEntityParentBean);
			}
			else if(userProfile.getUserType().equals("corporate")){
				response = this.screeningEntityParentService.addQuestionsByCorporate(screeningEntityParentBean,corporate );
			}
			return new ResponseEntity<Response>(new Response("success", 200, "", response, null),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasAnyRole('university','corporate','admin')")
	@RequestMapping(value = "/screening_questions", method = RequestMethod.GET)
	public ResponseEntity<Response> getAllScreeningQuestions(@RequestHeader("Authorization") String token) {
		try {
			String email = Validation.validateToken(token);
			Map<String, Object> response = new HashMap();
			List<Object[]> userData = this.userRepository.findUserAndAssociatedEntity(email);
			UserProfile userProfile = (UserProfile) userData.get(0)[0];
			Corporate corporate  = (Corporate) userData.get(0)[1];
			University university  = (University) userData.get(0)[2];
			Student student = (Student) userData.get(0)[3];
			if(userProfile.getUserType().equals("university")) {
				response.put("screening_questions", this.screeningEntityRepository.findByUniveristyId(university.getUniversityId()));
			}
			else if(userProfile.getUserType().equals("admin")) {
				response.put("screening_questions", this.screeningEntityRepository.findByAdmin());
			}
			else if(userProfile.getUserType().equals("corporate")){
				response.put("screening_questions", this.screeningEntityRepository.findByCorporateId(corporate.getCorporateId()));
			}
			response.put("screening_questions", this.screeningEntityRepository.findByCorporateId(corporate.getCorporateId()));
			
			return new ResponseEntity<Response>(new Response("success", 200, "", response, null),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasAnyRole('university','corporate','admin')")
	@RequestMapping(value = "/screening/screening_questions", method = RequestMethod.DELETE)
	public ResponseEntity<Response> deleteScreeningQuestions(@RequestHeader("Authorization") String token,@RequestBody ScreeningEntityParentBean screeningEntityParentBean) {
		try {
			
			String email = Validation.validateToken(token);
			Map<String, Object> response = new HashMap();
			List<Object[]> userData = this.userRepository.findUserAndAssociatedEntity(email);
			UserProfile userProfile = (UserProfile) userData.get(0)[0];
			Corporate corporate  = (Corporate) userData.get(0)[1];
			University university  = (University) userData.get(0)[2];
			Student student = (Student) userData.get(0)[3];
			if(userProfile.getUserType().equals("university")) {
				 this.screeningEntityParentService.deleteQuestionsByUniversity(screeningEntityParentBean, university);
				 
			}
			else if(userProfile.getUserType().equals("admin")) {
				 this.screeningEntityParentService.deleteQuestions(screeningEntityParentBean);
			}
			else if(userProfile.getUserType().equals("corporate")){
				this.screeningEntityParentService.deleteQuestionsByCorporate(screeningEntityParentBean, corporate);
			}
			
			return new ResponseEntity<Response>(new Response("success", 200, "", response, null),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasAnyRole('university','corporate','admin')")
	@RequestMapping(value = "/screenings",method = RequestMethod.GET)
	public ResponseEntity<Response> getAllScreening(@RequestHeader("Authorization") String token) {
		logger.info("inside addScreeningByCorporate");
		try {
			Map<String, Object> response = new HashMap<>() ;
			String email = Validation.validateToken(token);
			List<Object[]> userData = this.userRepository.findUserAndAssociatedEntity(email);
			UserProfile userProfile = (UserProfile) userData.get(0)[0];
			Corporate corporate  = (Corporate) userData.get(0)[1];
			University university  = (University) userData.get(0)[2];
			Student student = (Student) userData.get(0)[3];
			if(userProfile.getUserType().equals("university")) {
				response = this.screeningEntityParentService.findAllByUniversity(university);
			}
			else if(userProfile.getUserType().equals("admin")) {
				response = this.screeningEntityParentService.findAll();
			}
			else if(userProfile.getUserType().equals("corporate")){
				response = this.screeningEntityParentService.findAllByCorporate(corporate);
			}
			
			 
			return new ResponseEntity<Response>(new Response("success", 200, "", response, null),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasAnyRole('university','corporate','admin')")
	@RequestMapping(value = "/screening", method = RequestMethod.DELETE)
	public ResponseEntity<Response> deleteScreening(@RequestHeader("Authorization") String token,@RequestBody ScreeningEntityParentBean screeningEntityParentBean) {
		try {
			
			String email = Validation.validateToken(token);
			Map<String, Object> response = new HashMap();
			List<Object[]> userData = this.userRepository.findUserAndAssociatedEntity(email);
			UserProfile userProfile = (UserProfile) userData.get(0)[0];
			Corporate corporate  = (Corporate) userData.get(0)[1];
			University university  = (University) userData.get(0)[2];
			Student student = (Student) userData.get(0)[3];
			if(userProfile.getUserType().equals("university")) {
				 this.screeningEntityParentService.deleteByUniversity(screeningEntityParentBean, university);
				 
			}
			else if(userProfile.getUserType().equals("admin")) {
				 this.screeningEntityParentService.delete(screeningEntityParentBean);
			}
			else if(userProfile.getUserType().equals("corporate")){
				this.screeningEntityParentService.deleteByCorporate(screeningEntityParentBean, corporate);
			}
			
			return new ResponseEntity<Response>(new Response("successfully deleted", 200, "", response, null),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	}
	
}
