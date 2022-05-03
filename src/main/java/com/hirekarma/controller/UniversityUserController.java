package com.hirekarma.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.hirekarma.beans.Response;
import com.hirekarma.beans.UserBean;
import com.hirekarma.beans.UserBeanResponse;
import com.hirekarma.exception.UniversityUserDefindException;
import com.hirekarma.model.QuestionANdanswer;
import com.hirekarma.model.University;
import com.hirekarma.model.UserProfile;
import com.hirekarma.repository.UniversityRepository;
import com.hirekarma.service.StudentService;
import com.hirekarma.service.UniversityUserService;
import com.hirekarma.utilty.StudentDataExcelGenerator;
import com.hirekarma.utilty.Validation;

@RestController("universityUserController ")
@CrossOrigin
@RequestMapping("/hirekarma/")
public class UniversityUserController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UniversityUserController.class);

	@Autowired
	private UniversityUserService universityUserService;

	@Autowired
	private StudentService studentService;
	
	@Autowired
	private UniversityRepository universityRepository;

//	@PostMapping("/universitySaveUrl")
//	public ResponseEntity<UniversityUserBean> createUser(@RequestBody UniversityUserBean universityUserBean){
//		LOGGER.debug("Inside UniversityUserController.createUser(-)");
//		UniversityUser universityUser=null;
//		UniversityUser universityUserReturn=null;
//		UniversityUserBean userBean=null;	
//		try {
//			LOGGER.debug("Inside try block of UniversityUserController.createUser(-)");
//			universityUser=new UniversityUser();
//			userBean=new UniversityUserBean();
//			BeanUtils.copyProperties(universityUserBean, universityUser);
//			universityUserReturn=universityUserService.insert(universityUser);
//			BeanUtils.copyProperties(universityUserReturn,userBean);
//			LOGGER.info("Data successfully saved using UniversityUserController.createUser(-)");
//			return new ResponseEntity<>(userBean,HttpStatus.CREATED);
//		}
//		catch (Exception e){
//			LOGGER.error("Data saving failed in UniversityUserController.createUser(-): "+e);
//			e.printStackTrace();
//			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
//		}	
//	}

	@PostMapping("/universitySaveUrl")
	public ResponseEntity<Response> createUser(@RequestBody UserBean universityUserBean) {
		LOGGER.debug("Inside UniversityUserController.createUser(-)");
		UserProfile universityUser = null;
		UserProfile universityUserReturn = new UserProfile();
		UserBean userBean = null;
		Response response = new Response();
		ResponseEntity<Response> responseEntity = null;
		try {
			LOGGER.debug("Inside try block of UniversityUserController.createUser(-)");

			if (Validation.validateEmail(universityUserBean.getEmail())) {
//				if (Validation.phoneNumberValidation(Long.valueOf(universityUserBean.getPhoneNo()))) {

					universityUser = new UserProfile();
					userBean = new UserBean();
					BeanUtils.copyProperties(universityUserBean, universityUser);

					universityUserReturn = universityUserService.insert(universityUser);

					BeanUtils.copyProperties(universityUserReturn, userBean);
					LOGGER.info("Data successfully saved using UniversityUserController.createUser(-)");

					responseEntity = new ResponseEntity<>(response, HttpStatus.CREATED);

					response.setMessage("Data Shared Successfully...");
					response.setStatus("Success");
					response.setResponseCode(responseEntity.getStatusCodeValue());
					response.setData(userBean);
//				} else {
//					throw new UniversityUserDefindException("Please Enter A Valid Phone Number !!");
//				}
			} else {
				throw new UniversityUserDefindException("Please Enter A Valid Email !!");
			}

		} catch (Exception e) {
			LOGGER.error("Data saving failed in UniversityUserController.createUser(-): " + e);
			e.printStackTrace();

			responseEntity = new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

			response.setMessage(e.getMessage());
			response.setStatus("Failed");
			response.setResponseCode(responseEntity.getStatusCodeValue());
		}
		return responseEntity;
	}

