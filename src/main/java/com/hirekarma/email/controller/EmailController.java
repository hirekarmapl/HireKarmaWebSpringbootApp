package com.hirekarma.email.controller;


import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirekarma.beans.EmailBean;
import com.hirekarma.beans.UserBean;
import com.hirekarma.email.service.EmailSenderService;

@RestController
@RequestMapping("/hirekarma/")
public class EmailController {
	
	private static final Logger Logger = LoggerFactory.getLogger(EmailController.class);
	
	@Autowired
	public EmailSenderService mailSender;
	
	
	/*
	 * This welcomeEmail() send an email to single person
	 * 
	 *  with no attachment
	 * 
	 */
	
	@PostMapping("/welcomeEmail")
	public ResponseEntity<String> welcomeEmail(@RequestBody Map<String,String> map)
	{
		Logger.info("Inside SendingEmailList() Controller...");
		try{
			mailSender.sendEmailWithoutAttachment(map.get("email"),"Welcome to HireKarma Org","Welcome to HireKarma");
			Logger.info("Mail sent using EmailController.SendingEmailList(-)");
			return new ResponseEntity<String>("Mail sended successfully",HttpStatus.OK);
		}
		catch (Exception e) {
			Logger.error("Mail sending failed using EmailController.SendingEmailList(-)");
			return new ResponseEntity<String>("Mail sending Failed!!!",HttpStatus.OK);
		}
	}
	
	
	/*
	 * This welcomeEmailList() sends an email's to list of people
	 * 
	 *  with no attachment
	 * 
	 */
	
	@PostMapping("/welcomeEmailList")
	public ResponseEntity<String> welcomeEmailList(@RequestBody List<UserBean> userBeans)
	{
		Logger.info("Inside SendingEmailList() Controller...");
		try{
			mailSender.sendEmailWithoutAttachmentList(userBeans);
			Logger.info("Mail sent using EmailController.SendingEmailList(-)");
			return new ResponseEntity<String>("Mail sended successfully",HttpStatus.OK);
		}
		catch (Exception e) {
			Logger.error("Mail sending failed using EmailController.SendingEmailList(-)");
			return new ResponseEntity<String>("Mail sending Failed!!!",HttpStatus.OK);
		}
	}
	
	
	/*
	 * This letsGetStarted() is an success email if anyone
	 * 
	 *  selected (with no attachment).
	 * 
	 */
	
	@PostMapping("/letsGetStarted")
	public ResponseEntity<String> letsGetStarted(@RequestBody Map<String,String> map)
	{
		Logger.info("Inside SendingEmailList() Controller...");
		try{
			mailSender.sendEmailWithoutAttachment(map.get("email"),"You're in!! Let's get started","You're in!! Let's get started");
			Logger.info("Mail sent using EmailController.SendingEmailList(-)");
			return new ResponseEntity<String>("Mail sended successfully",HttpStatus.OK);
		}
		catch (Exception e) {
			Logger.error("Mail sending failed using EmailController.SendingEmailList(-)");
			return new ResponseEntity<String>("Mail sending Failed!!!",HttpStatus.OK);
		}
	}
	
	
	/*
	 *  This jobInvitation() is used to send invitation mail to
	 * 
	 *  selected university's(with attachment).
	 * 
	 */

	@GetMapping("/jobInvitationEmail")
	public String jobInvitation(@RequestBody EmailBean emailBean) throws MessagingException
	{
		Logger.info("Inside SendingEmailList() Controller...");
		try {
			mailSender.sendEmailListWithAttachment(emailBean);
		} catch (MessagingException e) {
			e.printStackTrace();
			return "Invitation's Failed";
		} catch (IOException e) {
			e.printStackTrace();
			return "Invitation's Failed";
		}
		return "Invitation's Sended Successfully";
	}
	
	
	/*
	 *  This shortListedApplicant() is used to send confirmation mail to
	 * 
	 *  short listed students(with attachment).
	 * 
	 */
	
	@GetMapping("/shortListedApplicant")
	public String shortListedApplicant(@RequestBody EmailBean emailBean) throws MessagingException
	{
		Logger.info("Inside SendingEmailList() Controller...");
		try {
			mailSender.sendEmailListWithAttachment(emailBean,"Hey Congrats !! You are shortlisted  for this job and shortly we are let we know about our onboarding process till than enjoy champ . . .","Congratulations !!","C:\\Users\\biswa.sahoo\\Desktop\\FIELS\\EXT DRM IMG\\believe.png");
		} catch (MessagingException e) {
			e.printStackTrace();
			return "Failed !!";
		} catch (IOException e) {
			e.printStackTrace();
			return "Failed !!";
		}
		return "Email Sended Successfully";
	}
	
	/*
	 *  This hiringMeetEmail() is used to send email's with a
	 *  
	 *   meet link selected people(with attachment).
	 * 
	 */
	
	@GetMapping("/hiringMeetEmail")
	public String hiringMeetEmail(@RequestBody EmailBean emailBean) throws MessagingException
	{
		Logger.info("Inside SendingEmailList() Controller...");
		try {
			mailSender.sendHiringMeetEmail(emailBean,"Congratulations !!");
		} catch (MessagingException e) {
			e.printStackTrace();
			return "hiringMeetEmail Failed !!";
		} catch (IOException e) {
			e.printStackTrace();
			return "hiringMeetEmail Failed !!";
		}
		return "HiringMeetEmail Sended Successfully";
	}

}
