package com.hirekarma.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirekarma.beans.AuthenticationProvider;
import com.hirekarma.model.UserProfile;
import com.hirekarma.repository.UserRepository;
import com.hirekarma.utilty.Utility;

@Service
public class Oauth2LoginServiceImpl {
	
	@Autowired
	private UserRepository userRepository;
	
	public UserProfile getStudentByEmail(String email) {
		UserProfile user = userRepository.findUserByEmail(email);
		return user;
	}

	
	public void createNewStudentAfterOauthLoginSuccess(String email, String name, AuthenticationProvider provider) {
		UserProfile user = new UserProfile();
		user.setEmail(email);
		user.setName(name);
		user.setAuthProvider(provider);
		user.setPassword(Utility.passwordTokenGenerator());
		
		System.out.println(user.toString());
		userRepository.save(user);
	}

	public void updateOldStudentAfterOauthLoginSuccess(UserProfile bean, String name, AuthenticationProvider google) {
	
		bean.setName(name);
		bean.setAuthProvider(google);
		userRepository.save(bean);
		
	}


}
