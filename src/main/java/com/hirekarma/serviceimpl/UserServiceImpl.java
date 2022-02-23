package com.hirekarma.serviceimpl;

import java.util.NoSuchElementException;

import javax.security.sasl.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hirekarma.email.controller.EmailController;
import com.hirekarma.email.service.EmailSenderService;
import com.hirekarma.model.UserProfile;
import com.hirekarma.repository.UserRepository;
import com.hirekarma.service.UserService;
import com.hirekarma.utilty.HirekarmaPasswordVerifier;
import com.hirekarma.utilty.Utility;
import com.hirekarma.utilty.Validation;

@Service("UserService")
public class UserServiceImpl implements UserService {

	@Autowired
	EmailSenderService emailSenderService;
	
	@Autowired
	EmailController emailController;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	

	@Autowired
	UserRepository userRepository;
	
	public boolean resetPasswordToken(String email) throws Exception {
		UserProfile userProfile = userRepository.findUserByEmail(email);
		if(userProfile==null) {
			throw new Exception("no such user found");
		}
		String resetPasswordToken = Utility.passwordTokenGenerator();
		userProfile.setResetPasswordToken(resetPasswordToken);
		
		emailController.resetPasswordLink("noreply@hirekarma.org", resetPasswordToken,userProfile.getEmail());
		this.userRepository.save(userProfile);
		return true;
	}
	public boolean validateToken(String token,String email) throws Exception  {
		UserProfile userProfile = userRepository.findUserByEmail(email);
		if(userProfile==null) {
			throw new NoSuchElementException("no such user found");
		}
		if(!(userProfile.getResetPasswordToken().equals(token))) {
			throw new AuthenticationException("unauthorized");
		}
		return true;
	}
	public boolean resetPassword(String newPassword,String oldPassword,String email) throws Exception {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		UserProfile userProfile = userRepository.findUserByEmail(email);
		if(userProfile==null) {
			throw new NoSuchElementException("no such user found");
		}
		boolean isPasswordMatch = bCryptPasswordEncoder.matches(oldPassword, userProfile.getPassword());
		System.out.println(isPasswordMatch);
		if(!isPasswordMatch) {
			System.out.println("not match");
			throw new AuthenticationException("unauthorized");
			
		}
		System.out.println(" match");
		userProfile.setPassword(passwordEncoder.encode(newPassword));
		this.userRepository.save(userProfile);
		return true;
	}
	public boolean updatePassword(String newPassword,String email,String token) throws Exception{
		UserProfile userProfile = userRepository.findUserByEmail(email);
		if(!userProfile.getResetPasswordToken().equals(token)) {
			throw new Exception("Invalid Request");
		}
		System.out.println(userProfile);
		if(userProfile==null) {
			throw new NoSuchElementException("no such user found");
		}
		userProfile.setPassword(passwordEncoder.encode(newPassword));
		this.userRepository.save(userProfile);
		System.out.println(userProfile.getPassword());
		return true;
	}
	@Override
	public String updateAbout(String about,String token) throws Exception {
		String email =  Validation.validateToken(token);
		UserProfile userProfile = userRepository.findUserByEmail(email);
		if(userProfile==null) {
			throw new Exception("invalid token");
		}
		userProfile.setAbout(about);
		userRepository.save(userProfile);
		return about;
	}
	@Override
	public String getAbout(String token) throws Exception {
		String email =  Validation.validateToken(token);
		UserProfile userProfile = userRepository.findUserByEmail(email);
		if(userProfile==null) {
			throw new Exception("invalid token");
		}
		return userProfile.getAbout();
	}
}
