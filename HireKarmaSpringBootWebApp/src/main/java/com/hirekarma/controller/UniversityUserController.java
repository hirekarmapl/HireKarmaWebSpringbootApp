package com.hirekarma.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hirekarma.beans.LoginBean;

import com.hirekarma.beans.UniversityUserBean;

import com.hirekarma.model.UniversityUser;
import com.hirekarma.service.UniversityUserService;

@RestController("UniversityUserController ")
@CrossOrigin
@RequestMapping("/university/")
public class UniversityUserController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UniversityUserController.class);
	
	@Autowired
	private UniversityUserService universityUserService;
	
	@PostMapping("/UniversityUserUrl")
	public ResponseEntity<UniversityUserBean> createUser(@RequestBody UniversityUserBean universityUserBean){
		
		LOGGER.debug("Inside UniversityUserController.createUser(-)");
		UniversityUser universityUser=null;
		UniversityUser universityUserReturn=null;
		UniversityUserBean userBean=null;	
	
		try {
			LOGGER.debug("Inside try block of UniversityUserController.createUser(-)");
			universityUser=new UniversityUser();
			userBean=new UniversityUserBean();
			BeanUtils.copyProperties(universityUserBean, universityUser);
			universityUserReturn=universityUserService.insert(universityUser);
			LOGGER.info("Data successfully saved using UniversityUserController.createUser(-)");
			BeanUtils.copyProperties(universityUserReturn,userBean);
			
			return new ResponseEntity<>(userBean,HttpStatus.CREATED);
			
		}
		catch (Exception e){
			LOGGER.error("Data saving failed in UniversityUserController.createUser(-): "+e);
			e.printStackTrace();
			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
				
		}	
	}
	
	@PostMapping("/checkLoginCredentials")
	public ResponseEntity<UniversityUserBean> checkLoginCredentials(@RequestBody LoginBean loginBean) {
		LOGGER.debug("Inside UniversityUserController.checkLoginCredentials(-)");
		UniversityUserBean UniversityUserBean=null;
	
	        try {
		
	     LOGGER.debug("Inside try block of UniversityUserController.checkLoginCredentials(-)");
	     UniversityUserBean=universityUserService.checkLoginCredentials(loginBean.getEmail(), loginBean.getPassword());
		if(UniversityUserBean!=null) {
		LOGGER.info("Credential matched in UniversityUserController.checkLoginCredentials(-)");
		return new ResponseEntity<>(UniversityUserBean,HttpStatus.ACCEPTED);
		
		
		}
		else {
			LOGGER.info("Credential does not matched in UniversityUserController.checkLoginCredentials(-)");
			return new ResponseEntity<>(null,HttpStatus.NOT_ACCEPTABLE);
		}
		
		
		
	}catch(Exception e) {
		LOGGER.error("Some problem occured in UniversityUserController.checkLoginCredentials(-): "+e);
		e.printStackTrace();
		return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);	
		
	}
	
	
	}	
	
}
