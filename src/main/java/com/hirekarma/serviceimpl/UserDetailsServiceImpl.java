package com.hirekarma.serviceimpl;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hirekarma.model.UserProfile;
import com.hirekarma.repository.UserRepository;

@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		LOGGER.debug("Inside UserDetailsServiceImpl.loadUserByUsername(-)");
		UserProfile user=userRepository.findUserByEmail(email);
		if (user == null) {
			LOGGER.warn("User not found with email: " +email+" inside UserDetailsServiceImpl.checkLoginCredentials(-)");
			throw new UsernameNotFoundException("Student not found with email: " + email);
		}
		else{
			LOGGER.info("student credential match using UserDetailsServiceImpl.loadUserByUsername(-)");
			return new User(user.getEmail(), user.getPassword(),getAuthorities(user));
		}
	}
	
	private Set<SimpleGrantedAuthority> getAuthorities(UserProfile user) {
		Set<SimpleGrantedAuthority> authorities=new HashSet<SimpleGrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_"+user.getUserType()));
		return authorities;
	}
}
