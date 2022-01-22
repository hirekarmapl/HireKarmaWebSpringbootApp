package com.hirekarma.serviceimpl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hirekarma.beans.EmailBean;
import com.hirekarma.beans.UserBean;
import com.hirekarma.exception.StudentUserDefindException;
import com.hirekarma.model.UserProfile;
import com.hirekarma.service.SendEmailService;
@Service("sendEmailServiceImpl")
public class SendEmailServiceImpl implements SendEmailService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SendEmailServiceImpl.class);

	@Override
	public EmailBean jobInvitation(EmailBean emailBean) {
		LOGGER.debug("Inside SendEmailServiceImpl.jobInvitation(-)");
		//EmailBean bean 
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return emailBean;
	}
	
//	@Override
//	public UserProfile insert(UserProfile student) {
//		LOGGER.debug("Inside StudentServiceImpl.insert(-)");
//		UserProfile studentReturn=null;
//		HttpHeaders headers=null;
//		Map<String,String> body=null;
//		String reqBodyData=null;
//		HttpEntity<String> requestEntity=null;
//		try {
//			LOGGER.debug("Inside try block of StudentServiceImpl.insert(-)");
//			student.setStatus("Active");
//			student.setUserType("student");
//			student.setPassword(passwordEncoder.encode(student.getPassword()));
//			studentReturn=userRepository.save(student);
//			headers=new HttpHeaders();
//			headers.setContentType(MediaType.APPLICATION_JSON);
//			body=new HashMap<String,String>();
//			body.put("email", student.getEmail());
//			reqBodyData=new ObjectMapper().writeValueAsString(body);
//			requestEntity=new HttpEntity<String>(reqBodyData,headers);
//			restTemplate.exchange(welcomeUrl,HttpMethod.POST,requestEntity,String.class);
//			restTemplate.exchange(getStarted,HttpMethod.POST,requestEntity,String.class);
//			LOGGER.info("Data successfully saved using StudentServiceImpl.insert(-)");
//			return studentReturn;
//		}
//		catch (Exception e) {
//			LOGGER.error("Data Insertion failed using StudentServiceImpl.insert(-): "+e);
//			throw new StudentUserDefindException(e.getMessage());
//		}
//	}
}
