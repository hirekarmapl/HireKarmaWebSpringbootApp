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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirekarma.beans.InternshipApplyBean;
import com.hirekarma.beans.InternshipApplyResponseBean;
import com.hirekarma.beans.Response;
import com.hirekarma.model.Corporate;
import com.hirekarma.model.Skill;
import com.hirekarma.model.Student;
import com.hirekarma.repository.CorporateRepository;
import com.hirekarma.repository.StudentRepository;
import com.hirekarma.service.InternshipApplyService;
import com.hirekarma.utilty.Validation;

@RestController("internshipApplyController")
@CrossOrigin
@RequestMapping("/hirekarma/")
public class InternshipApplyController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(InternshipApplyController.class);
	
	@Autowired
	private InternshipApplyService internshipApplyService;
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private CorporateRepository corporateRepository;
	
	@PostMapping("/applyInternshipUrl")
	@PreAuthorize("hasRole('student')")
	public ResponseEntity<Response> applyInternship(@RequestBody InternshipApplyBean internshipApplyBean,@RequestHeader(value = "Authorization") String token){
		LOGGER.debug("Inside InternshipApplyController.applyInternship()");
		InternshipApplyBean internshipApplyBeanReturn=null;
		ResponseEntity<Response> responseEntity = null;
		Response response = new Response();
		try {
			LOGGER.debug("Inside try block of InternshipApplyController.applyInternship()");
			internshipApplyBeanReturn = internshipApplyService.insert(internshipApplyBean,token);
			LOGGER.info("Internship successfully applied");
			responseEntity = new ResponseEntity<>(response, HttpStatus.CREATED);

			response.setMessage("Internship successfully applied...");
			response.setStatus("Success");
			response.setResponseCode(responseEntity.getStatusCodeValue());
			response.setData(internshipApplyBeanReturn);
		}
		catch (Exception e) {
			LOGGER.error("Internship applying failed");
			e.printStackTrace();
			responseEntity = new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
			response.setMessage(e.getMessage());
			response.setStatus("Failed");
			response.setResponseCode(responseEntity.getStatusCodeValue());
		}
		return responseEntity;
	}
	
	@GetMapping("/student/internship/application")
	@PreAuthorize("hasRole('student')")
	public ResponseEntity<Response> getAllInternshipAppliedByAStudent(@RequestHeader("Authorization")String token)
			throws Exception {
		
		try {
			String email = Validation.validateToken(token);
			Student student =studentRepository.findByStudentEmail(email);
			System.out.println(student.getStudentId());
			List<InternshipApplyResponseBean> internshipApplyResponseBeans = this.internshipApplyService.getAllInternshipsForAStudent(student.getStudentId());
			return new ResponseEntity<Response>(new Response("success", 200, "", internshipApplyResponseBeans, null), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/corporate/internship/applied_student")
	@PreAuthorize("hasRole('corporate')")
	public ResponseEntity<Response> getAllInternshipApplicationForSpecificCorporate(@RequestHeader("Authorization")String token)
			throws Exception {
		
		try {
			String email = Validation.validateToken(token);
			Corporate corporate = this.corporateRepository.findByEmail(email);
			
			List<InternshipApplyResponseBean> internshipApplyResponseBeans = this.internshipApplyService.getAllInternshipApplicationForSpecificCorporate(corporate.getCorporateId());
			return new ResponseEntity<Response>(new Response("success", 200, "", internshipApplyResponseBeans, null), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	}
}
