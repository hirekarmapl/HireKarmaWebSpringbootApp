package com.hirekarma.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
