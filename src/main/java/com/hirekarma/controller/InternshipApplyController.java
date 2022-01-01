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
import org.springframework.web.bind.annotation.RestController;

import com.hirekarma.beans.InternshipApplyBean;
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
	public ResponseEntity<InternshipApplyBean> applyInternship(@RequestBody InternshipApplyBean internshipApplyBean){
		LOGGER.debug("Inside InternshipApplyController.applyInternship()");
		InternshipApplyBean internshipApplyBeanReturn=null;
		try {
			LOGGER.debug("Inside try block of InternshipApplyController.applyInternship()");
			internshipApplyBeanReturn=internshipApplyService.insert(internshipApplyBean);
			LOGGER.info("Internship successfully applied");
			return new ResponseEntity<InternshipApplyBean>(internshipApplyBeanReturn,HttpStatus.OK);
		}
		catch (Exception e) {
			LOGGER.error("Internship applying failed");
			e.printStackTrace();
			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
