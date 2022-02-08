package com.hirekarma.controller;

import java.io.IOException;
import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirekarma.beans.Response;
import com.hirekarma.beans.UniversityJobShareToStudentBean;
import com.hirekarma.beans.UserBean;
import com.hirekarma.exception.StudentUserDefindException;
import com.hirekarma.model.UserProfile;
import com.hirekarma.service.StudentService;
import com.hirekarma.utilty.Validation;

@RestController("studentController")
@CrossOrigin
@RequestMapping("/hirekarma/")
public class StudentController {

	private static final Logger LOGGER = LoggerFactory.getLogger(StudentController.class);

	@Autowired
	private StudentService studentService;

	@PostMapping("/saveStudentUrl")
	public ResponseEntity<Response> createUser(@RequestBody UserBean studentBean,BindingResult result) {
		LOGGER.debug("Inside StudentController.createUser(-)");
		UserProfile student = null;
		UserProfile studentReturn = null;
		UserBean bean = null;
		Response response = new Response();
		System.out.println("helllo");
		
		
		ResponseEntity<Response> responseEntity = null;
		try {
			LOGGER.debug("Inside try block of StudentController.createUser(-)");

			if (Validation.validateEmail(studentBean.getEmail())) {

				student = new UserProfile();
				bean = new UserBean();
				BeanUtils.copyProperties(studentBean, student);
				studentReturn = studentService.insert(student);
				LOGGER.info("Data successfully saved using StudentController.createUser(-)");
				BeanUtils.copyProperties(studentReturn, bean);
				bean.setPassword(null);

				responseEntity = new ResponseEntity<>(response, HttpStatus.CREATED);

				response.setMessage("Data Shared Successfully...");
				response.setStatus("Success");
				response.setResponseCode(responseEntity.getStatusCodeValue());
				response.setData(bean);
			} else {
				throw new StudentUserDefindException("Please Enter A Valid Email !!");
			}
		} catch (Exception e) {
			LOGGER.error("Data saving failed in StudentController.createUser(-): " + e);
			e.printStackTrace();

			responseEntity = new ResponseEntity<>(response, HttpStatus.CONFLICT);

			response.setMessage(e.getMessage());
			response.setStatus("Failed");
			response.setResponseCode(responseEntity.getStatusCodeValue());
		}
		return responseEntity;
	}

	@PutMapping(value = "/updateStudentProfile")
	@PreAuthorize("hasRole('student')")
	public ResponseEntity<Response> updateStudentProfile(@ModelAttribute UserBean studentBean,
			@RequestHeader(value = "Authorization") String token) {
		LOGGER.debug("Inside StudentController.updateStudentProfile(-)");
		UserBean bean = null;
		byte[] image = null;
		Response response = new Response();
		ResponseEntity<Response> responseEntity = null;
		try {
			LOGGER.debug("Inside try block of StudentController.updateStudentProfile(-)");

			if (Validation.validateEmail(studentBean.getEmail())) {
				if (Validation.phoneNumberValidation(Long.valueOf(studentBean.getPhoneNo()))) {

					image = studentBean.getFile().getBytes();
					studentBean.setImage(image);
					bean = studentService.updateStudentProfile(studentBean, token);
					if (bean != null) {
						LOGGER.info(
								"Coporate details successfully updated in StudentController.updateStudentProfile(-)");
						bean.setPassword(null);

						responseEntity = new ResponseEntity<>(response, HttpStatus.OK);

						response.setStatus("Success");
						response.setMessage("Data Updated Successfully...");
					} else {
						LOGGER.info("Coporate details not found in StudentController.updateStudentProfile(-)");

						responseEntity = new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

						response.setStatus("Failed");
						response.setMessage("Details Not Found !!");
					}

					response.setStatus("Success");
					response.setResponseCode(responseEntity.getStatusCodeValue());
					response.setData(bean);
				} else {
					throw new StudentUserDefindException("Please Enter A Valid Phone Number !!");
				}
			} else {
				throw new StudentUserDefindException("Please Enter A Valid Email !!");
			}
		} catch (IOException e) {
			LOGGER.error(
					"Problem occured during image to byte[] conversion in StudentController.updateStudentProfile(-): "
							+ e);
			e.printStackTrace();
			responseEntity = new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

			response.setMessage(e.getMessage());
			response.setStatus("Failed");
			response.setResponseCode(responseEntity.getStatusCodeValue());

		} catch (Exception e) {
			LOGGER.error("Some problem occured in StudentController.updateStudentProfile(-): " + e);
			e.printStackTrace();
			responseEntity = new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

			response.setMessage(e.getMessage());
			response.setStatus("Failed");
			response.setResponseCode(responseEntity.getStatusCodeValue());
		}
		return responseEntity;
	}

