package com.hirekarma.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirekarma.beans.HireKarmaUserBean;
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
	public HireKarmaUserBean createUser(@RequestBody HireKarmaUserBean hireKarmaUserBean) {
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
			return userBean;
		}
		catch (Exception e) {
			LOGGER.error("Data saving failed in HireKarmaUserController.createUser(-)");
			e.printStackTrace();
			return null;
		}
	}

}
