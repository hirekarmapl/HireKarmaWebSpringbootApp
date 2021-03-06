package com.hirekarma.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface UserService {
	boolean resetPasswordToken(String email) throws Exception;
	boolean validateToken(String token,String email) throws Exception;
	boolean resetPassword(String newPassword,String oldPassword,String email) throws Exception;
	boolean updatePassword(String newPassword,String email,String token) throws Exception;
	String updateAbout(String about,String token) throws Exception;
	String getAbout(String token) throws Exception;
	boolean verifyEmailAddress(String token, String email) throws Exception;
}
