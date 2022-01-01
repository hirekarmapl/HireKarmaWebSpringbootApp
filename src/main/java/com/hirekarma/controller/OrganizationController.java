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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirekarma.beans.OrganizationBean;
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
	public ResponseEntity<OrganizationBean> updateOrganizationDetails(@ModelAttribute OrganizationBean organizationBean){
		LOGGER.debug("Inside OrganizationController.updateOrganizationDetails(-)");
		OrganizationBean userBean=null;
		byte[] image=null;
		try {
			LOGGER.debug("Inside try block of OrganizationController.updateOrganizationDetails(-)");
			image=organizationBean.getFile().getBytes();
			organizationBean.setLogo(image);
			userBean=organizationService.updateOrganizationDetails(organizationBean);
			if(userBean!=null) {
				LOGGER.info("Organization details successfully updated in OrganizationController.updateOrganizationDetails(-)");
				return new ResponseEntity<>(userBean,HttpStatus.OK);
			}
			else {
				LOGGER.info("Organization details not found in OrganizationController.updateOrganizationDetails(-)");
				return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
			}
		}
		catch (OrganizationUserDefindException e) {
			LOGGER.error("Some problem occured in OrganizationController.updateOrganizationDetails(-): "+e);
			e.printStackTrace();
			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		catch (IOException e) {
			LOGGER.error("Problem occured during image to byte[] conversion in OrganizationController.updateOrganizationDetails(-): "+e);
			e.printStackTrace();
			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(value = "/findOrganizationByCorporateId/{corpUserId}")
	@PreAuthorize("hasRole('corporate')")
	public ResponseEntity<OrganizationBean> findOrganizationByCorporateId(@PathVariable Long corpUserId){
		LOGGER.debug("Inside OrganizationController.findOrganizationByCorporateId(-)");
		OrganizationBean userBean=null;
		try {
			LOGGER.debug("Inside try block of OrganizationController.findOrganizationByCorporateId(-)");
			userBean=organizationService.findOrganizationByUserId(corpUserId);
			if(userBean!=null) {
				LOGGER.info("Organization details get in OrganizationController.findOrganizationByCorporateId(-)");
				return new ResponseEntity<>(userBean,HttpStatus.OK);
			}
			else {
				LOGGER.info("Organization details not found in OrganizationController.findOrganizationByCorporateId(-)");
				return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
			}
		}
		catch (Exception e) {
			LOGGER.error("Some problem occured in OrganizationController.findOrganizationByCorporateId(-): "+e);
			e.printStackTrace();
			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