//	@PostMapping("/checkLoginCredentials")
//	public ResponseEntity<UniversityUserBean> checkLoginCredentials(@RequestBody LoginBean loginBean) {
//		LOGGER.debug("Inside UniversityUserController.checkLoginCredentials(-)");
//		UniversityUserBean UniversityUserBean=null;
//	    try {
//	    	LOGGER.debug("Inside try block of UniversityUserController.checkLoginCredentials(-)");
//	    	UniversityUserBean=universityUserService.checkLoginCredentials(loginBean.getEmail(), loginBean.getPassword());
//	    	if(UniversityUserBean!=null) {
//	    		LOGGER.info("Credential matched in UniversityUserController.checkLoginCredentials(-)");
//	    		return new ResponseEntity<>(UniversityUserBean,HttpStatus.ACCEPTED);
//	    	}
//	    	else {
//	    		LOGGER.info("Credential does not matched in UniversityUserController.checkLoginCredentials(-)");
//	    		return new ResponseEntity<>(null,HttpStatus.NOT_ACCEPTABLE);
//	    	}
//	    }
//		catch(Exception e) {
//			LOGGER.error("Some problem occured in UniversityUserController.checkLoginCredentials(-): "+e);
//			e.printStackTrace();
//			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);	
//		}
//	}

//	@PutMapping(value = "/updateUniversityUserProfile")
//	public ResponseEntity<UniversityUserBean> updateUniversityUserProfile(@ModelAttribute UniversityUserBean universityUserBean){
//		LOGGER.debug("Inside UniversityUserController.updateUniversityUserProfile(-)");
//		UniversityUserBean userBean=null;
//		byte[] image=null;
//		try {
//			LOGGER.debug("Inside try block of UniversityUserController.updateUniversityUserProfile(-)");
//			image=universityUserBean.getFile().getBytes();
//			universityUserBean.setUniversityImage(image);
//			userBean=universityUserService.updateUniversityUserProfile(universityUserBean);
//			if(userBean!=null) {
//				LOGGER.info("Coporate details successfully updated in UniversityUserController.updateUniversityUserProfile(-)");
//				userBean.setPassword(null);
//				return new ResponseEntity<>(userBean,HttpStatus.OK);
//			}
//			else {
//				LOGGER.info("Coporate details not found in UniversityUserController.updateUniversityUserProfile(-)");
//				return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
//			}
//		}
//		catch (IOException e) {
//			LOGGER.error("Problem occured during image to byte[] conversion in UniversityUserController.updateUniversityUserProfile(-): "+e);
//			e.printStackTrace();
//			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//		catch (Exception e) {
//			LOGGER.error("Some problem occured in UniversityUserController.updateUniversityUserProfile(-): "+e);
//			e.printStackTrace();
//			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}

	@PutMapping(value = "/updateUniversityUserProfile")
	@PreAuthorize("hasRole('university')")
	public ResponseEntity<Response> updateUniversityUserProfile(@ModelAttribute UserBean universityUserBean,@RequestHeader(value = "Authorization")String token) {
		LOGGER.debug("Inside UniversityUserController.updateUniversityUserProfile(-)");
		UserBeanResponse userBean = null;
		byte[] image = null;
		Response response = new Response();
		ResponseEntity<Response> responseEntity = null;
		try {
			LOGGER.debug("Inside try block of UniversityUserController.updateUniversityUserProfile(-)");

					userBean = universityUserService.updateUniversityUserProfile(universityUserBean,token);
					if (userBean != null) {
						LOGGER.info(
								"Coporate details successfully updated in UniversityUserController.updateUniversityUserProfile(-)");
						userBean.setPassword(null);
						return new ResponseEntity(new Response("success", HttpStatus.OK, "", userBean, null),
								HttpStatus.OK);
						
					} else {
						LOGGER.info(
								"Coporate details not found in UniversityUserController.updateUniversityUserProfile(-)");

						responseEntity = new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

						response.setStatus("Failed");
						response.setMessage("Details Not Found !!");
					}

					response.setResponseCode(responseEntity.getStatusCodeValue());
					response.setData(userBean);
				
		} catch (Exception e) {
			LOGGER.error("Some problem occured in UniversityUserController.updateUniversityUserProfile(-): " + e);
			e.printStackTrace();
			responseEntity = new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

			response.setMessage(e.getMessage());
			response.setStatus("Failed");
			response.setResponseCode(responseEntity.getStatusCodeValue());
		}
		return responseEntity;
	}

