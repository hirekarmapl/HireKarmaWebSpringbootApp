package com.hirekarma.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hirekarma.beans.AdminShareJobToUniversityBean;
import com.hirekarma.service.AdminService;

@RestController("adminController")
@CrossOrigin
@RequestMapping("/hirekarma/")
public class AdminController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);

	@Autowired
	private AdminService adminService;

	@PutMapping("/updateJobStatus")
	@PreAuthorize("hasRole('corporate')")
	public ResponseEntity<?> updateJobStatus(@RequestParam("id") Long id, @RequestParam("status") boolean status) {

		LOGGER.debug("Inside AdminController.updateJobStatus(-)");
		ResponseEntity<?> resEntity = null;
		Map<String, Object> response = new HashMap<String, Object>();

		try {
			LOGGER.debug("Inside try block of AdminController.updateJobStatus(-)");

			Map<String, Object> fieldDetails = adminService.updateActiveStatus(id, status);

			LOGGER.info("Status Successfully Updated using AdminController.updateJobStatus(-)");

			resEntity = new ResponseEntity<>(response, HttpStatus.OK);

			response.put("Status", "Success");
			response.put("Response_Code", resEntity.getStatusCodeValue());
			response.put("Message", "Job Updated Successfully");
			response.put("Data", fieldDetails.get("activeJob"));

		} catch (Exception e) {

			LOGGER.error("Status Updation failed in AdminController.updateJobStatus(-): " + e);
			e.printStackTrace();

			resEntity = new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

			response.put("Status", "Failed");
			response.put("Response_Code", resEntity.getStatusCodeValue());
			response.put("Message", e.getMessage());
		}
		return resEntity;
	}

	@PostMapping("/shareJob")
	@PreAuthorize("hasRole('corporate')")
	public ResponseEntity<?> shareJob(@RequestBody AdminShareJobToUniversityBean adminShareJobToUniversityBean) {

		LOGGER.debug("Inside AdminController.shareJob(-)");
		ResponseEntity<?> resEntity = null;
		Map<String, Object> details = new HashMap<String, Object>();
		try {
			LOGGER.debug("Inside try block of AdminController.shareJob(-)");
			Map<String, Object> fieldDetails = adminService.shareJob(adminShareJobToUniversityBean);
			LOGGER.info("Status Successfully Updated using AdminController.shareJob(-)");

			resEntity = new ResponseEntity<>(details, HttpStatus.ACCEPTED);

			details.put("Status", "Success");
			details.put("Response_Code", resEntity.getStatusCodeValue());
			details.put("Message", "Job Shared Successfully");
			details.put("ToatlSharedJob", fieldDetails.get("totalSharedJob"));

			details.put("Data", fieldDetails.get("shareJob"));

		} catch (Exception e) {
			LOGGER.error("Status Updation failed in AdminController.shareJob(-): " + e);
			e.printStackTrace();
			resEntity = new ResponseEntity<>(details, HttpStatus.INTERNAL_SERVER_ERROR);
			details.put("Status", "Failed");
			details.put("Response_Code", resEntity.getStatusCodeValue());
			details.put("Message", e.getMessage());
		}
		return resEntity;
	}
	
}
