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

import com.hirekarma.beans.HireKarmaUserBean;
import com.hirekarma.beans.LoginBean;
import com.hirekarma.model.HireKarmaUser;
import com.hirekarma.service.HireKarmaUserService;

@RestController("hireKarmaUserController")
@CrossOrigin
@RequestMapping("/hirekarma/")
public class HireKarmaUserController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HireKarmaUserController.class);
	
	@Autowired
	private HireKarmaUserService hireKarmaUserService;
	
	@PostMapping("/saveUserUrl")
	public ResponseEntity<HireKarmaUserBean> createUser(@RequestBody HireKarmaUserBean hireKarmaUserBean) {
		LOGGER.debug("Inside HireKarmaUserController.createUser(-)");
		HireKarmaUser hireKarmaUser=null;
		HireKarmaUser hireKarmaUserReturn=null;
		HireKarmaUserBean userBean=null;
		try {
			LOGGER.debug("Inside try block of HireKarmaUserController.createUser(-)");
			hireKarmaUser=new HireKarmaUser();
			userBean=new HireKarmaUserBean();
			BeanUtils.copyProperties(hireKarmaUserBean, hireKarmaUser);
			hireKarmaUserReturn=hireKarmaUserService.insert(hireKarmaUser);
			LOGGER.info("Data successfully saved using HireKarmaUserController.createUser(-)");
			BeanUtils.copyProperties(hireKarmaUserReturn,userBean);
			return new ResponseEntity<>(userBean,HttpStatus.CREATED);
		}
		catch (Exception e) {
			LOGGER.error("Data saving failed in HireKarmaUserController.createUser(-): "+e);
			e.printStackTrace();
			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/checkLoginCredentials")
	public ResponseEntity<HireKarmaUserBean> checkLoginCredentials(@RequestBody LoginBean loginBean) {
		LOGGER.debug("Inside HireKarmaUserController.checkLoginCredentials(-)");
		HireKarmaUserBean hireKarmaUserBean=null;
		try {
			LOGGER.debug("Inside try block of HireKarmaUserController.checkLoginCredentials(-)");
			hireKarmaUserBean=hireKarmaUserService.checkLoginCredentials(loginBean.getEmail(), loginBean.getPassword());
			if(hireKarmaUserBean!=null) {
				LOGGER.info("Credential matched in HireKarmaUserController.checkLoginCredentials(-)");
				return new ResponseEntity<>(hireKarmaUserBean,HttpStatus.ACCEPTED);
			}
			else {
				LOGGER.info("Credential does not matched in HireKarmaUserController.checkLoginCredentials(-)");
				return new ResponseEntity<>(null,HttpStatus.NOT_ACCEPTABLE);
			}
		}
		catch (Exception e) {
			LOGGER.error("Some problem occured in HireKarmaUserController.checkLoginCredentials(-): "+e);
			e.printStackTrace();
			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
