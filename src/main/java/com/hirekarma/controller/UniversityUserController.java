package com.hirekarma.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirekarma.beans.UserBean;
import com.hirekarma.model.UserProfile;
import com.hirekarma.service.UniversityUserService;

@RestController("universityUserController ")
@CrossOrigin
@RequestMapping("/hirekarma/")
public class UniversityUserController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UniversityUserController.class);
	
	@Autowired
	private UniversityUserService universityUserService;
	
//	@PostMapping("/universitySaveUrl")
//	public ResponseEntity<UniversityUserBean> createUser(@RequestBody UniversityUserBean universityUserBean){
//		LOGGER.debug("Inside UniversityUserController.createUser(-)");
//		UniversityUser universityUser=null;
//		UniversityUser universityUserReturn=null;
//		UniversityUserBean userBean=null;	
//		try {
//			LOGGER.debug("Inside try block of UniversityUserController.createUser(-)");
//			universityUser=new UniversityUser();
//			userBean=new UniversityUserBean();
//			BeanUtils.copyProperties(universityUserBean, universityUser);
//			universityUserReturn=universityUserService.insert(universityUser);
//			BeanUtils.copyProperties(universityUserReturn,userBean);
//			LOGGER.info("Data successfully saved using UniversityUserController.createUser(-)");
//			return new ResponseEntity<>(userBean,HttpStatus.CREATED);
//		}
//		catch (Exception e){
//			LOGGER.error("Data saving failed in UniversityUserController.createUser(-): "+e);
//			e.printStackTrace();
//			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
//		}	
//	}
	
	@PostMapping("/universitySaveUrl")
	public ResponseEntity<UserBean> createUser(@RequestBody UserBean universityUserBean){
		LOGGER.debug("Inside UniversityUserController.createUser(-)");
		UserProfile universityUser=null;
		UserProfile universityUserReturn=null;
		UserBean userBean=null;	
		try {
			LOGGER.debug("Inside try block of UniversityUserController.createUser(-)");
			universityUser=new UserProfile();
			userBean=new UserBean();
			BeanUtils.copyProperties(universityUserBean, universityUser);
			universityUserReturn=universityUserService.insert(universityUser);
			BeanUtils.copyProperties(universityUserReturn,userBean);
			LOGGER.info("Data successfully saved using UniversityUserController.createUser(-)");
			return new ResponseEntity<>(userBean,HttpStatus.CREATED);
		}
		catch (Exception e){
			LOGGER.error("Data saving failed in UniversityUserController.createUser(-): "+e);
			e.printStackTrace();
			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
//	@PostMapping("/checkLoginCredentials")
//	public ResponseEntity<UniversityUserBean> checkLoginCredentials(@RequestBody LoginBean loginBean) {
//		LOGGER.debug("Inside UniversityUserController.checkLoginCredentials(-)");
//		UniversityUserBean UniversityUserBean=null;
//	    try {
//	    	LOGGER.debug("Inside try block of UniversityUserController.checkLoginCredentials(-)");
//	    	UniversityUserBean=universityUserService.checkLoginCredentials(loginBean.getEmail(), loginBean.getPassword());
//	    	if(UniversityUserBean!=null) {
//	    		LOGGER.info("Credential matched in UniversityUserController.checkLoginCredentials(-)");
//	    		return new ResponseEntity<>(UniversityUserBean,HttpStatus.ACCEPTED);
//	    	}
//	    	else {
//	    		LOGGER.info("Credential does not matched in UniversityUserController.checkLoginCredentials(-)");
//	    		return new ResponseEntity<>(null,HttpStatus.NOT_ACCEPTABLE);
//	    	}
//	    }
//		catch(Exception e) {
//			LOGGER.error("Some problem occured in UniversityUserController.checkLoginCredentials(-): "+e);
//			e.printStackTrace();
//			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);	
//		}
//	}
	
//	@PutMapping(value = "/updateUniversityUserProfile")
//	public ResponseEntity<UniversityUserBean> updateUniversityUserProfile(@ModelAttribute UniversityUserBean universityUserBean){
//		LOGGER.debug("Inside UniversityUserController.updateUniversityUserProfile(-)");
//		UniversityUserBean userBean=null;
//		byte[] image=null;
//		try {
//			LOGGER.debug("Inside try block of UniversityUserController.updateUniversityUserProfile(-)");
//			image=universityUserBean.getFile().getBytes();
//			universityUserBean.setUniversityImage(image);
//			userBean=universityUserService.updateUniversityUserProfile(universityUserBean);
//			if(userBean!=null) {
//				LOGGER.info("Coporate details successfully updated in UniversityUserController.updateUniversityUserProfile(-)");
//				userBean.setPassword(null);
//				return new ResponseEntity<>(userBean,HttpStatus.OK);
//			}
//			else {
//				LOGGER.info("Coporate details not found in UniversityUserController.updateUniversityUserProfile(-)");
//				return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
//			}
//		}
//		catch (IOException e) {
//			LOGGER.error("Problem occured during image to byte[] conversion in UniversityUserController.updateUniversityUserProfile(-): "+e);
//			e.printStackTrace();
//			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//		catch (Exception e) {
//			LOGGER.error("Some problem occured in UniversityUserController.updateUniversityUserProfile(-): "+e);
//			e.printStackTrace();
//			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}
	
	@PutMapping(value = "/updateUniversityUserProfile")
	@PreAuthorize("hasRole('university')")
	public ResponseEntity<UserBean> updateUniversityUserProfile(@ModelAttribute UserBean universityUserBean){
		LOGGER.debug("Inside UniversityUserController.updateUniversityUserProfile(-)");
		UserBean userBean=null;
		byte[] image=null;
		try {
			LOGGER.debug("Inside try block of UniversityUserController.updateUniversityUserProfile(-)");
			image=universityUserBean.getFile().getBytes();
			universityUserBean.setImage(image);
			userBean=universityUserService.updateUniversityUserProfile(universityUserBean);
			if(userBean!=null) {
				LOGGER.info("Coporate details successfully updated in UniversityUserController.updateUniversityUserProfile(-)");
				userBean.setPassword(null);
				return new ResponseEntity<>(userBean,HttpStatus.OK);
			}
			else {
				LOGGER.info("Coporate details not found in UniversityUserController.updateUniversityUserProfile(-)");
				return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
			}
		}
		catch (IOException e) {
			LOGGER.error("Problem occured during image to byte[] conversion in UniversityUserController.updateUniversityUserProfile(-): "+e);
			e.printStackTrace();
			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		catch (Exception e) {
			LOGGER.error("Some problem occured in UniversityUserController.updateUniversityUserProfile(-): "+e);
			e.printStackTrace();
			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
//	@GetMapping(value = "/findUniversityById/{universityId}")
//	public ResponseEntity<UniversityUserBean> findUniversityById(@PathVariable Long universityId){
//		LOGGER.debug("Inside UniversityUserController.findUniversityById(-)");
//		UniversityUserBean userBean=null;
//		try {
//			LOGGER.debug("Inside try block of UniversityUserController.findUniversityById(-)");
//			userBean=universityUserService.findUniversityById(universityId);
//			if(userBean!=null) {
//				LOGGER.info("University details get in UniversityUserController.findUniversityById(-)");
//				userBean.setPassword(null);
//				return new ResponseEntity<>(userBean,HttpStatus.OK);
//			}
//			else {
//				LOGGER.info("University details not found in UniversityUserController.findUniversityById(-)");
//				return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
//			}
//		}
//		catch (Exception e) {
//			LOGGER.error("Some problem occured in UniversityUserController.findUniversityById(-): "+e);
//			e.printStackTrace();
//			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}
	
	@GetMapping(value = "/findUniversityById/{universityId}")
	@PreAuthorize("hasRole('university')")
	public ResponseEntity<UserBean> findUniversityById(@PathVariable Long universityId){
		LOGGER.debug("Inside UniversityUserController.findUniversityById(-)");
		UserBean userBean=null;
		try {
			LOGGER.debug("Inside try block of UniversityUserController.findUniversityById(-)");
			userBean=universityUserService.findUniversityById(universityId);
			if(userBean!=null) {
				LOGGER.info("University details get in UniversityUserController.findUniversityById(-)");
				userBean.setPassword(null);
				return new ResponseEntity<>(userBean,HttpStatus.OK);
			}
			else {
				LOGGER.info("University details not found in UniversityUserController.findUniversityById(-)");
				return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
			}
		}
		catch (Exception e) {
			LOGGER.error("Some problem occured in UniversityUserController.findUniversityById(-): "+e);
			e.printStackTrace();
			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
