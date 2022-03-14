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
import com.hirekarma.beans.JobResponseBean;
import com.hirekarma.beans.Response;
import com.hirekarma.model.Corporate;
import com.hirekarma.model.Internship;
import com.hirekarma.model.Job;
import com.hirekarma.model.Student;
import com.hirekarma.repository.CorporateRepository;
import com.hirekarma.repository.JobRepository;
import com.hirekarma.repository.StudentRepository;
import com.hirekarma.service.JobService;
import com.hirekarma.utilty.Validation;

@RestController("jobController")
@CrossOrigin
@RequestMapping("/hirekarma/")
public class JobController {

	private static final Logger LOGGER = LoggerFactory.getLogger(JobController.class);
	
	@Autowired
	private JobService jobService;
	
	@Autowired
	private CorporateRepository corporateRepository;
	
	@Autowired
	private JobRepository jobRepository;
	
	@Autowired
	private StudentRepository studentRepository;
	
	/*
	 * By using saveJobDetails() corporate can
	 * 
	 * create a job.
	 * 
	 */
	@PostMapping("/job")
	@PreAuthorize("hasRole('corporate')")
	public ResponseEntity<Response> saveJobDetails(@ModelAttribute JobBean jobBean,@RequestHeader(value = "Authorization") String token) {
		LOGGER.debug("Inside JobController.saveJobDetails(-)");
		Job bean=null;
		ResponseEntity<Response> responseEntity = null;
		Response response = new Response();
		try {
			LOGGER.debug("Inside try block of JobController.saveJobDetails(-)");
			bean=jobService.saveJob(jobBean,token);
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
	
	@GetMapping("/jobs")
	public ResponseEntity<?> getAllJobsAccordingToToken(@RequestHeader(value = "Authorization") String token){
		LOGGER.debug("Inside JobController.findJobsByCorporateId(-)");
		List<JobResponseBean> jobBeans=null;
		ResponseEntity<Response> responseEntity = null;
		Response response = new Response();
		try {
			LOGGER.debug("Inside try block of JobController.findJobsByCorporateId(-)");
			jobBeans=jobService.getAllJobsAccordingToToken(token);
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
	
	@GetMapping("/corporate/campusDrive/jobs")
	@PreAuthorize("hasRole('corporate')")
	public ResponseEntity<Response> findInActiveCampusDriveJobsForCorporate(@RequestHeader("Authorization")String token){
		try {
			String email = Validation.validateToken(token);
			Corporate corporate = corporateRepository.findByEmail(email);
			return new ResponseEntity(new Response("success", HttpStatus.OK, "", this.corporateRepository.findInActiveCampusDriveJobsForCorporate(corporate.getCorporateId()), null),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/admin/jobs")
	@PreAuthorize("hasRole('admin')")
	public ResponseEntity<Response> getAllJobsForAdmin(){
		try {
			
			return new ResponseEntity(new Response("success", HttpStatus.OK, "", this.jobService.getAllJobsForAdmin(), null),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/student/jobs")
	@PreAuthorize("hasRole('student')")
	public ResponseEntity<Response> getAllJobsForStudents(@RequestHeader("Authorization")String token){
		try {
			String email = Validation.validateToken(token);
			
			Student student = this.studentRepository.findByStudentEmail(email);
			return new ResponseEntity(new Response("success", HttpStatus.OK, "", this.jobService.getAllJobsForStudent(student), null),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/admin/job/unactiveJob")
	@PreAuthorize("hasRole('admin')")
	public ResponseEntity<Response> getAllJobsForAdminForActivation(){
		try {
			
			return new ResponseEntity(new Response("success", HttpStatus.OK, "", this.jobRepository.getAllJobsForAdminForActivation(), null),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/job/activeJobs")
	public ResponseEntity<Response> getAllActivatedJobsForAdmin(){
		try {
			
			return new ResponseEntity(new Response("success", HttpStatus.OK, "", this.jobRepository.getAllActivatedJobsForAdmin(), null),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	}
	
	/*
	 * This findJobsByCorporateId() method used find out
	 * 
	 * Particular job on the basis of job id.
	 * 
	 */
	
	@GetMapping("/findJobById/{jobId}")
	@PreAuthorize("hasRole('corporate')")
	public ResponseEntity<Response> findJobById(@PathVariable Long jobId,@RequestHeader(value = "Authorization") String token){
		LOGGER.debug("Inside JobController.findJobById(-)");
		JobBean jobBean=null;
		ResponseEntity<Response> responseEntity = null;
		Response response = new Response();
		try {
			LOGGER.debug("Inside try block of JobController.findJobById(-)");
			jobBean=jobService.findJobById(jobId,token);
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
	public ResponseEntity<Response> updateJobById(@ModelAttribute JobBean jobBean,@RequestHeader(value = "Authorization") String token){
		LOGGER.debug("Inside JobController.updateJobById(-)");
		Job job=null;
		ResponseEntity<Response> responseEntity = null;
		Response response = new Response();
		try {
			LOGGER.debug("Inside try block of JobController.updateJobById(-)");
			job=jobService.updateJobById2(jobBean,token);
			LOGGER.info("Data successfully updated using JobController.updateJobById(-)");
			responseEntity = new ResponseEntity<>(response, HttpStatus.CREATED);

			response.setMessage("Job Updated Successfully...");
			response.setStatus("Success");
			response.setResponseCode(responseEntity.getStatusCodeValue());
			response.setData(job);
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
