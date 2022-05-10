package com.hirekarma.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.net.MediaType;
import com.hirekarma.beans.BlogBean;
import com.hirekarma.beans.Response;
import com.hirekarma.model.Blog;
import com.hirekarma.model.Corporate;
import com.hirekarma.model.Experience;
import com.hirekarma.service.BlogService;
import com.hirekarma.utilty.Validation;

//======== task remaining ===========
//change authoirzation to admin

@RestController
@CrossOrigin
@RequestMapping("/hirekarma/")
public class BlogController {

	@Autowired
	BlogService blogService;
	@GetMapping("/blog/dummy")
	@PreAuthorize("hasRole('corporate')")
	public BlogBean getDummy() {
		return new BlogBean();
	}
	
//	change it to admin
//	@PreAuthorize("hasRole('student')")
	@RequestMapping(value = "/admin/blog", method = RequestMethod.POST, consumes = "multipart/form-data")
	public ResponseEntity<?> addBlogByAdmin(@RequestPart("data") BlogBean bean, @RequestPart("file") MultipartFile file) {
		try {
		Blog blog =	this.blogService.addBlogByAdmin(bean, file);
			return new ResponseEntity<Response>(new Response("success", 201, "added succesfully", blog, null),
					HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	}


	@GetMapping("/admin/blog/{slug}")
	public ResponseEntity<Response> getBlogByAdminBySlug(@PathVariable("slug")String slug) {
		try {
			Blog blog = this.blogService.getBlogByAdminBySlug(slug);
			return new ResponseEntity<Response>(new Response("success", 201, "added succesfully", blog, null),
						HttpStatus.CREATED);
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
						HttpStatus.BAD_REQUEST);
			}
	}
	
	@DeleteMapping("/admin/blog/{slug}")
	@PreAuthorize("hasRole('admin')")
	public ResponseEntity<Response> deleteBlogBySlug(
			@PathVariable("slug") String slug) {
		try {
			System.out.println(slug);
			this.blogService.deleteAdminBlogBySlug(slug);
			return new ResponseEntity<Response>(new Response("success", 200, "deleted succesfully", null, null),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	}
//	@DeleteMapping("/corporate/blog/{slug}")
//	
//	public ResponseEntity<Response> deleteBlogBySlug(@RequestHeader(value = "Authorization") String token,@PathVariable("slug")String slug) {
//		try {
//			this.blogService.deleteBlogBySlug(token,slug);
//			return new ResponseEntity<Response>(new Response("success", 201, "deleted succesfully", null, null),
//						HttpStatus.CREATED);
//			} catch (Exception e) {
//				return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
//						HttpStatus.BAD_REQUEST);
//			}
//	}
	
	
	
}
