package com.hirekarma.controller;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.hirekarma.beans.Response;

@RestController
@CrossOrigin
public class RedirectContorller {
	
//	@GetMapping("/university/")
//	public ResponseEntity<Response> redirector(HttpServletRequest httpServletRequest,@PathVariable String id) {
//		try {
//			System.out.println("inside ");
//		 	return new ResponseEntity<Response>(new Response("success", 200, "", json, null), HttpStatus.OK);
//			
//		} catch (Exception e) {
//		 	return new ResponseEntity<Response>(new Response("success", 200, "", null, null), HttpStatus.OK);
//		}
//
//	}
}
