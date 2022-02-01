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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hirekarma.beans.JobBean;
import com.hirekarma.beans.Response;
import com.hirekarma.service.JobService;

@RestController("jobController")
@CrossOrigin
@RequestMapping("/hirekarma/")
public class JobController {

	private static final Logger LOGGER = LoggerFactory.getLogger(JobController.class);
	
	@Autowired
	private JobService jobService;
	
	/*
	 * By using saveJobDetails() corporate can
	 * 
	 * create a job.
	 * 
	 */
	@PostMapping("/saveJobUrl")
	@PreAuthorize("hasRole('corporate')")
	public ResponseEntity<Response> saveJobDetails(@ModelAttribute JobBean jobBean,@RequestHeader(value = "Authorization") String token) {
		LOGGER.debug("Inside JobController.saveJobDetails(-)");
		JobBean bean=null;
		ResponseEntity<Response> responseEntity = null;
		Response response = new Response();
		try {
			LOGGER.debug("Inside try block of JobController.saveJobDetails(-)");
			bean=jobService.insert(jobBean,token);
			LOGGER.info("Data successfully saved using JobController.saveJobDetails(-)");
			responseEntity = new ResponseEntity<>(response, HttpStatus.CREATED);

			response.setMessage("Job Created Successfully...");
			response.setStatus("Success");
			response.setResponseCode(responseEntity.getStatusCodeValue());
			response.setData(bean);
			
		}
		catch (Exception e) {
			LOGGER.error("Data saving failed in JobController.saveJobDetails(-): "+e);
			e.printStackTrace();
			responseEntity = new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			response.setMessage(e.getMessage());
			response.setStatus("Failed");
			response.setResponseCode(responseEntity.getStatusCodeValue());
		}
		return responseEntity;
	}
	
	/*
	 * By calling findJobsByCorporateId() method corporate can
	 * 
	 * view all his/her jobs.
	 * 
	 */
	
	@GetMapping("/findJobs")
	@PreAuthorize("hasRole('corporate')")
	public ResponseEntity<?> findJobsByCorporateId(@RequestHeader(value = "Authorization") String token){
		LOGGER.debug("Inside JobController.findJobsByCorporateId(-)");
		List<JobBean> jobBeans=null;
		ResponseEntity<Response> responseEntity = null;
		Response response = new Response();
		try {
			LOGGER.debug("Inside try block of JobController.findJobsByCorporateId(-)");
			jobBeans=jobService.findJobsByUserId(token);
			LOGGER.info("Data successfully saved using JobController.saveJobDetails(-)");
			responseEntity = new ResponseEntity<>(response, HttpStatus.CREATED);

			response.setMessage("Data Fetched Successfully...");
			response.setStatus("Success");
			response.setResponseCode(responseEntity.getStatusCodeValue());
			response.setData(jobBeans);
		}
		catch (Exception e) {
			LOGGER.error("Data retrive failed in JobController.findJobsByCorporateId(-): "+e);
			e.printStackTrace();
			responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
			response.setMessage(e.getMessage());
			response.setStatus("Failed");
			response.setResponseCode(responseEntity.getStatusCodeValue());
		}
		return responseEntity;
	}
	
	
	/*
	 * This findJobsByCorporateId() method used find out
	 * 
	 * Particular job on the basis of job id.
	 * 
	 */
	
	@GetMapping("/findJobById/{jobId}")
	@PreAuthorize("hasRole('corporate')")
	public ResponseEntity<Response> findJobById(@PathVariable Long jobId){
		LOGGER.debug("Inside JobController.findJobById(-)");
		JobBean jobBean=null;
		ResponseEntity<Response> responseEntity = null;
		Response response = new Response();
		try {
			LOGGER.debug("Inside try block of JobController.findJobById(-)");
			jobBean=jobService.findJobById(jobId);
			if(jobBean!=null) {
				LOGGER.info("Data retrived successfully using JobController.findJobById(-)");
				
				response.setMessage("Data Fetched Successfully...");
				response.setStatus("Success");
				responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
			}
			else {
				LOGGER.info("No data found using JobController.findJobById(-)");
				
				response.setMessage("No data found");
				response.setStatus("Success");
				responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
			}
			
			response.setResponseCode(responseEntity.getStatusCodeValue());
			response.setData(jobBean);
		}
		catch (Exception e) {
			LOGGER.error("Data retrive failed in JobController.findJobById(-): "+e);
			e.printStackTrace();
			responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
			response.setMessage(e.getMessage());
			response.setStatus("Failed");
			response.setResponseCode(responseEntity.getStatusCodeValue());
		}
		return responseEntity;
	}
	

	/*
	 * Here deleteJobById() method used remove the particular
	 * 
	 * job from DB on the basis of job id.
	 * 
	 */
	
	@PutMapping("/deleteJobById/{jobId}")
	@PreAuthorize("hasRole('corporate')")
	public ResponseEntity<Response> deleteJobById(@PathVariable Long jobId,@RequestHeader(value = "Authorization") String token){
		LOGGER.debug("Inside JobController.deleteJobById(-)");
		List<JobBean> beans=null;
		ResponseEntity<Response> responseEntity = null;
		Response response = new Response();
		try {
			LOGGER.debug("Inside try block of JobController.deleteJobById(-)");
			beans=jobService.deleteJobById(jobId,token);
			LOGGER.info("Data Deleted successfully using JobController.deleteJobById(-)");
			responseEntity = new ResponseEntity<>(response, HttpStatus.OK);

			response.setMessage("Job Removed Successfully...");
			response.setStatus("Success");
			response.setResponseCode(responseEntity.getStatusCodeValue());
			response.setData(beans);
		}
		catch (Exception e) {
			LOGGER.info("Data not deleted using JobController.deleteJobById(-)");
			e.printStackTrace();
			responseEntity = new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			response.setMessage(e.getMessage());
			response.setStatus("Failed");
			response.setResponseCode(responseEntity.getStatusCodeValue());
		}
		return responseEntity;
	}
	
	/*
	 * Here updateJobById() method used update the particular
	 * 
	 * job details on the basis of job id.
	 * 
	 */
	
	@PutMapping("/updateJobById")
	@PreAuthorize("hasRole('corporate')")
	public ResponseEntity<Response> updateJobById(@ModelAttribute JobBean jobBean){
		LOGGER.debug("Inside JobController.updateJobById(-)");
		JobBean bean=null;
		ResponseEntity<Response> responseEntity = null;
		Response response = new Response();
		try {
			LOGGER.debug("Inside try block of JobController.updateJobById(-)");
			bean=jobService.updateJobById(jobBean);
			LOGGER.info("Data successfully updated using JobController.updateJobById(-)");
			responseEntity = new ResponseEntity<>(response, HttpStatus.CREATED);

			response.setMessage("Job Updated Successfully...");
			response.setStatus("Success");
			response.setResponseCode(responseEntity.getStatusCodeValue());
			response.setData(bean);
		}
		catch (Exception e) {
			LOGGER.error("Data saving failed in JobController.updateJobById(-): "+e);
			e.printStackTrace();
			responseEntity = new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			response.setMessage(e.getMessage());
			response.setStatus("Failed");
			response.setResponseCode(responseEntity.getStatusCodeValue());
		}
		return responseEntity;
	}
	
}
