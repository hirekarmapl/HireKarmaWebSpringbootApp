package com.hirekarma.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirekarma.beans.JobApplyBean;
import com.hirekarma.beans.Response;
import com.hirekarma.service.JobApplyService;

@RestController("jobApplyController")
@CrossOrigin
@RequestMapping("/hirekarma/")
public class JobApplyController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(JobApplyController.class);
	
	@Autowired
	private JobApplyService jobApplyService;
	
	@PostMapping("/applyJobUrl")
	@PreAuthorize("hasRole('student')")
	public ResponseEntity<Response> applyJob(@RequestBody JobApplyBean jobApplyBean,@RequestHeader(value = "Authorization") String token){
		LOGGER.debug("Inside JobApplyController.applyJob()");
		JobApplyBean jobApplyBeanReturn=null;
		ResponseEntity<Response> responseEntity = null;
		Response response = new Response();
		try {
			LOGGER.debug("Inside try block of JobApplyController.applyJob()");
			jobApplyBeanReturn=jobApplyService.insert(jobApplyBean,token);
			LOGGER.info("Job successfully applied");
			responseEntity = new ResponseEntity<>(response, HttpStatus.CREATED);

			response.setMessage("Job Applied Successfully...");
			response.setStatus("Success");
			response.setResponseCode(responseEntity.getStatusCodeValue());
			response.setData(jobApplyBeanReturn);
		}
		catch (Exception e) {
			LOGGER.error("Job applying failed");
			e.printStackTrace();
			responseEntity = new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
			response.setMessage(e.getMessage());
			response.setStatus("Failed");
			response.setResponseCode(responseEntity.getStatusCodeValue());
		}
		return responseEntity;
	}
}
