package com.hirekarma.controller;

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
	
	@Autowired
	private HireKarmaUserService hireKarmaUserService;
	
	@PostMapping("/saveUserUrl")
	public HireKarmaUserBean createUser(@RequestBody HireKarmaUserBean hireKarmaUserBean) {
		HireKarmaUser hireKarmaUser=new HireKarmaUser();
		HireKarmaUser hireKarmaUserReturn=null;
		HireKarmaUserBean userBean=new HireKarmaUserBean();
		BeanUtils.copyProperties(hireKarmaUserBean, hireKarmaUser);
		hireKarmaUserReturn=hireKarmaUserService.insert(hireKarmaUser);
		BeanUtils.copyProperties(hireKarmaUserReturn,userBean);
		return userBean;
	}

}
