package com.hirekarma.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirekarma.beans.ScreeningBean;
import com.hirekarma.service.ScreeningService;

@RestController("screeningController")
@CrossOrigin
@RequestMapping("/hirekarma/")
public class ScreeningController {
	
private static final Logger LOGGER = LoggerFactory.getLogger(ScreeningController.class);
	
	@Autowired
	private ScreeningService screeningService;
	
	@PostMapping("/insertScreeningQuestionsByAdmin")
	@PreAuthorize("hasRole('admin')")
	public ResponseEntity<ScreeningBean> insertScreeningQuestionsByAdmin(@RequestBody ScreeningBean screeningBean) {
		LOGGER.debug("ScreeningController.insertScreeningQuestionsByAdmin(-)");
		ScreeningBean screeningBeanReturn = null;
		try {
			LOGGER.debug("Inside try block of ScreeningController.insertScreeningQuestionsByAdmin(-)");
			if(screeningBean != null) {
				screeningBean.setCorporateId(0l);
				screeningBeanReturn = screeningService.insertScreeningQuestions(screeningBean);
			}
			LOGGER.info("successfully saved using ScreeningController.insertScreeningQuestionsByAdmin(-)");
			return new ResponseEntity<ScreeningBean>(screeningBeanReturn,HttpStatus.CREATED);
		}
		catch (Exception e) {
			LOGGER.error("Saving failed using ScreeningController.insertScreeningQuestionsByAdmin(-)");
			e.printStackTrace();
			return new ResponseEntity<ScreeningBean>(screeningBeanReturn,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/insertScreeningQuestionsByCorporate")
	@PreAuthorize("hasRole('corporate')")
	public ResponseEntity<ScreeningBean> insertScreeningQuestionsByCorporate(@RequestBody ScreeningBean screeningBean) {
		LOGGER.debug("ScreeningController.insertScreeningQuestionsByCorporate(-)");
		ScreeningBean screeningBeanReturn = null;
		try {
			LOGGER.debug("Inside try block of ScreeningController.insertScreeningQuestionsByCorporate(-)");
			screeningBeanReturn = screeningService.insertScreeningQuestions(screeningBean);
			LOGGER.info("successfully saved using ScreeningController.insertScreeningQuestionsByCorporate(-)");
			return new ResponseEntity<ScreeningBean>(screeningBeanReturn,HttpStatus.CREATED);
		}
		catch (Exception e) {
			LOGGER.error("Saving failed using ScreeningController.insertScreeningQuestionsByCorporate(-)");
			e.printStackTrace();
			return new ResponseEntity<ScreeningBean>(screeningBeanReturn,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/getScreeningQuestionsByCorporateId/{corporateId}")
	@PreAuthorize("hasRole('corporate')")
	public ResponseEntity<ScreeningBean> getScreeningQuestionsByCorporateId(@PathVariable Long corporateId) {
		LOGGER.debug("ScreeningController.getScreeningQuestionsByCorporateId(-)");
		ScreeningBean screeningBeanReturn = null;
		try {
			LOGGER.debug("Inside try block of ScreeningController.getScreeningQuestionsByCorporateId(-)");
			screeningBeanReturn = screeningService.getScreeningQuestionsByCorporateId(corporateId);
			LOGGER.info("getting successfully using ScreeningController.getScreeningQuestionsByCorporateId(-)");
			return new ResponseEntity<ScreeningBean>(screeningBeanReturn,HttpStatus.CREATED);
		}
		catch (Exception e) {
			LOGGER.error("Getting failed using ScreeningController.getScreeningQuestionsByCorporateId(-)");
			e.printStackTrace();
			return new ResponseEntity<ScreeningBean>(screeningBeanReturn,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
