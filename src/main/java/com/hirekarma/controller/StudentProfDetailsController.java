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

import com.hirekarma.beans.StudentProfessionalDetailsBean;
import com.hirekarma.service.StudentProfDetailsService;

@RestController("StudentProfDetailsController")
@CrossOrigin
@RequestMapping("/hirekarma/")
public class StudentProfDetailsController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(StudentProfDetailsController.class);

	@Autowired
	private StudentProfDetailsService studentProfDetailsService;
	
	@PostMapping("/addStudentProfessionalDetails")
	@PreAuthorize("hasRole('student')")
	public ResponseEntity<StudentProfessionalDetailsBean> addStudentProfessionalDetails(@RequestBody StudentProfessionalDetailsBean professionalDetailsBean) {
		
		LOGGER.debug("Inside StudentProfDetailsController.addStudentProfessionalDetails(-)");
		StudentProfessionalDetailsBean professionalDetailsBeanReturn=null;
		ResponseEntity<StudentProfessionalDetailsBean> responseEntity=null;
		try {
			LOGGER.debug("Inside StudentProfDetailsController.addStudentProfessionalDetails(-)");
			professionalDetailsBeanReturn=studentProfDetailsService.addStudentProfessionalDetails(professionalDetailsBean);
			responseEntity=new ResponseEntity<StudentProfessionalDetailsBean>(professionalDetailsBeanReturn,HttpStatus.CREATED);
			LOGGER.info("Data saved successfully using StudentProfDetailsController.addStudentProfessionalDetails(-)");
		}
		catch (Exception e) {
			LOGGER.error("Data saving failed using StudentProfDetailsController.addStudentProfessionalDetails(-): "+e.getMessage());
			responseEntity=new ResponseEntity<StudentProfessionalDetailsBean>(professionalDetailsBeanReturn,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}
	

}
