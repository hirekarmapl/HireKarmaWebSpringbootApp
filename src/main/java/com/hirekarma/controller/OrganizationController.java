package com.hirekarma.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirekarma.beans.OrganizationBean;
import com.hirekarma.beans.Response;
import com.hirekarma.exception.OrganizationUserDefindException;
import com.hirekarma.service.OrganizationService;

@RestController("organizationController")
@CrossOrigin
@RequestMapping("/hirekarma/")
public class OrganizationController {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrganizationController.class);
	
	@Autowired
	private OrganizationService organizationService;
	
	@PutMapping(value = "/updateOrganizationDetails")
	@PreAuthorize("hasRole('corporate')")
	public ResponseEntity<Response> updateOrganizationDetails(@ModelAttribute OrganizationBean organizationBean,@RequestHeader(value = "Authorization") String token){
		LOGGER.debug("Inside OrganizationController.updateOrganizationDetails(-)");
		OrganizationBean userBean=null;
		byte[] image=null;
		ResponseEntity<Response> responseEntity = null;
		Response response = new Response();
		try {
			LOGGER.debug("Inside try block of OrganizationController.updateOrganizationDetails(-)");
			image=organizationBean.getFile().getBytes();
			organizationBean.setLogo(image);
			userBean=organizationService.updateOrganizationDetails(organizationBean,token);
			if(userBean!=null) {
				LOGGER.info("Organization details successfully updated in OrganizationController.updateOrganizationDetails(-)");
				responseEntity = new ResponseEntity<>(response, HttpStatus.OK);

				response.setMessage("Data Updated Successfully...");
				response.setStatus("Success");
			}
			else {
				LOGGER.info("Organization details not found in OrganizationController.updateOrganizationDetails(-)");
				responseEntity = new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

				response.setMessage("Organization details not found...");
				response.setStatus("Success");
			}
			
			
			response.setResponseCode(responseEntity.getStatusCodeValue());
			response.setData(userBean);
		}
		catch (OrganizationUserDefindException e) {
			LOGGER.error("Some problem occured in OrganizationController.updateOrganizationDetails(-): "+e);
			e.printStackTrace();
			responseEntity = new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			response.setMessage(e.getMessage());
			response.setStatus("Failed");
			response.setResponseCode(responseEntity.getStatusCodeValue());
		}
		catch (IOException e) {
			LOGGER.error("Problem occured during image to byte[] conversion in OrganizationController.updateOrganizationDetails(-): "+e);
			e.printStackTrace();
			responseEntity = new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			response.setMessage(e.getMessage());
			response.setStatus("Problem occured during image insertation...");
			response.setResponseCode(responseEntity.getStatusCodeValue());
		} catch (Exception e) {
			e.printStackTrace();
			responseEntity = new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			response.setMessage(e.getMessage());
			response.setStatus("Failed");
			response.setResponseCode(responseEntity.getStatusCodeValue());
		}
		return responseEntity;
	}
	
	@GetMapping(value = "/findOrganizationByCorporateId/{corpUserId}")
	@PreAuthorize("hasRole('corporate')")
	public ResponseEntity<Response> findOrganizationByCorporateId(@PathVariable Long corpUserId,@RequestHeader(value = "Authorization") String token){
		LOGGER.debug("Inside OrganizationController.findOrganizationByCorporateId(-)");
		OrganizationBean userBean=null;
		ResponseEntity<Response> responseEntity = null;
		Response response = new Response();
		try {
			LOGGER.debug("Inside try block of OrganizationController.findOrganizationByCorporateId(-)");
			userBean=organizationService.findOrganizationByUserId(corpUserId,token);
			if(userBean!=null) {
				LOGGER.info("Organization details get in OrganizationController.findOrganizationByCorporateId(-)");
				responseEntity = new ResponseEntity<>(response, HttpStatus.OK);

				response.setMessage("Data Fetched Successfully...");
				response.setStatus("Success");
			}
			else {
				LOGGER.info("Organization details not found in OrganizationController.findOrganizationByCorporateId(-)");
				responseEntity = new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

				response.setMessage("Organization details not found !!");
				response.setStatus("Success");
			}
			
			response.setResponseCode(responseEntity.getStatusCodeValue());
			response.setData(userBean);
		}
		catch (Exception e) {
			LOGGER.error("Some problem occured in OrganizationController.findOrganizationByCorporateId(-): "+e);
			e.printStackTrace();
			responseEntity = new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			response.setMessage(e.getMessage());
			response.setStatus("Failed");
			response.setResponseCode(responseEntity.getStatusCodeValue());
		}
		return responseEntity;
	}
}
