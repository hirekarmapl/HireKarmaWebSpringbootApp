package com.hirekarma.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirekarma.beans.ShareJobBean;
import com.hirekarma.beans.UniversityJobShareBean;
import com.hirekarma.service.UniversityService;

@RestController("universityController")
@CrossOrigin
@RequestMapping("/hirekarma/")
public class UniversityController {
	
private static final Logger LOGGER = LoggerFactory.getLogger(UniversityController.class);
	
	@Autowired
	private UniversityService universityService;
	
	@PostMapping("/universityResponse")
	public ResponseEntity<ShareJobBean> universityResponse(@RequestBody ShareJobBean jobBean)
	{
		LOGGER.debug("Inside UniversityController.universityResponse(-)");
		ShareJobBean shareJobBean = new ShareJobBean();
		ResponseEntity<ShareJobBean> responseEntity = null;
		try {
			LOGGER.debug("Inside try block of UniversityController.universityResponse(-)");
			shareJobBean = universityService.universityResponse(jobBean);
			LOGGER.info("Status Successfully Updated using UniversityController.universityResponse(-)");
//			shareJobBean.setResponse("SUCCESS");
			responseEntity = new ResponseEntity<>(shareJobBean,HttpStatus.ACCEPTED);
		}
		catch (Exception e) {
			LOGGER.error("Status Updation failed in UniversityController.universityResponse(-): "+e);
			e.printStackTrace();
//			shareJobBean.setResponse("FAILED");
			responseEntity =  new ResponseEntity<>(shareJobBean,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return  responseEntity;
	}
	
	
	@PostMapping("/shareJobStudent")
	public ResponseEntity<UniversityJobShareBean> shareJobStudent(@RequestBody UniversityJobShareBean  universityJobShareBean)
	{
		LOGGER.debug("Inside UniversityController.shareJobStudent(-)");
		UniversityJobShareBean university = new UniversityJobShareBean();
		ResponseEntity<UniversityJobShareBean> responseEntity = null;
		try {
			LOGGER.debug("Inside try block of UniversityController.shareJobStudent(-)");
			university = universityService.shareJobStudent(universityJobShareBean);
			LOGGER.info("Job Shared Successfully using UniversityController.shareJobStudent(-)");
			responseEntity = new ResponseEntity<>(university,HttpStatus.ACCEPTED);
		}
		catch (Exception e) {
			LOGGER.error("Job Sharing failed in UniversityController.shareJobStudent(-): "+e);
			e.printStackTrace();
			responseEntity =  new ResponseEntity<>(university,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return  responseEntity;
	}
	

}
