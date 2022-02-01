package com.hirekarma.email.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.hirekarma.beans.EmailBean;
import com.hirekarma.beans.UserBean;

@Service("emailSenderService")
public class EmailSenderService {
	
	private static final Logger Logger = LoggerFactory.getLogger(EmailSenderService.class);
	
	@Autowired
	public JavaMailSender mailSender;
	
	@Value("${spring.mail.username}")
	private String from;
	
	
	public void sendEmailWithoutAttachment(
			String toEmail,
			String body, 
			String subject){
		
		Logger.info("Inside SendEmailWithoutAttachment() Method...");
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		
		mailMessage.setFrom("heydarlinglisten@gmail.com");
		mailMessage.setTo(toEmail);
		mailMessage.setText(body);
		mailMessage.setSubject(subject);
		
		mailSender.send(mailMessage);
		
		Logger.info("Mail Sended Sussessfully WithOut Attachment...");
		
	}
	
	
   public void sendEmailWithoutAttachmentList(List<UserBean> students){
		
		Logger.info("Inside SendEmailWithoutAttachmentList() Method...");
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		for(UserBean student : students)
		{
			mailMessage.setFrom(from);
			mailMessage.setText("Dear "+student.getName()+", Your login id is: "+student.getEmail()+" and password is: "+student.getPassword());
			mailMessage.setSubject("You're in!! Let's get started");
			mailMessage.setTo(student.getEmail());
			mailSender.send(mailMessage);
		}
		Logger.info("MailList Sended Sussessfully WithOut Attachment...");
	}

	
	public void sendEmailListWithAttachment(
			EmailBean emailBean,
			String body, 
			String subject, 
			String attachment) throws MessagingException, IOException{
		
		Logger.info("Inside SendEmailWithAttachment() Method...");
		
		MimeMessage message = mailSender.createMimeMessage();
		
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true);
		try {
		mimeMessageHelper.setFrom("heydarlinglisten@gmail.com");
		mimeMessageHelper.setText(body);
		mimeMessageHelper.setSubject(subject);
		
		FileSystemResource fileSystemResource = new FileSystemResource(new File(attachment));
		
		mimeMessageHelper.addAttachment(fileSystemResource.getFilename(), fileSystemResource);
		
		for(int i = 0;i<emailBean.getToListEmail().size();i++)
		{
			mimeMessageHelper.setTo(emailBean.getToListEmail().get(i));
			mailSender.send(message);
		}
		
		mailSender.send(message);
		
		Logger.info("Attachment Mail Sended Sussessfully...");
		} catch (MessagingException e) {
            e.printStackTrace();
        }
		
	}
	
	public void sendEmailListWithAttachment(EmailBean emailBean) throws MessagingException, IOException{
		
		Logger.info("Inside SendEmailWithAttachment() Method...");
		
		MimeMessage message = mailSender.createMimeMessage();
		
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true);
		try {
		mimeMessageHelper.setFrom("heydarlinglisten@gmail.com");
		mimeMessageHelper.setText(emailBean.getBody());
		mimeMessageHelper.setSubject(emailBean.getSubject());
		
		emailBean.setAttachment("C:\\Users\\biswa.sahoo\\Desktop\\FIELS\\EXT DRM IMG\\believe.png");
		
		FileSystemResource fileSystemResource = new FileSystemResource(new File(emailBean.getAttachment()));
		
		mimeMessageHelper.addAttachment(fileSystemResource.getFilename(), fileSystemResource);
		
		for(int i = 0;i<emailBean.getToListEmail().size();i++)
		{
			mimeMessageHelper.setTo(emailBean.getToListEmail().get(i));
			mailSender.send(message);
		}
		
		mailSender.send(message);
		
		Logger.info("Attachment Mail Sended Sussessfully...");
		} catch (MessagingException e) {
            e.printStackTrace();
        }
		
	}
	
	public void sendHiringMeetEmail(
			EmailBean emailBean,
			String subject) throws MessagingException, IOException{
		
		Logger.info("Inside sendHiringMeetEmail() Method...");
		
		MimeMessage message = mailSender.createMimeMessage();
		 String abc = emailBean.getMeetLink();
		
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true);
		
		String body = "<!doctype html><html><h2>Hey Congrats !!</h2><br>"
				+ " <h3>Please Join On Above Link For Further Process . . .</h3><br><br>"
				+ "<a href='"+ abc +"'><h3><b>link is here</b></h3></a></html>";
//		'https://www.google.com/'
		try {
		mimeMessageHelper.setFrom("heydarlinglisten@gmail.com");
		//mimeMessageHelper.setText(emailBean.getBody());
		mimeMessageHelper.setText(body, true);
		
		mimeMessageHelper.setSubject(subject);
		
		FileSystemResource fileSystemResource = new FileSystemResource(new File("C:\\Users\\biswa.sahoo\\Desktop\\FIELS\\EXT DRM IMG\\msd.jpg"));
		
		mimeMessageHelper.addAttachment(fileSystemResource.getFilename(), fileSystemResource);
		
		for(int i = 0;i<emailBean.getToListEmail().size();i++)
		{
			mimeMessageHelper.setTo(emailBean.getToListEmail().get(i));
			mailSender.send(message);
		}
			
		mailSender.send(message);
		
		Logger.info("sendHiringMeetEmail Sended Sussessfully...");
		} catch (MessagingException e) {
            e.printStackTrace();
        }
		
	}


}