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
import com.hirekarma.beans.StudentUserBean;
import com.hirekarma.model.StudentUser;
import com.hirekarma.service.StudentUserService;

@RestController("studentUserController")
@CrossOrigin
@RequestMapping("/hirekarma/")
public class StudentUserController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(StudentUserController.class);
	
	@Autowired
	private StudentUserService studentUserService;
	
	@PostMapping("/saveStudentUrl")
	public ResponseEntity<StudentUserBean> createUser(@RequestBody StudentUserBean studentUserBean) {
		LOGGER.debug("Inside StudentUserController.createUser(-)");
		StudentUser studentUser=null;
		StudentUser studentUserReturn=null;
		StudentUserBean userBean=null;
		try {
			LOGGER.debug("Inside try block of StudentUserController.createUser(-)");
			studentUser=new StudentUser();
			userBean=new StudentUserBean();
			BeanUtils.copyProperties(studentUserBean, studentUser);
			studentUserReturn=studentUserService.insert(studentUser);
			LOGGER.info("Data successfully saved using StudentUserController.createUser(-)");
			BeanUtils.copyProperties(studentUserReturn,userBean);
			return new ResponseEntity<>(userBean,HttpStatus.CREATED);
		}
		catch (Exception e) {
			LOGGER.error("Data saving failed in StudentUserController.createUser(-): "+e);
			e.printStackTrace();
			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/checkStudentLoginCredentials")
	public ResponseEntity<StudentUserBean> checkLoginCredentials(@RequestBody LoginBean loginBean) {
		LOGGER.debug("Inside StudentUserController.checkLoginCredentials(-)");
		StudentUserBean studentUserBean=null;
		try {
			LOGGER.debug("Inside try block of StudentUserController.checkLoginCredentials(-)");
			studentUserBean=studentUserService.checkLoginCredentials(loginBean.getEmail(), loginBean.getPassword());
			if(studentUserBean!=null) {
				LOGGER.info("Credential matched in StudentUserController.checkLoginCredentials(-)");
				return new ResponseEntity<>(studentUserBean,HttpStatus.ACCEPTED);
			}
			else {
				LOGGER.info("Credential does not matched in StudentUserController.checkLoginCredentials(-)");
				return new ResponseEntity<>(null,HttpStatus.NOT_ACCEPTABLE);
			}
		}
		catch (Exception e) {
			LOGGER.error("Some problem occured in StudentUserController.checkLoginCredentials(-): "+e);
			e.printStackTrace();
			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
