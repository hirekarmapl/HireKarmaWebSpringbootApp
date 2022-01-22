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

import com.hirekarma.beans.CampusDriveResponseBean;
import com.hirekarma.beans.Response;
import com.hirekarma.beans.UserBean;
import com.hirekarma.model.UserProfile;
import com.hirekarma.service.CoporateUserService;

@RestController("coporateUserController")
@CrossOrigin
@RequestMapping("/hirekarma/")
public class CoporateUserController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CoporateUserController.class);

	@Autowired
	private CoporateUserService coporateUserService;

//	@PostMapping("/saveCoporateUrl")
//	public ResponseEntity<CoporateUserBean> createUser(@RequestBody CoporateUserBean coporateUserBean) {
//		LOGGER.debug("Inside CoporateUserController.createUser(-)");
//		CoporateUser coporateUser=null;
//		CoporateUser coporateUserReturn=null;
//		CoporateUserBean userBean=null;
//		try {
//			LOGGER.debug("Inside try block of CoporateUserController.createUser(-)");
//			coporateUser=new CoporateUser();
//			userBean=new CoporateUserBean();
//			BeanUtils.copyProperties(coporateUserBean, coporateUser);
//			coporateUserReturn=coporateUserService.insert(coporateUser);
//			LOGGER.info("Data successfully saved using CoporateUserController.createUser(-)");
//			BeanUtils.copyProperties(coporateUserReturn,userBean);
//			userBean.setPassword(null);
//			return new ResponseEntity<>(userBean,HttpStatus.CREATED);
//		}
//		catch (Exception e) {
//			LOGGER.error("Data saving failed in CoporateUserController.createUser(-): "+e);
//			e.printStackTrace();
//			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}

	@PostMapping("/saveCorporateUrl")
	public ResponseEntity<UserBean> createUser(@RequestBody UserBean bean) {
		LOGGER.debug("Inside CoporateUserController.createUser(-)");
		UserProfile userProfile = null;
		UserProfile userProfileReturn = null;
		UserBean userBean = null;
		try {
			LOGGER.debug("Inside try block of CoporateUserController.createUser(-)");
			userProfile = new UserProfile();
			userBean = new UserBean();
			BeanUtils.copyProperties(bean, userProfile);
			userProfileReturn = coporateUserService.insert(userProfile);
			LOGGER.info("Data successfully saved using CoporateUserController.createUser(-)");
			BeanUtils.copyProperties(userProfileReturn, userBean);
			userBean.setPassword(null);
			return new ResponseEntity<>(userBean, HttpStatus.CREATED);
		} catch (Exception e) {
			LOGGER.error("Data saving failed in CoporateUserController.createUser(-): " + e);
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

//	@PostMapping("/checkCopporateLoginCredentials")
//	public ResponseEntity<CoporateUserBean> checkLoginCredentials(@RequestBody LoginBean loginBean) {
//		LOGGER.debug("Inside CoporateUserController.checkLoginCredentials(-)");
//		CoporateUserBean coporateUserBean=null;
//		try {
//			LOGGER.debug("Inside try block of CoporateUserController.checkLoginCredentials(-)");
//			coporateUserBean=coporateUserService.checkLoginCredentials(loginBean.getEmail(), loginBean.getPassword());
//			if(coporateUserBean!=null) {
//				LOGGER.info("Credential matched in CoporateUserController.checkLoginCredentials(-)");
//				coporateUserBean.setPassword(null);
//				return new ResponseEntity<>(coporateUserBean,HttpStatus.ACCEPTED);
//			}
//			else {
//				LOGGER.info("Credential does not matched in CoporateUserController.checkLoginCredentials(-)");
//				return new ResponseEntity<>(null,HttpStatus.NOT_ACCEPTABLE);
//			}
//		}
//		catch (Exception e) {
//			LOGGER.error("Some problem occured in CoporateUserController.checkLoginCredentials(-): "+e);
//			e.printStackTrace();
//			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}

//	@PutMapping(value = "/updateCoporateUserProfile")
//	public ResponseEntity<CoporateUserBean> updateCoporateUserProfile(@ModelAttribute CoporateUserBean coporateUserBean){
//		LOGGER.debug("Inside CoporateUserController.updateCoporateUserProfile(-)");
//		CoporateUserBean userBean=null;
//		byte[] image=null;
//		try {
//			LOGGER.debug("Inside try block of CoporateUserController.updateCoporateUserProfile(-)");
//			image=coporateUserBean.getFile().getBytes();
//			coporateUserBean.setProfileImage(image);
//			userBean=coporateUserService.updateCoporateUserProfile(coporateUserBean);
//			if(userBean!=null) {
//				LOGGER.info("Coporate details successfully updated in CoporateUserController.updateCoporateUserProfile(-)");
//				userBean.setPassword(null);
//				return new ResponseEntity<>(userBean,HttpStatus.OK);
//			}
//			else {
//				LOGGER.info("Coporate details not found in CoporateUserController.updateCoporateUserProfile(-)");
//				return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
//			}
//		}
//		catch (IOException e) {
//			LOGGER.error("Problem occured during image to byte[] conversion in CoporateUserController.updateCoporateUserProfile(-): "+e);
//			e.printStackTrace();
//			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//		catch (Exception e) {
//			LOGGER.error("Some problem occured in CoporateUserController.updateCoporateUserProfile(-): "+e);
//			e.printStackTrace();
//			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}

	@PutMapping(value = "/updateCoporateUserProfile")
	@PreAuthorize("hasRole('corporate')")
	public ResponseEntity<UserBean> updateCoporateUserProfile(@ModelAttribute UserBean bean) {
		LOGGER.debug("Inside CoporateUserController.updateCoporateUserProfile(-)");
		UserBean userBean = null;
		byte[] image = null;
		try {
			LOGGER.debug("Inside try block of CoporateUserController.updateCoporateUserProfile(-)");
			image = bean.getFile().getBytes();
			bean.setImage(image);
			userBean = coporateUserService.updateCoporateUserProfile(bean);
			if (userBean != null) {
				LOGGER.info(
						"Coporate details successfully updated in CoporateUserController.updateCoporateUserProfile(-)");
				userBean.setPassword(null);
				return new ResponseEntity<>(userBean, HttpStatus.OK);
			} else {
				LOGGER.info("Coporate details not found in CoporateUserController.updateCoporateUserProfile(-)");
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
		} catch (IOException e) {
			LOGGER.error(
					"Problem occured during image to byte[] conversion in CoporateUserController.updateCoporateUserProfile(-): "
							+ e);
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			LOGGER.error("Some problem occured in CoporateUserController.updateCoporateUserProfile(-): " + e);
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/findCorporateById/{corpUserId}")
	@PreAuthorize("hasRole('corporate')")
	public ResponseEntity<UserBean> findCorporateById(@PathVariable Long corpUserId) {
		LOGGER.debug("Inside CoporateUserController.findCorporateById(-)");
		UserBean userBean = null;
		try {
			LOGGER.debug("Inside try block of CoporateUserController.findCorporateById(-)");
			userBean = coporateUserService.findCorporateById(corpUserId);
			if (userBean != null) {
				LOGGER.info("Corporate details get in CoporateUserController.findCorporateById(-)");
				userBean.setPassword(null);
				return new ResponseEntity<>(userBean, HttpStatus.OK);
			} else {
				LOGGER.info("Coporate details not found in CoporateUserController.findCorporateById(-)");
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			LOGGER.error("Some problem occured in CoporateUserController.findCorporateById(-): " + e);
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/corporateCampusResponse")
	@PreAuthorize("hasRole('corporate')")
	public ResponseEntity<?> corporateCampusResponse(@RequestBody CampusDriveResponseBean campus) {

		LOGGER.debug("Inside CoporateUserController.corporateCampusResponse(-)");

		CampusDriveResponseBean driveResponseBean = new CampusDriveResponseBean();
		ResponseEntity<Response> responseEntity = null;
		Response response = new Response();

		try {
			LOGGER.debug("Inside try block of CoporateUserController.corporateCampusResponse(-)");

			driveResponseBean = coporateUserService.corporateCampusResponse(campus);

			LOGGER.info("Status Successfully Updated using CoporateUserController.corporateCampusResponse(-)");

			responseEntity = new ResponseEntity<>(response, HttpStatus.OK);

			response.setMessage("Request Accepted Successfully...");
			response.setStatus("Success");
			response.setResponseCode(responseEntity.getStatusCodeValue());
			response.setData(driveResponseBean);

		} catch (Exception e) {
			LOGGER.error("Status Updation failed in CoporateUserController.corporateCampusResponse(-): " + e);
			e.printStackTrace();
			responseEntity = new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			response.setMessage(e.getMessage());
			response.setStatus("Failed");
			response.setResponseCode(responseEntity.getStatusCodeValue());
		}
		return responseEntity;
	}
}
