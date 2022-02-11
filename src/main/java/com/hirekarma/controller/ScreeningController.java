package com.hirekarma.controller;

import java.util.HashMap;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hirekarma.beans.ScreeningBean;
import com.hirekarma.model.UserProfile;
import com.hirekarma.repository.UserRepository;
import com.hirekarma.service.ScreeningService;
import com.hirekarma.utilty.JwtUtil;

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
	private UserRepository userRepository;
	
	@PostMapping("/createScreeningQuestion")
	@PreAuthorize("hasAnyRole('admin','corporate')")
	public ResponseEntity<Map<String,Object>> createScreeningQuestion(@RequestBody ScreeningBean screeningBean, HttpServletRequest request) {
		LOGGER.debug("Inside ScreeningController.createScreeningQuestion()");
		Map<String, Object> map = null;
		ResponseEntity<Map<String, Object>> responseEntity = null;
		String jwtToken = null;
		String authorizationHeader = null;
		String email=null;
		UserProfile userProfile = null;
		try {
			authorizationHeader = request.getHeader("Authorization");
			jwtToken = authorizationHeader.substring(7);
			email = jwtTokenUtil.extractUsername(jwtToken);
			userProfile = userRepository.findUserByEmail(email);
			if(userProfile!=null) {
				screeningBean.setCorporateId(userProfile.getUserId());
			}
			else {
				screeningBean.setCorporateId(null);
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
	
	@PutMapping("/updateScreeningQuestion/{slug}")
	@PreAuthorize("hasAnyRole('admin','corporate')")
	public ResponseEntity<Map<String,Object>> updateScreeningQuestion(@PathVariable("slug") String slug,@RequestBody ScreeningBean screeningBean) {
		LOGGER.debug("Inside ScreeningController.createScreeningQuestion()");
		Map<String, Object> map = null;
		ResponseEntity<Map<String, Object>> responseEntity = null;
		try {
			map = screeningService.updateScreeningQuestion(slug,screeningBean);
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
	public ResponseEntity<Map<String,Object>> deleteScreeningQuestion(@PathVariable("slug") String slug) {
		LOGGER.debug("Inside ScreeningController.deleteScreeningQuestion()");
		Map<String, Object> map = null;
		ResponseEntity<Map<String, Object>> responseEntity = null;
		try {
			map = screeningService.deleteScreeningQuestion(slug);
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
	public ResponseEntity<Map<String,Object>> sendScreeningQuestions(@RequestParam("jobApplyId") Long jobApplyId,@RequestParam("slug") String slug) {
		LOGGER.debug("Inside ScreeningController.sendScreeningQuestions()");
		Map<String, Object> map = null;
		ResponseEntity<Map<String, Object>> responseEntity = null;
		try {
			map = screeningService.sendScreeningQuestions(jobApplyId,slug);
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
}
