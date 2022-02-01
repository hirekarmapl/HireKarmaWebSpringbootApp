//package com.hirekarma.controller;
//
//import java.security.Principal;
//
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController("LoginController")
//@CrossOrigin
//@RequestMapping("/hirekarma/")
//public class LoginController {
//	
//	@GetMapping("/biswa1")
//	public String Home() {
//		return "Welcome Biswa !!";
//	}
//	
//	@GetMapping("/biswa")
//	public Principal Login(Principal principal) {
//		System.out.println("username : "+principal.getName());
//		return principal;
//	}
//
//}
