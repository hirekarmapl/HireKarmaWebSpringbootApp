package com.hirekarma.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hirekarma.model.Job;
import com.hirekarma.service.AdminService;

@RestController("adminController")
@CrossOrigin
@RequestMapping("/hirekarma/")
public class AdminController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);
	
	@Autowired
	private AdminService adminService;
	
	
	@PostMapping("/updateJobStatus")
	public ResponseEntity<Job> updateJobStatus(@RequestParam("id") Long id, @RequestParam("status") String status) {
		
		LOGGER.debug("Inside StudentController.updateJobStatus(-)");
		Job job = new Job();
		ResponseEntity<Job> resEntity = null;
		
		try {
			LOGGER.debug("Inside try block of AdminController.updateJobStatus(-)");
			job = adminService.updateActiveStatus(id,status);
			LOGGER.info("Status Successfully Updated using AdminController.updateJobStatus(-)");
			job.setResponse("UPDATED");
			resEntity = new ResponseEntity<>(job,HttpStatus.ACCEPTED);
		}
		catch (Exception e) {
			LOGGER.error("Status Updation failed in AdminController.updateJobStatus(-): "+e);
			e.printStackTrace();
			job.setResponse("FAILED");
			resEntity =  new ResponseEntity<>(job,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return  resEntity;
	}
}
