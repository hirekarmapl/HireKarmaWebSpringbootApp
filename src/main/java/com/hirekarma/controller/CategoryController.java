package com.hirekarma.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirekarma.beans.Response;
import com.hirekarma.model.Category;
import com.hirekarma.model.Corporate;
import com.hirekarma.repository.CategoryRepository;
import com.hirekarma.utilty.Validation;

@RestController
@CrossOrigin
@RequestMapping("/hirekarma/")
public class CategoryController {

	@Autowired
	CategoryRepository categoryRepository;
	
	@PostMapping("/category")
	public ResponseEntity<Response> add(@RequestBody Category category) {
		try {
			Map<String,Object> response = new HashMap();
			
			category.setName(category.getName().toLowerCase().trim());
			Category cat  = this.categoryRepository.findByName(category.getName());
			if(cat==null) {
				this.categoryRepository.save(category);
				
			}
			return new ResponseEntity<Response>(new Response("success", 200, "", response, null), HttpStatus.OK);

		}
		catch(NoSuchElementException ne) {
			return new ResponseEntity(new Response("error", HttpStatus.NOT_FOUND, ne.getMessage(), null, null),
					HttpStatus.NOT_FOUND);
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@GetMapping("/category/{id}")
	public ResponseEntity<Response> getCategoryById(@PathVariable("id") int id) {
		try {
			Map<String,Object> response = new HashMap();
			Optional<Category> categoryOptional =  this.categoryRepository.findById(id);
			if(!categoryOptional.isPresent()) {
				throw new Exception("invalid Id");
			}
			response.put("category", categoryOptional.get());
			return new ResponseEntity<Response>(new Response("success", 200, "", response, null), HttpStatus.OK);
		}
		catch(NoSuchElementException ne) {
			return new ResponseEntity(new Response("error", HttpStatus.NOT_FOUND, ne.getMessage(), null, null),
					HttpStatus.NOT_FOUND);
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/categories")
	public ResponseEntity<Response> getAllCategories(){
		Map<String,Object> response = new HashMap();
		response.put("categories", this.categoryRepository.findAll());
		try {	
			return new ResponseEntity<Response>(new Response("success", 200, "", response, null), HttpStatus.OK);
		}
		catch(NoSuchElementException ne) {
			return new ResponseEntity(new Response("error", HttpStatus.NOT_FOUND, ne.getMessage(), null, null),
					HttpStatus.NOT_FOUND);
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/categories")
	public ResponseEntity<Response> addAllCategories(@RequestBody List<Category> categories) {
		try {	
			Map<String,Object> response = new HashMap();
			List<Category> categoriesToBeSaved = new ArrayList<Category>();
			List<Category> alreadyPresentCategory = this.categoryRepository.findAll();
			for(Category category:categories) {
				category.setName(category.getName().toLowerCase().trim());
				Category cat = this.categoryRepository.findByName(category.getName());
				if(cat==null) {
					categoriesToBeSaved.add(category);
				}
			}
			 this.categoryRepository.saveAll(categoriesToBeSaved);
			 return new ResponseEntity<Response>(new Response("success", 200, "", response, null), HttpStatus.OK);
		}
		catch(NoSuchElementException ne) {
			return new ResponseEntity(new Response("error", HttpStatus.NOT_FOUND, ne.getMessage(), null, null),
					HttpStatus.NOT_FOUND);
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	
	}
	
}
