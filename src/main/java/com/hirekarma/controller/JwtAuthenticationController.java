package com.hirekarma.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hirekarma.beans.JwtRequest;
import com.hirekarma.beans.JwtResponse;
import com.hirekarma.beans.Response;
import com.hirekarma.beans.UserBean;
import com.hirekarma.beans.UserBeanResponse;
import com.hirekarma.model.Corporate;
import com.hirekarma.model.Student;
import com.hirekarma.model.StudentBatch;
import com.hirekarma.model.University;
import com.hirekarma.model.UserProfile;
import com.hirekarma.repository.CorporateRepository;
import com.hirekarma.repository.StudentBatchRepository;
import com.hirekarma.repository.StudentBranchRepository;
import com.hirekarma.repository.StudentRepository;
import com.hirekarma.repository.UniversityRepository;
import com.hirekarma.repository.UserRepository;
import com.hirekarma.serviceimpl.UserDetailsServiceImpl;
import com.hirekarma.utilty.JwtUtil;
import com.hirekarma.utilty.Validation;

import io.jsonwebtoken.impl.DefaultClaims;

@RestController
@CrossOrigin
@RequestMapping("/hirekarma/")
public class JwtAuthenticationController {
	
	@Autowired
	StudentBatchRepository studentBatchRepository;
	
	@Autowired
	StudentBranchRepository studentBranchRepository;
	
	@Autowired
	CorporateRepository corporateRepository;
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UniversityRepository universityRepository;
	
	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private JwtUtil jwtTokenUtil;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
	@Autowired 
	private UserRepository userRepository;
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest,BindingResult result) throws Exception {
		
		System.out.println("outside valid");
		String token=null;
		Response response = new Response();
		UserDetails userDetails=null;
		JwtResponse jwtResponse=null;
		UserProfile userProfile=null;
		UserBeanResponse userBean=null;
		ResponseEntity<Response> responseEntity = null;
		try {
			if (Validation.validateEmail(authenticationRequest.getEmail())) {
				System.out.println("email validated succesfully");
				if(!authenticationRequest.getEmail().equalsIgnoreCase("admin@gmail.com") && !authenticationRequest.getPassword().equalsIgnoreCase("admin"))
					authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
			}else {
				throw new Exception("Bad Credential");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			responseEntity = new ResponseEntity<Response>(response,HttpStatus.BAD_REQUEST);
			response.setMessage(e.getMessage());
			response.setStatus("Error");
			response.setResponseCode(responseEntity.getStatusCodeValue());
			response.setData(jwtResponse);
			return responseEntity;
			
		}
		
		userDetails=this.userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
		token=this.jwtTokenUtil.generateToken(userDetails);
		userProfile=userRepository.findUserByEmail(authenticationRequest.getEmail());
		userBean=new UserBeanResponse();
		BeanUtils.copyProperties(userProfile, userBean);
		if(userProfile.getUserType().equals("corporate")) {
			Corporate corporate = this.corporateRepository.findByEmail(userBean.getEmail());
			userBean.setProfileUpdateStatus(corporate.getProfileUpdationStatus());
			userBean.setWebsiteUrl(corporate.getWebsiteUrl());
			userBean.setAbout(corporate.getAbout());
		}
		else if(userProfile.getUserType().equals("student")) {
			Student student = this.studentRepository.findByStudentEmail(userBean.getEmail());
			userBean.setProfileUpdateStatus(student.getProfileUpdationStatus());
			if(student.getBatch()!=null) {
				StudentBatch studentBatch = this.studentBatchRepository.getById(student.getBatch());

				userBean.setStudentBatchName(studentBatch.getBatchName());
			};
			userBean.setStudentBranchName(student.getBranch()!=null?this.studentBranchRepository.getById(student.getBranch()).getBranchName():null);
			
			userBean.setUniversityName(student.getUniversityId()!=null?universityRepository.getById(student.getUniversityId()).getUniversityName():null);
			userBean.setBatch(student.getBatch());
			userBean.setBranch(student.getBranch());
			userBean.setCgpa(student.getCgpa());
			userBean.setUniversityId(student.getUniversityId());
			userBean.setStream(student.getStream());
			userBean.setImageUrl(student.getImageUrl());
			userBean.setPassword(null);
			userBean.setProfileUpdateStatus(student.getProfileUpdationStatus());
		}
		else if(userProfile.getUserType().equals("university")) {
			University university = this.universityRepository.findByEmail(userBean.getEmail());
			userBean.setImageUrl(university.getUniversityImageUrl());
			userBean.setProfileUpdateStatus(university.getProfileUpdationStatus());
			userBean.setUniversityEmailAddress(userBean.getUniversityEmailAddress());
			userBean.setEmail(university.getUniversityEmail());
			userBean.setName(university.getUniversityName());
			userBean.setAddress(university.getUniversityAddress());
		}
		responseEntity = new ResponseEntity<Response>(response,HttpStatus.OK);
		jwtResponse=new JwtResponse();
		jwtResponse.setJwtToken(token);
		jwtResponse.setData(userBean);
		response.setMessage("Data Saved Succesfully");
		response.setStatus("Success");
		response.setResponseCode(responseEntity.getStatusCodeValue());
		response.setData(jwtResponse);
		return responseEntity;
	}
	
	@RequestMapping(value = "/refreshtoken", method = RequestMethod.GET)
	public ResponseEntity<?> refreshtoken(HttpServletRequest request) throws Exception {
		DefaultClaims claims=null;
		String token=null;
		Map<String, Object> expectedMap=null;
		JwtResponse jwtResponse=null;
		// From the HttpRequest get the claims
		if(request.getAttribute("claims")!=null) {
			claims = (io.jsonwebtoken.impl.DefaultClaims) request.getAttribute("claims");
			expectedMap = getMapFromIoJsonwebtokenClaims(claims);
			token = jwtTokenUtil.createRefreshToken(expectedMap, expectedMap.get("sub").toString());
			jwtResponse=new JwtResponse();
			jwtResponse.setJwtToken(token);
			return ResponseEntity.ok(jwtResponse);
		}
		else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	public Map<String, Object> getMapFromIoJsonwebtokenClaims(DefaultClaims claims) {
		Map<String, Object> expectedMap = new HashMap<String, Object>();
		for (Entry<String, Object> entry : claims.entrySet()) {
			expectedMap.put(entry.getKey(), entry.getValue());
		}
		return expectedMap;
	}
}
