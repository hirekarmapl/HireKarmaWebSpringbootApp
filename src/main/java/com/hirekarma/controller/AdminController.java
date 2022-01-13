package com.hirekarma.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hirekarma.model.Job;
import com.hirekarma.model.UserProfile;
import com.hirekarma.service.AdminService;

@RestController("adminController")
@CrossOrigin
@RequestMapping("/hirekarma/")
public class AdminController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);
	
	@Autowired
	private AdminService adminService;
	
	
	@PostMapping("/updateJobStatus")
	@PreAuthorize("hasRole('admin')")
	public ResponseEntity<Job> updateJobStatus(@RequestParam("id") Long id) {
		
		LOGGER.debug("Inside AdminController.updateJobStatus(-)");
		Job job = new Job();
		ResponseEntity<Job> resEntity = null;
		
		try {
			LOGGER.debug("Inside try block of AdminController.updateJobStatus(-)");
			job = adminService.updateActiveStatus(id,"Active");
			LOGGER.info("Status Successfully Updated using AdminController.updateJobStatus(-)");
			resEntity = new ResponseEntity<>(job,HttpStatus.ACCEPTED);
		}
		catch (Exception e) {
			LOGGER.error("Status Updation failed in AdminController.updateJobStatus(-): "+e);
			e.printStackTrace();
			resEntity =  new ResponseEntity<>(job,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return  resEntity;
	}
	
	@PostMapping("/shareJob")
//	@PreAuthorize("hasRole('admin')")
	public  ResponseEntity<UserProfile> shareJob(@RequestParam("jobId") Long jobId,@RequestParam("universityId") Long universityId)
	{
		LOGGER.debug("Inside StudentController.shareJob(-)");
		UserProfile job = new UserProfile();
		ResponseEntity<UserProfile> resEntity = null;
		
		try {
			LOGGER.debug("Inside try block of AdminController.shareJob(-)");
			job = adminService.shareJob(jobId,universityId);
			LOGGER.info("Status Successfully Updated using AdminController.shareJob(-)");
			job.setResponse("SHARED");
			resEntity = new ResponseEntity<>(job,HttpStatus.ACCEPTED);
		}
		catch (Exception e) {
			LOGGER.error("Status Updation failed in AdminController.shareJob(-): "+e);
			e.printStackTrace();
			job.setResponse("FAILED");
			resEntity =  new ResponseEntity<>(job,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return  resEntity;
	}
}
