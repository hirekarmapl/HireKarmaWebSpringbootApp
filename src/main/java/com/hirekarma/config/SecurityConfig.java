package com.hirekarma.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(1000)
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtRequestFilter jwtRequestFilter;
	
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf()
			.disable()
			.authorizeRequests()
			.antMatchers(
//					"/hirekarma/**"
				"/hirekarma/login","/hirekarma/saveStudentUrl","/hirekarma/account/resetPassword",
				"/hirekarma/account/validate",
				"/hirekarma/account/update",
				"/hirekarma/account/reset",
				"/hirekarma/account/verify",
				"/hirekarma/universitySaveUrl","/hirekarma/saveCorporateUrl",
				"/hirekarma/masterData","/hirekarma/corporateList",
				"/hirekarma/displayJobList",
				"/hirekarma/branch",
				"/hirekarma/branchs",
					"/hirekarma/stream",
				"/hirekarma/streams",
				"/hirekarma/batch",
				"/hirekarma/batch",
				"/hirekarma/university/getDummyExcelForStudentImport",
				"/achievement",
				"/hirekarma/job/activeJobs",
				"/hirekarma/account/request-a-demo",
				"/oauth2_login",
				"/loginSuccess",
				"/loginFailure"
			).permitAll()
			.anyRequest()
			.authenticated()
	        .and()
			.exceptionHandling()
			.authenticationEntryPoint(jwtAuthenticationEntryPoint)
			.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			.oauth2Login()
	        .defaultSuccessUrl("/loginSuccess")
	        .failureUrl("/loginFailure")
	        .loginPage("/oauth2_login");
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
		http.cors();
	}
	
	
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManager();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
