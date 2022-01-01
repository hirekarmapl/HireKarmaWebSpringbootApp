package com.hirekarma.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hirekarma.beans.InternshipBean;
import com.hirekarma.service.InternshipService;

@RestController("internshipController")
@CrossOrigin
@RequestMapping("/hirekarma/")
public class InternshipController {

	private static final Logger LOGGER = LoggerFactory.getLogger(InternshipController.class);
	
	@Autowired
	private InternshipService internshipService;
	
	@PostMapping("/saveInternshipUrl")
	@PreAuthorize("hasRole('corporate')")
	public ResponseEntity<InternshipBean> saveInternshipDetails(@ModelAttribute InternshipBean internshipBean) {
		LOGGER.debug("Inside InternshipController.saveInternshipDetails(-)");
		InternshipBean bean=null;
		try {
			LOGGER.debug("Inside try block of InternshipController.saveInternshipDetails(-)");
			bean=internshipService.insert(internshipBean);
			LOGGER.info("Data successfully saved using InternshipController.saveInternshipDetails(-)");
			return new ResponseEntity<>(bean,HttpStatus.CREATED);
		}
		catch (Exception e) {
			LOGGER.error("Data saving failed in InternshipController.saveInternshipDetails(-): "+e);
			e.printStackTrace();
			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/findInternshipsByCorporateId")
	@PreAuthorize("hasRole('corporate')")
	public ResponseEntity<List<InternshipBean>> findInternshipsByUserId(@RequestParam("userId") Long userId){
		LOGGER.debug("Inside InternshipController.findInternshipsByCorporateId(-)");
		List<InternshipBean> internshipBeans=null;
		try {
			LOGGER.debug("Inside try block of InternshipController.findInternshipsByCorporateId(-)");
			internshipBeans=internshipService.findInternshipsByUserId(userId);
			LOGGER.info("Data successfully saved using InternshipController.saveInternshipDetails(-)");
			return new ResponseEntity<>(internshipBeans,HttpStatus.OK);
		}
		catch (Exception e) {
			LOGGER.error("Data retrive failed in InternshipController.findInternshipsByCorporateId(-): "+e);
			e.printStackTrace();
			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/findInternshipById/{internshipId}")
	@PreAuthorize("hasRole('corporate')")
	public ResponseEntity<InternshipBean> findInternshipById(@PathVariable Long internshipId){
		LOGGER.debug("Inside InternshipController.findInternshipById(-)");
		InternshipBean internshipBean=null;
		try {
			LOGGER.debug("Inside try block of InternshipController.findInternshipById(-)");
			internshipBean=internshipService.findInternshipById(internshipId);
			if(internshipBean!=null) {
				LOGGER.info("Data retrived successfully using InternshipController.findInternshipById(-)");
				return new ResponseEntity<>(internshipBean,HttpStatus.OK);
			}
			else {
				LOGGER.info("No data found using InternshipController.findInternshipById(-)");
				return new ResponseEntity<>(internshipBean,HttpStatus.OK);
			}
		}
		catch (Exception e) {
			LOGGER.error("Data retrive failed in InternshipController.findInternshipById(-): "+e);
			e.printStackTrace();
			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/deleteInternshipById/{internshipId}")
	@PreAuthorize("hasRole('corporate')")
	public ResponseEntity<List<InternshipBean>> deleteInternshipById(@PathVariable Long internshipId){
		LOGGER.debug("Inside InternshipController.deleteInternshipById(-)");
		List<InternshipBean> beans=null;
		try {
			LOGGER.debug("Inside try block of InternshipController.deleteInternshipById(-)");
			beans=internshipService.deleteInternshipById(internshipId);
			LOGGER.info("Data Deleted successfully using InternshipController.deleteInternshipById(-)");
			return new ResponseEntity<List<InternshipBean>>(beans,HttpStatus.OK);
		}
		catch (Exception e) {
			LOGGER.info("Data not deleted using InternshipController.deleteInternshipById(-)");
			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/updateInternshipById")
	@PreAuthorize("hasRole('corporate')")
	public ResponseEntity<InternshipBean> updateInternshipById(@ModelAttribute InternshipBean internshipBean){
		LOGGER.debug("Inside InternshipController.updateInternshipById(-)");
		InternshipBean bean=null;
		try {
			LOGGER.debug("Inside try block of InternshipController.updateInternshipById(-)");
			bean=internshipService.updateInternshipById(internshipBean);
			LOGGER.info("Data successfully updated using InternshipController.updateInternshipById(-)");
			return new ResponseEntity<>(bean,HttpStatus.CREATED);
		}
		catch (Exception e) {
			LOGGER.error("Data saving failed in InternshipController.updateInternshipById(-): "+e);
			e.printStackTrace();
			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