//	@GetMapping(value = "/findUniversityById/{universityId}")
//	public ResponseEntity<UniversityUserBean> findUniversityById(@PathVariable Long universityId){
//		LOGGER.debug("Inside UniversityUserController.findUniversityById(-)");
//		UniversityUserBean userBean=null;
//		try {
//			LOGGER.debug("Inside try block of UniversityUserController.findUniversityById(-)");
//			userBean=universityUserService.findUniversityById(universityId);
//			if(userBean!=null) {
//				LOGGER.info("University details get in UniversityUserController.findUniversityById(-)");
//				userBean.setPassword(null);
//				return new ResponseEntity<>(userBean,HttpStatus.OK);
//			}
//			else {
//				LOGGER.info("University details not found in UniversityUserController.findUniversityById(-)");
//				return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
//			}
//		}
//		catch (Exception e) {
//			LOGGER.error("Some problem occured in UniversityUserController.findUniversityById(-): "+e);
//			e.printStackTrace();
//			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}

	@GetMapping(value = "/findUniversityById/{universityId}")
	@PreAuthorize("hasRole('university')")
	public ResponseEntity<UserBean> findUniversityById(@PathVariable Long universityId) {
		LOGGER.debug("Inside UniversityUserController.findUniversityById(-)");
		UserBean userBean = null;
		try {
			LOGGER.debug("Inside try block of UniversityUserController.findUniversityById(-)");
			userBean = universityUserService.findUniversityById(universityId);
			if (userBean != null) {
				LOGGER.info("University details get in UniversityUserController.findUniversityById(-)");
				userBean.setPassword(null);
				return new ResponseEntity<>(userBean, HttpStatus.OK);
			} else {
				LOGGER.info("University details not found in UniversityUserController.findUniversityById(-)");
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			LOGGER.error("Some problem occured in UniversityUserController.findUniversityById(-): " + e);
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// download student details as excel
	@GetMapping("/exportStudentDataExcel")
	@PreAuthorize("hasRole('university')")
	public ModelAndView exportStudentDataExcel(HttpServletResponse res,@RequestHeader("Authorization")String token) {
		LOGGER.debug("Inside UniversityUserController.exportStudentDataExcel(-)");
		List<UserBean> studentBeans = null;
		Map<String, Object> model = null;
		Date date = null;
		try {
			LOGGER.debug("Inside try block of UniversityUserController.exportStudentDataExcel(-)");
			studentBeans = studentService.getAllStudents(token);
			model = new HashMap<String, Object>();
			date = new Date();
			res.setContentType("application/ms-excel");
			res.setHeader("Content-disposition",
					"inline; filename=students-" + new SimpleDateFormat("yyyy-MM-dd").format(date) + ".xls");
			model.put("Data", studentBeans);
			return new ModelAndView(new StudentDataExcelGenerator(), model);
		} catch (Exception e) {
			LOGGER.error("UniversityUserController.exportStudentDataExcel(-) excel creation failed: " + e.getMessage());
			e.printStackTrace();
			
		}
		return null;
	}

	 @GetMapping("/university/getDummyExcelForStudentImport")
	  public ResponseEntity<Resource> getDummyExcelForStudentImport() {
		 return universityUserService.getDummyExcelForStudentImport();
	 }
	// upload student details as excel
	@PostMapping("/importStudentDataExcel")
	@PreAuthorize("hasRole('university')")
	public ResponseEntity<Response> importStudentDataExcel(@RequestPart("file") MultipartFile file,@RequestHeader("Authorization")String token) {
		LOGGER.debug("Inside UniversityUserController.importStudentDataExcel(-)");
		Map<String,Object> result = new HashMap<String,Object>();
		List<UserBean> studentBeans = null;
		try {
			String email = Validation.validateToken(token);
			University university = universityRepository.findByEmail(email);
			
			result = studentService.importStudentDataExcel(file,token);
			return new ResponseEntity(new Response("success", HttpStatus.OK, "successfully imported", result, null),
					HttpStatus.OK);
		} 
		catch(NullPointerException ne) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, "invalid format", null, null),
					HttpStatus.BAD_REQUEST);
		}
		catch (Exception e) {
			LOGGER.error("UniversityUserController.importStudentDataExcel(-) excel creation failed: " + e.getMessage());
			e.printStackTrace();
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	}

}
