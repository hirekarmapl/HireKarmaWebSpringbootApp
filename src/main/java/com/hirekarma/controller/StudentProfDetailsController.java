package com.hirekarma.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;

import com.hirekarma.beans.JobApplyBean;
import com.hirekarma.beans.StudentProfessionalExperienceBean;
import com.hirekarma.model.StudentProfessionalExperience;
import com.hirekarma.service.StudentProfDetailsService;

public class StudentProfDetailsController {

	@Autowired
	private StudentProfDetailsService service;
	

}
