package com.hirekarma.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTempLateConfiguration {
	
	@Bean
//	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
