package com.hirekarma.security.oauth2;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.hirekarma.beans.AuthenticationProvider;
import com.hirekarma.model.UserProfile;
import com.hirekarma.serviceimpl.Oauth2LoginServiceImpl;

@Component
public class Oauth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Oauth2LoginSuccessHandler.class);
	
	@Autowired
	private Oauth2LoginServiceImpl oauth2LoginServiceImpl;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		System.out.println(authentication.toString());
		CustomOauth2User oauth2User = (CustomOauth2User) authentication.getPrincipal();
		System.out.println("inside onAuth");
		String email = oauth2User.getEmail();
		String name = oauth2User.getName();
		System.out.println(oauth2User.getAttributes());
		LOGGER.info("Social Login Email Is : "+email);
		
		UserProfile bean = oauth2LoginServiceImpl.getStudentByEmail(email);
		
		if(bean == null)
		{
			oauth2LoginServiceImpl.createNewStudentAfterOauthLoginSuccess(email, name, AuthenticationProvider.GOOGLE);
		}else {
			oauth2LoginServiceImpl.updateOldStudentAfterOauthLoginSuccess(bean, name, AuthenticationProvider.GOOGLE);
		}
		
		super.onAuthenticationSuccess(request, response, authentication);
	}
	

}
