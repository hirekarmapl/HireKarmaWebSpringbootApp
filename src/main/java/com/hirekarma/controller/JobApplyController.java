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

import com.hirekarma.beans.JobApplyBean;
import com.hirekarma.service.JobApplyService;

@RestController("jobApplyController")
@CrossOrigin
@RequestMapping("/hirekarma/")
public class JobApplyController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(JobApplyController.class);
	
	@Autowired
	private JobApplyService jobApplyService;
	
	@PostMapping("/applyJobUrl")
	public ResponseEntity<JobApplyBean> applyJob(@RequestBody JobApplyBean jobApplyBean){
		LOGGER.debug("Inside JobApplyController.applyJob()");
		JobApplyBean jobApplyBeanReturn=null;
		try {
			LOGGER.debug("Inside try block of JobApplyController.applyJob()");
			jobApplyBeanReturn=jobApplyService.insert(jobApplyBean);
			LOGGER.info("Job successfully applied");
			return new ResponseEntity<JobApplyBean>(jobApplyBeanReturn,HttpStatus.OK);
		}
		catch (Exception e) {
			LOGGER.error("Job applying failed");
			e.printStackTrace();
			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
