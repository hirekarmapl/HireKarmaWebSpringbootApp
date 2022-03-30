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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirekarma.beans.HiringBean;
import com.hirekarma.beans.JobApplyBean;
import com.hirekarma.beans.Response;
import com.hirekarma.service.JobApplyService;
import com.hirekarma.utilty.Utility;
import com.hirekarma.utilty.Validation;

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
	@PostMapping("/job/hiring-meet")
	public ResponseEntity<Response> createHriringMeetForPublicJob(@RequestBody HiringBean hiringBean,@RequestHeader("Authorization")String token){
		
		
		try {
			String email = Validation.validateToken(token);
			
			return new ResponseEntity<Response>(new Response("success",HttpStatus.OK,"",this.jobApplyService.hiringMeetForPublicJob(hiringBean,email),null),HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<Response>(new Response("error",HttpStatus.BAD_REQUEST,e.getMessage(),null,null),HttpStatus.BAD_REQUEST);
			
		}
		
	}
	@PostMapping("/campus-job/hiring-meet")
	public ResponseEntity<Response> createHriringMeetForCampusJob(@RequestBody HiringBean hiringBean,@RequestHeader("Authorization")String token){
		
		
		try {
			String email = Validation.validateToken(token);
			
			return new ResponseEntity<Response>(new Response("success",HttpStatus.OK,"",this.jobApplyService.hiringMeetForCampusJob(hiringBean,email),null),HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<Response>(new Response("error",HttpStatus.BAD_REQUEST,e.getMessage(),null,null),HttpStatus.BAD_REQUEST);
			
		}
		
	}
	
	@GetMapping("/corporate/jobApply/student/{jobApplyId}")
	public ResponseEntity<Response> hireStudent(@PathVariable("jobApplyId")Long jobApplyId,@RequestHeader("Authorization")String token){
		
		
		try {
			String email = Validation.validateToken(token);
			this.jobApplyService.hireStudent(jobApplyId, email);
			return new ResponseEntity<Response>(new Response("success",HttpStatus.OK,"",null,null),HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<Response>(new Response("error",HttpStatus.BAD_REQUEST,e.getMessage(),null,null),HttpStatus.BAD_REQUEST);
			
		}
		
	}
}
