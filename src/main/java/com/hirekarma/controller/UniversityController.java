package com.hirekarma.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hirekarma.beans.AdminShareJobToUniversityBean;
import com.hirekarma.beans.BlogBean;
import com.hirekarma.beans.CampusDriveResponseBean;
import com.hirekarma.beans.Response;
import com.hirekarma.beans.UniversityJobShareToStudentBean;
import com.hirekarma.exception.UserProfileException;
import com.hirekarma.model.AdminShareJobToUniversity;
import com.hirekarma.model.Blog;
import com.hirekarma.model.CampusDriveResponse;
import com.hirekarma.model.Job;
import com.hirekarma.model.Student;
import com.hirekarma.model.University;
import com.hirekarma.repository.AdminShareJobToUniversityRepository;
import com.hirekarma.repository.JobRepository;
import com.hirekarma.repository.StudentRepository;
import com.hirekarma.repository.UniversityRepository;
import com.hirekarma.service.UniversityService;
import com.hirekarma.utilty.Validation;

@RestController("universityController")
@CrossOrigin
@RequestMapping("/hirekarma/")
public class UniversityController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UniversityController.class);

	@Autowired
	private UniversityService universityService;
	
	@Autowired
	private AdminShareJobToUniversityRepository adminShareJobToUniversityRepository;
	
	@Autowired
	private JobRepository jobRepository;
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private UniversityRepository universityRepository;
	

	@PostMapping("/universityResponse")
	@PreAuthorize("hasRole('university')")
	public ResponseEntity<?> universityResponse(@RequestBody AdminShareJobToUniversityBean jobBean) {
		LOGGER.debug("Inside UniversityController.universityResponse(-)");
		Map<String, Object> response = new HashMap<String, Object>();
		ResponseEntity<?> responseEntity = null;

		try {
			LOGGER.debug("Inside try block of UniversityController.universityResponse(-)");

			Map<String, Object> details = universityService.universityResponse(jobBean);

			LOGGER.info("Status Successfully Updated using UniversityController.universityResponse(-)");

			responseEntity = new ResponseEntity<>(response, HttpStatus.ACCEPTED);

			response.put("Status", "Success");
			response.put("Response_Code", responseEntity.getStatusCodeValue());
			response.put("Message", "Response Shared Successfully");
			response.put("Data", details.get("result"));

		} catch (Exception e) {
			LOGGER.error("Status Updation failed in UniversityController.universityResponse(-): " + e);
			e.printStackTrace();

			responseEntity = new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

			response.put("Status", "Failed");
			response.put("Response_Code", responseEntity.getStatusCodeValue());
			response.put("Message", e.getMessage());
		}
		return responseEntity;
	}
	 
	@PostMapping("/shareJobStudent")
	@PreAuthorize("hasRole('university')")
	public ResponseEntity<?> shareJobStudent(
			@RequestBody UniversityJobShareToStudentBean universityJobShareToStudentBean,
			@RequestHeader(value = "Authorization") String token) {
		LOGGER.debug("Inside UniversityController.shareJobStudent(-)");

		ResponseEntity<?> responseEntity = null;
		Map<String, Object> response = new HashMap<String, Object>();

		universityJobShareToStudentBean.setToken(token);

		try {
			LOGGER.debug("Inside try block of UniversityController.shareJobStudent(-)");

			if (universityJobShareToStudentBean.getToken() != null && universityJobShareToStudentBean.getToken() != ""
					&& !universityJobShareToStudentBean.getToken().equalsIgnoreCase("null")
					&& !universityJobShareToStudentBean.getToken().isEmpty()
					&& !universityJobShareToStudentBean.getToken().isBlank()) {
				Map<String, Object> details = universityService.shareJobStudent(universityJobShareToStudentBean);

				LOGGER.info("Job Shared Successfully using UniversityController.shareJobStudent(-)");

				responseEntity = new ResponseEntity<>(response, HttpStatus.ACCEPTED);

				response.put("Status", "Success");
				response.put("Response_Code", responseEntity.getStatusCodeValue());
				response.put("Message", "Job Shared Successfully");
				response.put("ToatlSharedJob", details.get("totalSharedJob"));
				response.put("Data", details.get("shareJob"));
			} else {
				throw new UserProfileException("Null Token Can't Be Accepted !!");
			}

		} catch (Exception e) {
			LOGGER.error("Job Sharing failed in UniversityController.shareJobStudent(-): " + e);
			e.printStackTrace();
			responseEntity = new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			response.put("Status", "Failed");
			response.put("Response_Code", responseEntity.getStatusCodeValue());
			response.put("Message", e.getMessage());
		}
		return responseEntity;
	}

	@PostMapping("/campusDriveRequest")
	@PreAuthorize("hasRole('university')")
	public ResponseEntity<Response> campusDriveRequest(@RequestBody CampusDriveResponseBean campus,
			@RequestHeader(value = "Authorization") String token) {
		LOGGER.debug("Inside UniversityController.campusDriveRequest(-)");
		CampusDriveResponseBean driveResponseBean = new CampusDriveResponseBean();
		ResponseEntity<Response> responseEntity = null;
		Response response = new Response();
		try {
			LOGGER.debug("Inside try block of UniversityController.campusDriveRequest(-)");
			driveResponseBean = universityService.campusDriveRequest(campus, token);
			LOGGER.info("Response Successfully Updated using UniversityController.campusDriveRequest(-)");

			responseEntity = new ResponseEntity<>(response, HttpStatus.OK);

			response.setMessage("Request Shared Successfully...");
			response.setStatus("Success");
			response.setResponseCode(responseEntity.getStatusCodeValue());
			response.setData(driveResponseBean);

		} catch (Exception e) {
			LOGGER.error("Response Updation failed in UniversityController.campusDriveRequest(-): " + e);
			e.printStackTrace();
			responseEntity = new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			response.setMessage(e.getMessage());
			response.setStatus("Failed");
			response.setResponseCode(responseEntity.getStatusCodeValue());
		}
		return responseEntity;
	}

	@RequestMapping("/seeShareJobListByAdmin")
	@PreAuthorize("hasRole('university')")
	public ResponseEntity<Response> seeShareJobList(@RequestHeader(value = "Authorization") String token) {
		LOGGER.debug("Inside UniversityController.seeShareJobList(-)");
		List<?> listData = null;
		ResponseEntity<Response> responseEntity = null;
		Response response = new Response();
		try {
			LOGGER.debug("Inside try block of UniversityController.seeShareJobList(-)");
			listData = universityService.seeShareJobList(token);
			LOGGER.info("Response Successfully Updated using UniversityController.seeShareJobList(-)");

			responseEntity = new ResponseEntity<>(response, HttpStatus.OK);

			response.setMessage("Data Fetched Successfully...");
			response.setStatus("Success");
			response.setResponseCode(responseEntity.getStatusCodeValue());
			response.setData(listData);

		} catch (Exception e) {
			LOGGER.error("Response Updation failed in UniversityController.seeShareJobList(-): " + e);
			e.printStackTrace();
			responseEntity = new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			response.setMessage(e.getMessage());
			response.setStatus("Failed");
			response.setResponseCode(responseEntity.getStatusCodeValue());
		}
		return responseEntity;
	}

	@PreAuthorize("hasRole('university')")
	@GetMapping("/university/getAllStudentsReadyForCampusDrive/{shareJobId}")
	public ResponseEntity<Response> getAllStudentsReadyForCampusDriveByShareJobId(@PathVariable("shareJobId") Long shareJobId) {
		try {
			Optional<AdminShareJobToUniversity> optional = adminShareJobToUniversityRepository.findById(shareJobId);
			if(!optional.isPresent()) {
				throw new Exception("invalid share Job Id");
			}
			AdminShareJobToUniversity adminShareJobToUniversity = optional.get();
		
			
			List<Student> students = studentRepository.getAllStudentsReadyForCampusDriveByCampusDriveId(adminShareJobToUniversity.getUniversityId(), adminShareJobToUniversity.getJobId());
			Job job = jobRepository.getById(adminShareJobToUniversity.getJobId());
			Map<Object,Object> map = new HashMap<Object, Object>();
			map.put("students", students);
			map.put("job", job);
			return new ResponseEntity<Response>(new Response("success", 200, "", map, null), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	}
	@RequestMapping("/seeShareJobListToStudent")
	@PreAuthorize("hasRole('university')")
	public ResponseEntity<Response> seeShareJobListToStudent(@RequestHeader(value = "Authorization") String token) {
		LOGGER.debug("Inside UniversityController.seeShareJobListToStudent(-)");
		List<?> listData = null;
		ResponseEntity<Response> responseEntity = null;
		Response response = new Response();
		try {
			LOGGER.debug("Inside try block of UniversityController.seeShareJobListToStudent(-)");
			listData = universityService.seeShareJobListToStudent(token);
			LOGGER.info("Response Successfully Updated using UniversityController.seeShareJobListToStudent(-)");

			responseEntity = new ResponseEntity<>(response, HttpStatus.OK);

			response.setMessage("Data Fetched Successfully...");
			response.setStatus("Success");
			response.setResponseCode(responseEntity.getStatusCodeValue());
			response.setData(listData);

		} catch (Exception e) {
			LOGGER.error("Response Updation failed in UniversityController.seeShareJobListToStudent(-): " + e);
			e.printStackTrace();
			responseEntity = new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			response.setMessage(e.getMessage());
			response.setStatus("Failed");
			response.setResponseCode(responseEntity.getStatusCodeValue());
		}
		return responseEntity;
	}

	@RequestMapping("/studentDetails")
	@PreAuthorize("hasRole('university')")
	public ResponseEntity<Response> studentDetails(@RequestHeader(value = "Authorization") String token) {
		LOGGER.debug("Inside UniversityController.studentDetails(-)");
		List<?> listData = null;
		ResponseEntity<Response> responseEntity = null;
		Response response = new Response();
		try {
			LOGGER.debug("Inside try block of UniversityController.studentDetails(-)");
			listData = universityService.studentDetails(token);
			LOGGER.info("Response Successfully Updated using UniversityController.studentDetails(-)");

			responseEntity = new ResponseEntity<>(response, HttpStatus.OK);

			response.setMessage("Data Fetched Successfully...");
			response.setStatus("Success");
			response.setResponseCode(responseEntity.getStatusCodeValue());
			response.setData(listData);

		} catch (Exception e) {
			LOGGER.error("Response Updation failed in UniversityController.studentDetails(-): " + e);
			e.printStackTrace();
			responseEntity = new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			response.setMessage(e.getMessage());
			response.setStatus("Failed");
			response.setResponseCode(responseEntity.getStatusCodeValue());
		}
		return responseEntity;
	}

	@RequestMapping("/studentFilter")
	@PreAuthorize("hasRole('university')")
	public ResponseEntity<Response> studentFilter(
			@RequestParam("batchId") Long batchId,
			@RequestParam("branchId") Long branchId,
			@RequestParam("cgpa") Double cgpa,
			@RequestHeader(value = "Authorization") String token) {
		LOGGER.debug("Inside UniversityController.studentFilter(-)");
		List<?> listData = null;
		ResponseEntity<Response> responseEntity = null;
		Response response = new Response();
		try {
			String email = Validation.validateToken(token);
			University university = this.universityRepository.findByEmail(email);
			LOGGER.debug("Inside try block of UniversityController.studentFilter(-)");
			listData = studentRepository.findByBatchAndBranchAndUniversityIdAndCgpaGreaterThanEqual(batchId, branchId, university.getUniversityId(), cgpa);
			LOGGER.info("Response Successfully Updated using UniversityController.studentFilter(-)");

			responseEntity = new ResponseEntity<>(response, HttpStatus.OK);

			response.setMessage("Data Fetched Successfully...");
			response.setStatus("Success");
			response.setResponseCode(responseEntity.getStatusCodeValue());
			response.setData(listData);

		} catch (Exception e) {
			LOGGER.error("Response Updation failed in UniversityController.studentFilter(-): " + e);
			e.printStackTrace();
			responseEntity = new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			response.setMessage(e.getMessage());
			response.setStatus("Failed");
			response.setResponseCode(responseEntity.getStatusCodeValue());
		}
		return responseEntity;
	}
	@PreAuthorize("hasRole('university')")
	@GetMapping("/university/shareJobs")
	public ResponseEntity<?> getAllSharedByUniversity(@RequestHeader("Authorization")String token) {
		try {
			String email =  Validation.validateToken(token);
			University university = universityRepository.findByEmail(email);
			Map<String,Object> output = this.universityService.getAllJobsSharedByUniversity(university);
			return new ResponseEntity<Response>(new Response("success", 201, "added succesfully", output.get("data"), null),
					HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('university')")
	@PostMapping("/university/remove_student/{student_id}")
	public ResponseEntity<?> removeStudentFromUniversity(@RequestHeader("Authorization")String token,@PathVariable("student_id")Long studentId) {
		try {
			this.universityService.removeStudentFromUniversity(token, studentId);
			return new ResponseEntity<Response>(new Response("success", 20, " deleted successfully", null, null),
					HttpStatus.CREATED);
		}
		catch(Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	}
}
