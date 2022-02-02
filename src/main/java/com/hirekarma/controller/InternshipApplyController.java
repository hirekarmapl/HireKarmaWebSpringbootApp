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

import com.hirekarma.beans.InternshipApplyBean;
import com.hirekarma.beans.Response;
import com.hirekarma.service.InternshipApplyService;

@RestController("internshipApplyController")
@CrossOrigin
@RequestMapping("/hirekarma/")
public class InternshipApplyController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(InternshipApplyController.class);
	
	@Autowired
	private InternshipApplyService internshipApplyService;
	
	@PostMapping("/applyInternshipUrl")
	@PreAuthorize("hasRole('student')")
	public ResponseEntity<Response> applyInternship(@RequestBody InternshipApplyBean internshipApplyBean,@RequestHeader(value = "Authorization") String token){
		LOGGER.debug("Inside InternshipApplyController.applyInternship()");
		InternshipApplyBean internshipApplyBeanReturn=null;
		ResponseEntity<Response> responseEntity = null;
		Response response = new Response();
		try {
			LOGGER.debug("Inside try block of InternshipApplyController.applyInternship()");
			internshipApplyBeanReturn = internshipApplyService.insert(internshipApplyBean,token);
			LOGGER.info("Internship successfully applied");
			responseEntity = new ResponseEntity<>(response, HttpStatus.CREATED);

			response.setMessage("Internship successfully applied...");
			response.setStatus("Success");
			response.setResponseCode(responseEntity.getStatusCodeValue());
			response.setData(internshipApplyBeanReturn);
		}
		catch (Exception e) {
			LOGGER.error("Internship applying failed");
			e.printStackTrace();
			responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
			response.setMessage(e.getMessage());
			response.setStatus("Failed");
			response.setResponseCode(responseEntity.getStatusCodeValue());
		}
		return responseEntity;
	}
}
