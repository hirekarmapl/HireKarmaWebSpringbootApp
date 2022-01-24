package com.hirekarma.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hirekarma.beans.AdminShareJobToUniversityBean;
import com.hirekarma.beans.CampusDriveResponseBean;
import com.hirekarma.beans.Response;
import com.hirekarma.beans.UniversityJobShareToStudentBean;
import com.hirekarma.exception.UserProfileException;
import com.hirekarma.service.UniversityService;
import com.hirekarma.utilty.Validation;

@RestController("universityController")
@CrossOrigin
@RequestMapping("/hirekarma/")
public class UniversityController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UniversityController.class);

	@Autowired
	private UniversityService universityService;

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
			@RequestBody UniversityJobShareToStudentBean universityJobShareToStudentBean,@RequestHeader(value = "Authorization")String token) {
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
	public ResponseEntity<Response> campusDriveRequest(@RequestBody CampusDriveResponseBean campus,@RequestHeader(value = "Authorization")String token) {
		LOGGER.debug("Inside UniversityController.campusDriveRequest(-)");
		CampusDriveResponseBean driveResponseBean = new CampusDriveResponseBean();
		ResponseEntity<Response> responseEntity = null;
		Response response = new Response();
		try {
			LOGGER.debug("Inside try block of UniversityController.campusDriveRequest(-)");
			driveResponseBean = universityService.campusDriveRequest(campus,token);
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

//	@PostMapping("/shareJobStudent")
//	@PreAuthorize("hasRole('student')")
//	public String shareJobStudent(@RequestBody UniversityJobShareToStudentBean  universityJobShareToStudentBean,@RequestHeader(value = "Authorization")String token) throws Exception
//	{
//		
//		
//		
//		
//		String[] chunks = universityJobShareToStudentBean.getToken().split("\\.");
//		String[] chunks1 = token.split(" ");
//		
//		String[] chunks = chunks1[1].split("\\.");
//		
//		
//		
//		SignatureAlgorithm sa = SignatureAlgorithm.HS256;
//		SecretKeySpec secretKeySpec = new SecretKeySpec(universityJobShareToStudentBean.getCreatedBy().getBytes(), sa.getJcaName());
//		
//		String tokenWithoutSignature = chunks[0] + "." + chunks[1];
//		String signature = chunks[2];
//		
//		DefaultJwtSignatureValidator validator = new DefaultJwtSignatureValidator(sa, secretKeySpec);
//
//		if (!validator.isValid(tokenWithoutSignature, signature)) {
//		    throw new Exception("Could not verify JWT token integrity!");
//		}else {
//			signature +="biswa";
//		}
//		
//		
//		
//		
//		
//		
//		
//		Base64.Decoder decoder = Base64.getUrlDecoder();
//
//		String header = new String(decoder.decode(chunks[0]));
//		String payload = new String(decoder.decode(chunks[1]));
//		JSONParser jsonParser = new JSONParser();
//		Object obj = jsonParser.parse(payload);
//		
//		
//		JSONObject jsonObject = (JSONObject)obj;
//		
//		String name = (String) jsonObject.get("sub");
//		
//		System.out.println("payload : \n\n"+payload+"\n\nName Is : "+name);
//		return  name;
//	}
//
//	
//	@PostMapping("/testing")
//	@PreAuthorize("hasRole('university')")
//	public String shareJobStudent(@RequestParam("email") Long email)
//	{
//		String name = null;
//		if(Validation.phoneNumberValidation(email))
//		{
//			 name = "biswa";
//		}else {
//			name = "ranjan";
//		}
//		
//		return name;
//	}
	
		
}
