package com.hirekarma;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class WelcomeController {
	
	@GetMapping("/")
	public String welcome() {
		return "HireKarma SpringBoot Project";
	}
}
