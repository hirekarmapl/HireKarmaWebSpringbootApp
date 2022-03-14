package com.hirekarma.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hirekarma.beans.AdminShareJobToUniversityBean;
import com.hirekarma.beans.BadgeShareBean;
import com.hirekarma.beans.Response;
import com.hirekarma.model.AdminShareJobToUniversity;
import com.hirekarma.model.Corporate;
import com.hirekarma.model.Skill;
import com.hirekarma.model.University;
import com.hirekarma.service.AdminService;
import org.json.simple.JSONObject;

@RestController("adminController")
@CrossOrigin
@RequestMapping("/hirekarma/")
public class AdminController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);

	@Autowired
	private AdminService adminService;

	@PutMapping("/updateJobStatus")
	@PreAuthorize("hasRole('admin')")
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
	@PutMapping("/admin/shareJob/jd/{id}")
	@PreAuthorize("hasRole('admin')")
	public ResponseEntity<?> requestCorporateToUpdateJD(@PathVariable("id") long adminShareJobId){
		try {
			AdminShareJobToUniversity adminShareJobToUniversity = this.adminService.requestCorporateToUpdateJD(adminShareJobId);
			return new ResponseEntity<Response>(new Response("success", 201, "added succesfully", adminShareJobToUniversity, null),
					HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	}
	@PutMapping("/admin/shareJob/lookup/{id}")
	@PreAuthorize("hasRole('admin')")
	ResponseEntity<?> updateShareJob( @RequestBody JSONObject lookup,@PathVariable("id") long id) {
		System.out.println(lookup.toString());
		try {
			AdminShareJobToUniversity adminShareJobToUniversity = this.adminService.updateShareJob(lookup,id);
			return new ResponseEntity<Response>(new Response("success", 201, "added succesfully", adminShareJobToUniversity, null),
					HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	}
	@PostMapping("/shareJob")
	@PreAuthorize("hasRole('admin')")
	public ResponseEntity<?> shareJob(@RequestBody AdminShareJobToUniversityBean adminShareJobToUniversityBean) {

		LOGGER.debug("Inside AdminController.shareJob(-)");
		ResponseEntity<?> resEntity = null;
		Map<String, Object> details = new HashMap<String, Object>();
		try {
			LOGGER.debug("Inside try block of AdminController.shareJob(-)");
			Map<String, Object> fieldDetails = adminService.shareJob2(adminShareJobToUniversityBean);
			LOGGER.info("Status Successfully Updated using AdminController.shareJob(-)");

			resEntity = new ResponseEntity<>(details, HttpStatus.ACCEPTED);

			details.put("Status", "Success");
			details.put("Response_Code", resEntity.getStatusCodeValue());
			details.put("Message", "Job Shared Successfully");
			details.put("ToatlSharedJob", fieldDetails.get("totalSharedJob"));
			details.put("Data", fieldDetails.get("shareJob"));
			details.put("alreadyJobSharedToUniversity", fieldDetails.get("alreadyJobSharedToUniversity"));
			

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
	
	@RequestMapping("/displayJobList")
	@PreAuthorize("hasRole('admin')")
	public ResponseEntity<Response> displayJobList() {
		LOGGER.debug("Inside AdminController.displayJobList(-)");
		List<?> listData = null;
		ResponseEntity<Response> responseEntity = null;
		Response response = new Response();
		try {
			LOGGER.debug("Inside try block of AdminController.displayJobList(-)");
			listData = adminService.displayJobList();
			LOGGER.info("Response Successfully Updated using AdminController.displayJobList(-)");

			responseEntity = new ResponseEntity<>(response, HttpStatus.OK);

			response.setMessage("Data Fetched Successfully...");
			response.setStatus("Success");
			response.setResponseCode(responseEntity.getStatusCodeValue());
			response.setDataList(listData);

		} catch (Exception e) {
			LOGGER.error("Response Updation failed in AdminController.displayJobList(-): " + e);
			e.printStackTrace();
			responseEntity = new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			response.setMessage(e.getMessage());
			response.setStatus("Failed");
			response.setResponseCode(responseEntity.getStatusCodeValue());
		}
		return responseEntity;
	}
	
	
	@RequestMapping("/displayUniversityList")
	@PreAuthorize("hasRole('admin')")
	public ResponseEntity<Response> displayUniversityList() {
		LOGGER.debug("Inside AdminController.displayJobList(-)");
		List<?> listData = null;
		ResponseEntity<Response> responseEntity = null;
		Response response = new Response();
		try {
			LOGGER.debug("Inside try block of AdminController.displayUniversityList(-)");
			listData = adminService.displayUniversityList();
			LOGGER.info("Response Successfully Updated using AdminController.displayUniversityList(-)");

			responseEntity = new ResponseEntity<>(response, HttpStatus.OK);

			response.setMessage("Data Fetched Successfully...");
			response.setStatus("Success");
			response.setResponseCode(responseEntity.getStatusCodeValue());
			response.setDataList(listData);

		} catch (Exception e) {
			LOGGER.error("Response Updation failed in AdminController.displayUniversityList(-): " + e);
			e.printStackTrace();
			responseEntity = new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			response.setMessage(e.getMessage());
			response.setStatus("Failed");
			response.setResponseCode(responseEntity.getStatusCodeValue());
		}
		return responseEntity;
	}
	
	@PutMapping("shareBadge")
	@PreAuthorize("hasRole('admin')")
	public ResponseEntity<?> shareBadge(@RequestBody BadgeShareBean badgeShareBean) {

		LOGGER.debug("Inside AdminController.shareBadge(-)");
		
		ResponseEntity<Response> responseEntity = null;
		Response response = new Response();
		Corporate badgeShare= new Corporate();
		
		try {
			LOGGER.debug("Inside try block of AdminController.shareBadge(-)");
			
			badgeShare = adminService.shareBadge(badgeShareBean);

			LOGGER.info("Response Successfully Updated using AdminController.shareBadge(-)");

			responseEntity = new ResponseEntity<>(response, HttpStatus.OK);

			response.setMessage("Data Fetched Successfully...");
			response.setStatus("Success");
			response.setResponseCode(responseEntity.getStatusCodeValue());
			response.setData(badgeShare);


		} catch (Exception e) {
			LOGGER.error("Response Updation failed in AdminController.displayUniversityList(-): " + e);
			
			e.printStackTrace();
			
			responseEntity = new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			response.setMessage(e.getMessage());
			response.setStatus("Failed");
			response.setResponseCode(responseEntity.getStatusCodeValue());
		}
		return responseEntity;
	}
}
