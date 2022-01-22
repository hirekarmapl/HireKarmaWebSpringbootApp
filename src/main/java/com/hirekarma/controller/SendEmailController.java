package com.hirekarma.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirekarma.beans.EmailBean;
import com.hirekarma.service.SendEmailService;

@RestController("sendEmailController")
@CrossOrigin
@RequestMapping("/hirekarma/")
public class SendEmailController {
	
private static final Logger LOGGER = LoggerFactory.getLogger(SendEmailController.class);
	
	@Autowired
	private SendEmailService emailService;
	
	@PostMapping("/jobInvitation")
	public ResponseEntity<EmailBean> jobInvitation(@RequestBody EmailBean emailBean) {
		LOGGER.debug("Inside sendEmailController.jobInvitation(-)");
		EmailBean bean = new EmailBean();
		try {
			LOGGER.debug("Inside try block of sendEmailController.jobInvitation(-)");
			bean=emailService.jobInvitation(emailBean);
			LOGGER.info("Data successfully saved using sendEmailController.jobInvitation(-)");
			return new ResponseEntity<>(bean,HttpStatus.CREATED);
		}
		catch (Exception e) {
			LOGGER.error("Data saving failed in sendEmailController.jobInvitation(-): "+e);
			e.printStackTrace();
			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
