package com.hirekarma.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationResponse;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParser;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.hirekarma.security.oauth2.CustomOauth2User;
@Controller
public class LoginController {

	@GetMapping("/index")
	public String index() {
		return "index";
	}
	@GetMapping("/login/oauth2/code/google")
	public String Home() {
	return "home";
	}

	@GetMapping("/googleLogin")
	public OAuth2AuthenticationToken loginGoogle( OAuth2AuthenticationToken principal) {

		return principal;
	}

}
