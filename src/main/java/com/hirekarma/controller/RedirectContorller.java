package com.hirekarma.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirekarma.beans.Response;

@RestController
@CrossOrigin
@RequestMapping("/hirekarma/")
public class RedirectContorller {
	
//	@GetMapping("/v1/{*id}")
//	public ResponseEntity<Response> redirector(@PathVariable String id) {
//		try {
//			return route(GET("/test/{*id}"), 
//				      serverRequest -> ok().body(fromValue(serverRequest.pathVariable("id"))));
////		 
//		} catch (Exception e) {
//			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
//					HttpStatus.BAD_REQUEST);
//		}
//
//	}
}
