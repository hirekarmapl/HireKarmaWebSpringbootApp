package com.hirekarma.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hirekarma.beans.Response;
import com.hirekarma.beans.ScreeningBean;
import com.hirekarma.beans.ScreeningRequestBean;
import com.hirekarma.model.Corporate;
import com.hirekarma.model.Student;
import com.hirekarma.model.University;
import com.hirekarma.model.UserProfile;
import com.hirekarma.repository.CorporateRepository;
import com.hirekarma.repository.ScreeningEntityRepository;
import com.hirekarma.repository.UserRepository;
import com.hirekarma.service.ScreeningService;
import com.hirekarma.utilty.JwtUtil;
import com.hirekarma.utilty.Validation;

@RestController("screeningController")
@RequestMapping("/hirekarma/")
@CrossOrigin
public class ScreeningController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ScreeningController.class);
	
	@Autowired
	private ScreeningService screeningService;
	
	@Autowired
	private JwtUtil jwtTokenUtil;
	
	@Autowired
	private ScreeningEntityRepository screeningEntityRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CorporateRepository corporateRepository;
	
	@PostMapping("/createScreeningQuestion")
	@PreAuthorize("hasAnyRole('admin','corporate')")
	public ResponseEntity<Map<String,Object>> createScreeningQuestion(@RequestBody ScreeningBean screeningBean, @RequestHeader("Authorization")String token) {
		LOGGER.debug("Inside ScreeningController.createScreeningQuestion()");
		Map<String, Object> map = null;
		ResponseEntity<Map<String, Object>> responseEntity = null;

		try {
			String email = Validation.validateToken(token);
		List<Object[]> userData = this.userRepository.findUserAndAssociatedEntity(email);
		UserProfile userProfile = (UserProfile) userData.get(0)[0];
		Corporate corporate  = (Corporate) userData.get(0)[1];
		University university  = (University) userData.get(0)[2];
		Student student = (Student) userData.get(0)[3];
		if(userProfile.getUserType().equals("university")) {
			screeningBean.setUniversityId(university.getUniversityId());
			map = screeningService.createScreeningQuestion(screeningBean);
		}
		else if(userProfile.getUserType().equals("admin")) {
			
			map = screeningService.createScreeningQuestion(screeningBean);
		}
		else if(userProfile.getUserType().equals("corporate")){
			screeningBean.setCorporateId(corporate.getCorporateId());
			map = screeningService.createScreeningQuestion(screeningBean);
		}

			map = screeningService.createScreeningQuestion(screeningBean);
			responseEntity = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
			LOGGER.info("Question saved using ScreeningController.createScreeningQuestion()");
			return responseEntity;
		}
		catch (Exception e) {
			LOGGER.error("Error in ScreeningController.createScreeningQuestion(-)");
			map = new HashMap<String, Object>();
			map.put("status", "Bad Request");
			map.put("responseCode", 400);
			map.put("message", "Question saving failed!!!");
			responseEntity = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
			e.printStackTrace();
			return responseEntity;
		}
	}
	
	@PostMapping("/createListScreeningQuestion")
	@PreAuthorize("hasAnyRole('admin','corporate')")
	public ResponseEntity<Map<String,Object>> createListScreeningQuestion(@RequestBody List<ScreeningBean> screeningBeans, @RequestHeader("Authorization")String token) {
		LOGGER.debug("Inside ScreeningController.createScreeningQuestion()");
		Map<String, Object> map = null;
		ResponseEntity<Map<String, Object>> responseEntity = null;
	
		try {
			String email = Validation.validateToken(token);
			List<Object[]> userData = this.userRepository.findUserAndAssociatedEntity(email);
			UserProfile userProfile = (UserProfile) userData.get(0)[0];
			Corporate corporate  = (Corporate) userData.get(0)[1];
			University university  = (University) userData.get(0)[2];
			Student student = (Student) userData.get(0)[3];
//			System.out.println(userProfile.getUserType());
			if(userProfile.getUserType().equals("university")) {
				map = screeningService.createListScreeningQuestion(screeningBeans, null,university.getUniversityId());
			}
			else if(userProfile.getUserType().equals("admin")) {
				map = screeningService.createListScreeningQuestion(screeningBeans, null,null);
			}
			else if(userProfile.getUserType().equals("corporate")){
//				LOGGER.info("{} ",corporate.getCorporateId());
				map = screeningService.createListScreeningQuestion(screeningBeans, corporate.getCorporateId(),null);
			}
			responseEntity = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
			LOGGER.info("Question saved using ScreeningController.createScreeningQuestion()");
			return responseEntity;
		}
		catch (Exception e) {
			LOGGER.error("Error in ScreeningController.createScreeningQuestion(-)");
			map = new HashMap<String, Object>();
			map.put("status", "Bad Request");
			map.put("responseCode", 400);
			map.put("message", "Question saving failed!!!");
			responseEntity = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
			e.printStackTrace();
			return responseEntity;
		}
	}
	
	@PutMapping("/updateScreeningQuestion/{slug}")
	@PreAuthorize("hasAnyRole('admin','corporate')")
	public ResponseEntity<Map<String,Object>> updateScreeningQuestion(@PathVariable("slug") String slug,@RequestBody ScreeningBean screeningBean,@RequestHeader("Authorization") String token) {
		LOGGER.debug("Inside ScreeningController.createScreeningQuestion()");
		Map<String, Object> map = null;
		ResponseEntity<Map<String, Object>> responseEntity = null;
		try {
			String email = Validation.validateToken(token);
			List<Object[]> userData = this.userRepository.findUserAndAssociatedEntity(email);
			UserProfile userProfile = (UserProfile) userData.get(0)[0];
			Corporate corporate  = (Corporate) userData.get(0)[1];
			University university  = (University) userData.get(0)[2];
			Student student = (Student) userData.get(0)[3];
//			System.out.println(userProfile.getUserType());
			if(userProfile.getUserType().equals("university")) {
				map = screeningService.updateScreeningQuestion(slug,screeningBean,null,university.getUniversityId());
			}
			else if(userProfile.getUserType().equals("admin")) {
				map = screeningService.updateScreeningQuestion(slug,screeningBean,null,null);
			}
			else if(userProfile.getUserType().equals("corporate")){
//				LOGGER.info("{} ",corporate.getCorporateId());
				map = screeningService.updateScreeningQuestion(slug,screeningBean,corporate.getCorporateId(),null);
			}
			responseEntity = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
			LOGGER.info("Question updated using ScreeningController.updateScreeningQuestion()");
			return responseEntity;
		}
		catch (Exception e) {
			LOGGER.error("Error in ScreeningController.updateScreeningQuestion(-)");
			map = new HashMap<String, Object>();
			map.put("status", "Bad Request");
			map.put("responseCode", 400);
			map.put("message", "Question updation failed!!!");
			responseEntity = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
			e.printStackTrace();
			return responseEntity;
		}
	}
	
	@DeleteMapping("/deleteScreeningQuestion/{slug}")
	@PreAuthorize("hasAnyRole('admin','corporate')")
	public ResponseEntity<Map<String,Object>> deleteScreeningQuestion(@PathVariable("slug") String slug,@RequestHeader("Authorization")String token) {
		LOGGER.debug("Inside ScreeningController.deleteScreeningQuestion()");
		Map<String, Object> map = null;
		ResponseEntity<Map<String, Object>> responseEntity = null;
		try {
		String email = Validation.validateToken(token);
		List<Object[]> userData = this.userRepository.findUserAndAssociatedEntity(email);
		UserProfile userProfile = (UserProfile) userData.get(0)[0];
		Corporate corporate  = (Corporate) userData.get(0)[1];
		University university  = (University) userData.get(0)[2];
		Student student = (Student) userData.get(0)[3];
		System.out.println("slug"+slug);
		if(userProfile.getUserType().equals("university")) {
			map = screeningService.deleteScreeningQuestion(slug,null,university.getUniversityId());
		}
		else if(userProfile.getUserType().equals("admin")) {
			map = screeningService.deleteScreeningQuestion(slug,null,null);
		}
		else if(userProfile.getUserType().equals("corporate")){
			map = screeningService.deleteScreeningQuestion(slug,corporate.getCorporateId(),null);
		}
			
			responseEntity = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
			LOGGER.info("Question deleted using ScreeningController.deleteScreeningQuestion()");
			return responseEntity;
		}
		catch (Exception e) {
			LOGGER.error("Error in ScreeningController.deleteScreeningQuestion(-)");
			map = new HashMap<String, Object>();
			map.put("status", "Bad Request");
			map.put("responseCode", 400);
			map.put("message", "Question deletion failed!!!");
			responseEntity = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
			e.printStackTrace();
			return responseEntity;
		}
	}
	
	@PostMapping("/sendScreeningQuestions")
	@PreAuthorize("hasAnyRole('admin','corporate')")
	public ResponseEntity<Map<String,Object>> sendScreeningQuestions(@RequestParam("jobApplyId") Long jobApplyId,@RequestParam("slug") String slug,@RequestParam("chatRoomId") Long chatRoomId) {
		LOGGER.debug("Inside ScreeningController.sendScreeningQuestions()");
		Map<String, Object> map = null;
		ResponseEntity<Map<String, Object>> responseEntity = null;
		try {
			map = screeningService.sendScreeningQuestions(jobApplyId,slug,chatRoomId);
			responseEntity = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
			LOGGER.info("Question sent using ScreeningController.sendScreeningQuestions()");
			return responseEntity;
		}
		catch (Exception e) {
			LOGGER.error("Error in ScreeningController.sendScreeningQuestions(-,-)");
			map = new HashMap<String, Object>();
			map.put("status", "Bad Request");
			map.put("responseCode", 400);
			map.put("message", "Question sending failed!!!");
			responseEntity = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
			e.printStackTrace();
			return responseEntity;
		}
	}
	@PostMapping("/corporate/students/screenings/send_to_students")
	@PreAuthorize("hasAnyRole('admin','corporate')")
	public ResponseEntity<Response> sendScreeningQuestionsToMultipleStudents(@RequestBody ScreeningRequestBean screeningRequestBean) {
		try {
			this.screeningService.sendScreeningQuestionsToMultipleStudent(screeningRequestBean.getJobApplyIds(), screeningRequestBean.getSlugs());
			return new ResponseEntity(new Response("success", HttpStatus.OK, "sended succesfully", null, null),
					HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	}
	@PostMapping("/corporate/students/screening/send_to_students")
	@PreAuthorize("hasAnyRole('admin','corporate')")
	public ResponseEntity<Response> sendScreeningQuestions(@RequestBody ScreeningRequestBean screeningRequestBean) {
		try {
			this.screeningService.sendScreeningQuestionToStudents(screeningRequestBean.getJobApplyIds(), screeningRequestBean.getSlug());
			return new ResponseEntity(new Response("success", HttpStatus.OK, "sended succesfully", null, null),
					HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	}
	@GetMapping("/getScreeningQuestion")
	@PreAuthorize("hasAnyRole('admin','corporate')")
	public ResponseEntity<Map<String,Object>> getScreeningQuestionsByScreeningTableId(@RequestParam("slug") String slug) {
		LOGGER.debug("Inside ScreeningController.sendScreeningQuestions()");
		Map<String, Object> map = null;
		ResponseEntity<Map<String, Object>> responseEntity = null;
		try {
			map = screeningService.getScreeningQuestionsByScreeningTableId(slug);
			responseEntity = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
			LOGGER.info("Question sent using ScreeningController.sendScreeningQuestions()");
			return responseEntity;
		}
		catch (Exception e) {
			LOGGER.error("Error in ScreeningController.sendScreeningQuestions(-,-)");
			map = new HashMap<String, Object>();
			map.put("status", "Failed");
			map.put("responseCode", 400);
			map.put("message", "Bad Request!!!");
			responseEntity = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
			e.printStackTrace();
			return responseEntity;
		}
	}
	
	@GetMapping("/getAllScreeningQuestions")
	@PreAuthorize("hasAnyRole('admin','corporate','university')")
	public ResponseEntity<Response> getAllScreeningQuestions(@RequestHeader("Authorization")String token) {
		LOGGER.debug("Inside ScreeningController.sendScreeningQuestions()");
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
			
			return new ResponseEntity<Response>(new Response("Success", 200, "", response, null),
					HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
//		Map<String, Object> map = null;
//		ResponseEntity<Map<String, Object>> responseEntity = null;
//		try {
//			String email = Validation.validateToken(token);
//			map.put("status", "Success");
//			map.put("responseCode", 200);
//			List<Object[]> userData = this.userRepository.findUserAndAssociatedEntity(email);
//			UserProfile userProfile = (UserProfile) userData.get(0)[0];
//			Corporate corporate  = (Corporate) userData.get(0)[1];
//			University university  = (University) userData.get(0)[2];
//			Student student = (Student) userData.get(0)[3];
//			if(userProfile.getUserType().equals("university")) {
//				map.put("data", this.screeningEntityRepository.findByUniveristyId(university.getUniversityId()));
//			}
//			else if(userProfile.getUserType().equals("admin")) {
//				map.put("data", this.screeningEntityRepository.findByAdmin());
//			}
//			else if(userProfile.getUserType().equals("corporate")){
//				map.put("data", this.screeningEntityRepository.findByCorporateId(corporate.getCorporateId()));
//			}
//			map.put("data", this.screeningEntityRepository.findByCorporateId(corporate.getCorporateId()));
//			
//			responseEntity = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
//			LOGGER.info("Question sent using ScreeningController.sendScreeningQuestions()");
//			return responseEntity;
//		}
//		catch (Exception e) {
//			LOGGER.error("Error in ScreeningController.sendScreeningQuestions(-)");
//			map = new HashMap<String, Object>();
//			map.put("status", "Failed");
//			map.put("responseCode", 400);
//			map.put("message", "Bad Request!!!");
//			responseEntity = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
//			e.printStackTrace();
//			return responseEntity;
//		}
	}
}
