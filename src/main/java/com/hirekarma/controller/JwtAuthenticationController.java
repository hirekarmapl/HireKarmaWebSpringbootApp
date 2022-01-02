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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hirekarma.beans.JwtRequest;
import com.hirekarma.beans.JwtResponse;
import com.hirekarma.beans.UserBean;
import com.hirekarma.model.UserProfile;
import com.hirekarma.repository.UserRepository;
import com.hirekarma.serviceimpl.UserDetailsServiceImpl;
import com.hirekarma.utilty.JwtUtil;

import io.jsonwebtoken.impl.DefaultClaims;

@RestController
@CrossOrigin
@RequestMapping("/hirekarma/")
public class JwtAuthenticationController {
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtTokenUtil;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
	@Autowired
	private UserRepository userRepository;
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
		String token=null;
		UserDetails userDetails=null;
		JwtResponse jwtResponse=null;
		UserProfile userProfile=null;
		UserBean userBean=null;
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
		}
		catch (Exception e) {
			throw new Exception("Incorrect email or password: "+e.getMessage());
		}
		userDetails=this.userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
		token=this.jwtTokenUtil.generateToken(userDetails);
		userProfile=userRepository.findUserByEmail(authenticationRequest.getEmail());
		userBean=new UserBean();
		BeanUtils.copyProperties(userProfile, userBean);
		jwtResponse=new JwtResponse();
		jwtResponse.setJwtToken(token);
		jwtResponse.setData(userBean);
		return ResponseEntity.ok(jwtResponse);
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