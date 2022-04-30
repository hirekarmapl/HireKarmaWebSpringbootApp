package com.hirekarma.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hirekarma.beans.HomePageBean;
import com.hirekarma.beans.Response;
import com.hirekarma.service.HomePageService;

@CrossOrigin
@Controller
public class HomePageController {
	@Autowired
	HomePageService homePageService;

	@PostMapping("/{url}/homePage")
	ResponseEntity<Response> changeHomePageData(@RequestBody HomePageBean homePageBean,@PathVariable("url") String url){
	try {
			System.out.println(url);
			return new ResponseEntity(new Response("success", HttpStatus.OK, "",homePageService.add(homePageBean) , null),
					HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	}
}