	@GetMapping(value = "/findStudentById/{studentId}")
	@PreAuthorize("hasRole('student')")
	public ResponseEntity<UserBean> findStudentById(@PathVariable Long studentId) {
		LOGGER.debug("Inside StudentController.findStudentById(-)");
		UserBean bean = null;
		try {
			LOGGER.debug("Inside try block of StudentController.findStudentById(-)");
			bean = studentService.findStudentById(studentId);
			if (bean != null) {
				LOGGER.info("Corporate details get in StudentController.findStudentById(-)");
				bean.setPassword(null);
				return new ResponseEntity<>(bean, HttpStatus.OK);
			} else {
				LOGGER.info("Coporate details not found in StudentController.findStudentById(-)");
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			LOGGER.error("Some problem occured in StudentController.findStudentById(-): " + e);
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/studentJobResponse")
	@PreAuthorize("hasRole('student')")
	public ResponseEntity<Response> studentJobResponse(@RequestBody UniversityJobShareToStudentBean jobBean) {
		LOGGER.debug("Inside StudentController.studentJobResponse(-)");
		UniversityJobShareToStudentBean universityJobShareToStudentBean = new UniversityJobShareToStudentBean();
		ResponseEntity<Response> responseEntity = null;
		Response response = new Response();
		try {
			LOGGER.debug("Inside try block of StudentController.studentJobResponse(-)");
			universityJobShareToStudentBean = studentService.studentJobResponse(jobBean);
			LOGGER.info("Response Successfully Updated using UniversityController.studentJobResponse(-)");

			responseEntity = new ResponseEntity<>(response, HttpStatus.ACCEPTED);

			response.setMessage("Job Shared Successfully...");
			response.setStatus("Success");
			response.setResponseCode(responseEntity.getStatusCodeValue());
			response.setData(universityJobShareToStudentBean);

		} catch (Exception e) {
			LOGGER.error("Response Updation failed in StudentController.studentJobResponse(-): " + e);
			e.printStackTrace();
			responseEntity = new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			response.setMessage(e.getMessage());
			response.setStatus("Failed");
			response.setResponseCode(responseEntity.getStatusCodeValue());
		}
		return responseEntity;
	}

	@RequestMapping("/jobDetails")
	@PreAuthorize("hasRole('student')")
	public ResponseEntity<Response> jobDetails(@RequestHeader(value = "Authorization") String token) {
		LOGGER.debug("Inside studentController.jobDetails(-)");
		List<?> listData = null;
		ResponseEntity<Response> responseEntity = null;
		Response response = new Response();
		try {
			LOGGER.debug("Inside try block of studentController.jobDetails(-)");
			listData = studentService.jobDetails(token);
			LOGGER.info("Response Successfully Updated using studentController.jobDetails(-)");

			responseEntity = new ResponseEntity<>(response, HttpStatus.OK);

			response.setMessage("Data Fetched Successfully...");
			response.setStatus("Success");
			response.setResponseCode(responseEntity.getStatusCodeValue());
			response.setData(listData);

		} catch (Exception e) {
			LOGGER.error("Response Updation failed in studentController.jobDetails(-): " + e);
			e.printStackTrace();
			responseEntity = new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			response.setMessage(e.getMessage());
			response.setStatus("Failed");
			response.setResponseCode(responseEntity.getStatusCodeValue());
		}
		return responseEntity;
	}

//	@PostMapping("/checkStudentLoginCredentials")
//	public ResponseEntity<StudentBean> checkLoginCredentials(@RequestBody LoginBean loginBean) {
//		LOGGER.debug("Inside StudentController.checkLoginCredentials(-)");
//		StudentBean studentBean=null;
//		try {
//			LOGGER.debug("Inside try block of StudentController.checkLoginCredentials(-)");
//			studentBean=studentService.checkLoginCredentials(loginBean.getEmail(), loginBean.getPassword());
//			if(studentBean!=null) {
//				LOGGER.info("Credential matched in StudentController.checkLoginCredentials(-)");
//				return new ResponseEntity<>(studentBean,HttpStatus.ACCEPTED);
//			}
//			else {
//				LOGGER.info("Credential does not matched in StudentController.checkLoginCredentials(-)");
//				return new ResponseEntity<>(null,HttpStatus.NOT_ACCEPTABLE);
//			}
//		}
//		catch (Exception e) {
//			LOGGER.error("Some problem occured in StudentController.checkLoginCredentials(-): "+e);
//			e.printStackTrace();
//			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}

//	@PutMapping(value = "/updateStudentProfile")
//	public ResponseEntity<StudentBean> updateStudentProfile(@ModelAttribute StudentBean studentBean){
//		LOGGER.debug("Inside StudentController.updateStudentProfile(-)");
//		StudentBean bean=null;
//		byte[] image=null;
//		try {
//			LOGGER.debug("Inside try block of StudentController.updateStudentProfile(-)");
//			image=studentBean.getFile().getBytes();
//			studentBean.setProfileImage(image);
//			bean=studentService.updateStudentProfile(studentBean);
//			if(bean!=null) {
//				LOGGER.info("Coporate details successfully updated in StudentController.updateStudentProfile(-)");
//				bean.setPassword(null);
//				return new ResponseEntity<>(bean,HttpStatus.OK);
//			}
//			else {
//				LOGGER.info("Coporate details not found in StudentController.updateStudentProfile(-)");
//				return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
//			}
//		}
//		catch (IOException e) {
//			LOGGER.error("Problem occured during image to byte[] conversion in StudentController.updateStudentProfile(-): "+e);
//			e.printStackTrace();
//			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//		catch (Exception e) {
//			LOGGER.error("Some problem occured in StudentController.updateStudentProfile(-): "+e);
//			e.printStackTrace();
//			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}
//	@GetMapping(value = "/findStudentById/{studentId}")
//	public ResponseEntity<StudentBean> findStudentById(@PathVariable Long studentId){
//		LOGGER.debug("Inside StudentController.findStudentById(-)");
//		StudentBean bean=null;
//		try {
//			LOGGER.debug("Inside try block of StudentController.findStudentById(-)");
//			bean=studentService.findStudentById(studentId);
//			if(bean!=null) {
//				LOGGER.info("Corporate details get in StudentController.findStudentById(-)");
//				bean.setPassword(null);
//				return new ResponseEntity<>(bean,HttpStatus.OK);
//			}
//			else {
//				LOGGER.info("Coporate details not found in StudentController.findStudentById(-)");
//				return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
//			}
//		}
//		catch (Exception e) {
//			LOGGER.error("Some problem occured in StudentController.findStudentById(-): "+e);
//			e.printStackTrace();
//			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}

}
