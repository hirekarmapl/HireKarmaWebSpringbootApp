package com.hirekarma;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class HireKarmaSpringBootWebAppApplication extends SpringBootServletInitializer{
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {

        return application.sources(HireKarmaSpringBootWebAppApplication.class);

    }

	public static void main(String[] args) {
		SpringApplication.run(HireKarmaSpringBootWebAppApplication.class, args);
	}
	
	@GetMapping("/")
	public String welcome() {
		return "Welcome to Heroku";
	}

}
