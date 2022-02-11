package com.hirekarma.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirekarma.model.Category;
import com.hirekarma.repository.CategoryRepository;

@RestController
@CrossOrigin
@RequestMapping("/hirekarma/")
public class CategoryController {

	@Autowired
	CategoryRepository categoryRepository;
	
	@PostMapping("/category")
	@PreAuthorize("hasRole('corporate')")
	public Category add(@RequestBody Category category) {
		return this.categoryRepository.save(category);
	}
	
	@GetMapping("/category/{id}")
	@PreAuthorize("hasRole('corporate')")
	public Category getCategoryById(@PathVariable("id") int id) {
		System.out.println("id--------"+id);
		return this.categoryRepository.findById(id).get();
	
	}
	
	@GetMapping("/category")
	@PreAuthorize("hasRole('corporate')")
	public List<Category> getAllCategories(){
		return this.categoryRepository.findAll();
	}
	
	@PostMapping("/categories")
	@PreAuthorize("hasRole('corporate')")
	public List<Category> add(@RequestBody List<Category> category) {
		return this.categoryRepository.saveAll(category);
	}
	
}
