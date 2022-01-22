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

import com.hirekarma.beans.Response;
import com.hirekarma.beans.UniversityJobShareToStudentBean;
import com.hirekarma.beans.UserBean;
import com.hirekarma.model.UserProfile;
import com.hirekarma.service.StudentService;

@RestController("studentController")
@CrossOrigin
@RequestMapping("/hirekarma/")
public class StudentController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(StudentController.class);
	
	@Autowired
	private StudentService studentService;
	
//	@PostMapping("/saveStudentUrl")
//	public ResponseEntity<StudentBean> createUser(@RequestBody StudentBean studentBean) {
//		LOGGER.debug("Inside StudentController.createUser(-)");
//		Student student=null;
//		Student studentReturn=null;
//		StudentBean bean=null;
//		try {
//			LOGGER.debug("Inside try block of StudentController.createUser(-)");
//			student=new Student();
//			bean=new StudentBean();
//			BeanUtils.copyProperties(studentBean, student);
//			studentReturn=studentService.insert(student);
//			LOGGER.info("Data successfully saved using StudentController.createUser(-)");
//			BeanUtils.copyProperties(studentReturn,bean);
//			bean.setPassword(null);
//			return new ResponseEntity<>(bean,HttpStatus.CREATED);
//		}
//		catch (Exception e) {
//			LOGGER.error("Data saving failed in StudentController.createUser(-): "+e);
//			e.printStackTrace();
//			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}
	
	@PostMapping("/saveStudentUrl")
	public ResponseEntity<UserBean> createUser(@RequestBody UserBean studentBean) {
		LOGGER.debug("Inside StudentController.createUser(-)");
		UserProfile student=null;
		UserProfile studentReturn=null;
		UserBean bean=null;
		try {
			LOGGER.debug("Inside try block of StudentController.createUser(-)");
			student=new UserProfile();
			bean=new UserBean();
			BeanUtils.copyProperties(studentBean, student);
			studentReturn=studentService.insert(student);
			LOGGER.info("Data successfully saved using StudentController.createUser(-)");
			BeanUtils.copyProperties(studentReturn,bean);
			bean.setPassword(null);
			return new ResponseEntity<>(bean,HttpStatus.CREATED);
		}
		catch (Exception e) {
			LOGGER.error("Data saving failed in StudentController.createUser(-): "+e);
			e.printStackTrace();
			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
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
	
	@PutMapping(value = "/updateStudentProfile")
	@PreAuthorize("hasRole('student')")
	public ResponseEntity<UserBean> updateStudentProfile(@ModelAttribute UserBean studentBean){
		LOGGER.debug("Inside StudentController.updateStudentProfile(-)");
		UserBean bean=null;
		byte[] image=null;
		try {
			LOGGER.debug("Inside try block of StudentController.updateStudentProfile(-)");
			image=studentBean.getFile().getBytes();
			studentBean.setImage(image);
			bean=studentService.updateStudentProfile(studentBean);
			if(bean!=null) {
				LOGGER.info("Coporate details successfully updated in StudentController.updateStudentProfile(-)");
				bean.setPassword(null);
				return new ResponseEntity<>(bean,HttpStatus.OK);
			}
			else {
				LOGGER.info("Coporate details not found in StudentController.updateStudentProfile(-)");
				return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
			}
		}
		catch (IOException e) {
			LOGGER.error("Problem occured during image to byte[] conversion in StudentController.updateStudentProfile(-): "+e);
			e.printStackTrace();
			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		catch (Exception e) {
			LOGGER.error("Some problem occured in StudentController.updateStudentProfile(-): "+e);
			e.printStackTrace();
			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
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
	
	@GetMapping(value = "/findStudentById/{studentId}")
	@PreAuthorize("hasRole('student')")
	public ResponseEntity<UserBean> findStudentById(@PathVariable Long studentId){
		LOGGER.debug("Inside StudentController.findStudentById(-)");
		UserBean bean=null;
		try {
			LOGGER.debug("Inside try block of StudentController.findStudentById(-)");
			bean=studentService.findStudentById(studentId);
			if(bean!=null) {
				LOGGER.info("Corporate details get in StudentController.findStudentById(-)");
				bean.setPassword(null);
				return new ResponseEntity<>(bean,HttpStatus.OK);
			}
			else {
				LOGGER.info("Coporate details not found in StudentController.findStudentById(-)");
				return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
			}
		}
		catch (Exception e) {
			LOGGER.error("Some problem occured in StudentController.findStudentById(-): "+e);
			e.printStackTrace();
			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PostMapping("/studentJobResponse")
	@PreAuthorize("hasRole('corporate')")
	public ResponseEntity<Response> studentJobResponse(@RequestBody UniversityJobShareToStudentBean jobBean)
	{
		LOGGER.debug("Inside StudentController.studentJobResponse(-)");
		UniversityJobShareToStudentBean universityJobShareToStudentBean = new UniversityJobShareToStudentBean();
		ResponseEntity<Response> responseEntity = null;
		Response response = new Response();
		try {
			LOGGER.debug("Inside try block of StudentController.studentJobResponse(-)");
			universityJobShareToStudentBean = studentService.studentJobResponse(jobBean);
			LOGGER.info("Response Successfully Updated using UniversityController.studentJobResponse(-)");
			
			responseEntity = new ResponseEntity<>(response,HttpStatus.ACCEPTED);
			
			response.setMessage("Job Shared Successfully...");
			response.setStatus("Success");
			response.setResponseCode(responseEntity.getStatusCodeValue());
			response.setData(universityJobShareToStudentBean);
			
		}
		catch (Exception e) {
			LOGGER.error("Response Updation failed in StudentController.studentJobResponse(-): "+e);
			e.printStackTrace();
			responseEntity =  new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
			response.setMessage(e.getMessage());
			response.setStatus("Failed");
			response.setResponseCode(responseEntity.getStatusCodeValue());
		}
		return  responseEntity;
	}

}
