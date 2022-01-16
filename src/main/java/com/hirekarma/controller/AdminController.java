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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hirekarma.beans.JobBean;
import com.hirekarma.beans.ShareJobBean;
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
	public ResponseEntity<JobBean> updateJobStatus(@RequestParam("id") Long id,@RequestParam("status") String status) {
		
		LOGGER.debug("Inside AdminController.updateJobStatus(-)");
		JobBean job = new JobBean();
		ResponseEntity<JobBean> resEntity = null;
		
		try {
			LOGGER.debug("Inside try block of AdminController.updateJobStatus(-)");
			job = adminService.updateActiveStatus(id,status);
			LOGGER.info("Status Successfully Updated using AdminController.updateJobStatus(-)");
			resEntity = new ResponseEntity<>(job,HttpStatus.ACCEPTED);
		}
		catch (Exception e) {
			LOGGER.error("Status Updation failed in AdminController.updateJobStatus(-): "+e);
			job.setResponse("FAILED");
			e.printStackTrace();
			resEntity =  new ResponseEntity<>(job,HttpStatus.OK);
		}
		return  resEntity;
	}
	
	@PostMapping("/shareJob")
	@PreAuthorize("hasRole('admin')")
	public  ResponseEntity<ShareJobBean> shareJob(@RequestBody ShareJobBean shareJobBean)
	{
		LOGGER.debug("Inside AdminController.shareJob(-)");
		ShareJobBean job = new ShareJobBean();
		ResponseEntity<ShareJobBean> resEntity = null;
		
		try {
			LOGGER.debug("Inside try block of AdminController.shareJob(-)");
			job = adminService.shareJob(shareJobBean);
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
