package com.hirekarma.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hirekarma.model.StudentBatch;
import com.hirekarma.model.StudentBranch;
import com.hirekarma.service.MasterDataService;

@RestController("masterDataController")
@CrossOrigin
@RequestMapping("/hirekarma/")
public class MasterDataController {

	private static final Logger LOGGER = LoggerFactory.getLogger(MasterDataController.class);

	@Autowired
	public MasterDataService masterDataService;

	@RequestMapping("/masterData")
	public ResponseEntity<?> fetchMasterData(@RequestParam(value = "value") String value) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		ResponseEntity<?> responseEntity = null;
		try {
			LOGGER.info("Inside Try Block Of MasterDataController.fetchMasterData(-)");

			list = masterDataService.fetchMasterData(value);

			responseEntity = new ResponseEntity<>(list, HttpStatus.OK);

		} catch (Exception e) {
			LOGGER.error("Data Fetching Failed In MasterDataController.fetchMasterData(-): " + e);
			e.printStackTrace();
			responseEntity = new ResponseEntity<>(list, HttpStatus.NOT_FOUND);
		}
//		Response response = Response.status(Response.Status.OK)
//			      .entity(list)
//			      .build();
		return responseEntity;
	}
	
	@PostMapping("/createBatch")
	@PreAuthorize("hasRole('admin')")
	public ResponseEntity<?> createBatch(@RequestBody StudentBatch btch) {

		LOGGER.debug("Inside MasterDataController.createBatch(-)");
		ResponseEntity<?> resEntity = null;
		Map<String, Object> details = new HashMap<String, Object>();
		try {
			LOGGER.debug("Inside try block of MasterDataController.createBatch(-)");
			Map<String, Object> batch = masterDataService.createBatch(btch);
			LOGGER.info("Status Successfully Updated using MasterDataController.createBatch(-)");

			resEntity = new ResponseEntity<>(details, HttpStatus.ACCEPTED);

			details.put("Status", "Success");
			details.put("Response_Code", resEntity.getStatusCodeValue());
			details.put("Message", "Batch Created Successfully");

			details.put("Data", batch.get("response"));

		} catch (Exception e) {
			LOGGER.error("Status Updation failed in MasterDataController.createBatch(-): " + e);
			e.printStackTrace();
			resEntity = new ResponseEntity<>(details, HttpStatus.FAILED_DEPENDENCY);
			details.put("Status", "Failed");
			details.put("Response_Code", resEntity.getStatusCodeValue());
			details.put("Message", e.getMessage());
		}
		return resEntity;
	}
	
	@PostMapping("/createBranch")
	@PreAuthorize("hasRole('admin')")
	public ResponseEntity<?> createBranch(@RequestBody StudentBranch branch) {

		LOGGER.debug("Inside MasterDataController.createBranch(-)");
		ResponseEntity<?> resEntity = null;
		Map<String, Object> details = new HashMap<String, Object>();
		try {
			LOGGER.debug("Inside try block of MasterDataController.createBranch(-)");
			Map<String, Object> batch = masterDataService.createBranch(branch);
			LOGGER.info("Status Successfully Updated using MasterDataController.createBranch(-)");

			resEntity = new ResponseEntity<>(details, HttpStatus.ACCEPTED);

			details.put("Status", "Success");
			details.put("Response_Code", resEntity.getStatusCodeValue());
			details.put("Message", "Branch Created Successfully");

			details.put("Data", batch.get("response"));

		} catch (Exception e) {
			LOGGER.error("Status Updation failed in MasterDataController.createBranch(-): " + e);
			e.printStackTrace();
			resEntity = new ResponseEntity<>(details, HttpStatus.FAILED_DEPENDENCY);
			details.put("Status", "Failed");
			details.put("Response_Code", resEntity.getStatusCodeValue());
			details.put("Message", e.getMessage());
		}
		return resEntity;
	}
}
