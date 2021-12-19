package com.hirekarma.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hirekarma.beans.JobBean;
import com.hirekarma.service.JobService;

@RestController("jobController")
@CrossOrigin
@RequestMapping("/hirekarma/")
public class JobController {

	private static final Logger LOGGER = LoggerFactory.getLogger(JobController.class);
	
	@Autowired
	private JobService jobService;
	
	@PostMapping("/saveJobUrl")
	public ResponseEntity<JobBean> saveJobDetails(@ModelAttribute JobBean jobBean) {
		LOGGER.debug("Inside JobController.saveJobDetails(-)");
		JobBean bean=null;
		try {
			LOGGER.debug("Inside try block of JobController.saveJobDetails(-)");
			bean=jobService.insert(jobBean);
			LOGGER.info("Data successfully saved using JobController.saveJobDetails(-)");
			return new ResponseEntity<>(bean,HttpStatus.CREATED);
		}
		catch (Exception e) {
			LOGGER.error("Data saving failed in JobController.saveJobDetails(-): "+e);
			e.printStackTrace();
			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/findJobsByCorporateId")
	public ResponseEntity<List<JobBean>> findJobsByCorporateId(@RequestParam("corpUserId") Long corpUserId){
		LOGGER.debug("Inside JobController.findJobsByCorporateId(-)");
		List<JobBean> jobBeans=null;
		try {
			LOGGER.debug("Inside try block of JobController.findJobsByCorporateId(-)");
			jobBeans=jobService.findJobsByCorporateId(corpUserId);
			LOGGER.info("Data successfully saved using JobController.saveJobDetails(-)");
			return new ResponseEntity<>(jobBeans,HttpStatus.OK);
		}
		catch (Exception e) {
			LOGGER.error("Data retrive failed in JobController.findJobsByCorporateId(-): "+e);
			e.printStackTrace();
			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/findJobById/{jobId}")
	public ResponseEntity<JobBean> findJobById(@PathVariable Long jobId){
		LOGGER.debug("Inside JobController.findJobById(-)");
		JobBean jobBean=null;
		try {
			LOGGER.debug("Inside try block of JobController.findJobById(-)");
			jobBean=jobService.findJobById(jobId);
			if(jobBean!=null) {
				LOGGER.info("Data retrived successfully using JobController.findJobById(-)");
				return new ResponseEntity<>(jobBean,HttpStatus.OK);
			}
			else {
				LOGGER.info("No data found using JobController.findJobById(-)");
				return new ResponseEntity<>(jobBean,HttpStatus.OK);
			}
		}
		catch (Exception e) {
			LOGGER.error("Data retrive failed in JobController.findJobById(-): "+e);
			e.printStackTrace();
			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/deleteJobById/{jobId}")
	public ResponseEntity<List<JobBean>> deleteJobById(@PathVariable Long jobId){
		LOGGER.debug("Inside JobController.deleteJobById(-)");
		List<JobBean> beans=null;
		try {
			LOGGER.debug("Inside try block of JobController.deleteJobById(-)");
			beans=jobService.deleteJobById(jobId);
			LOGGER.info("Data Deleted successfully using JobController.deleteJobById(-)");
			return new ResponseEntity<List<JobBean>>(beans,HttpStatus.OK);
		}
		catch (Exception e) {
			LOGGER.info("Data not deleted using JobController.deleteJobById(-)");
			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/updateJobById")
	public ResponseEntity<JobBean> updateJobById(@ModelAttribute JobBean jobBean){
		LOGGER.debug("Inside JobController.updateJobById(-)");
		JobBean bean=null;
		try {
			LOGGER.debug("Inside try block of JobController.updateJobById(-)");
			bean=jobService.updateJobById(jobBean);
			LOGGER.info("Data successfully updated using JobController.updateJobById(-)");
			return new ResponseEntity<>(bean,HttpStatus.CREATED);
		}
		catch (Exception e) {
			LOGGER.error("Data saving failed in JobController.updateJobById(-): "+e);
			e.printStackTrace();
			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
