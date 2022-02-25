package com.hirekarma.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirekarma.model.StudentBranch;
import com.hirekarma.repository.StudentBranchRepository;

@RestController
@CrossOrigin
@RequestMapping("/hirekarma/")
public class StudentBranchController {
	
	@Autowired
	StudentBranchRepository studentBranchRepository;
	@PostMapping("/branch")
	public StudentBranch addBranch(@RequestBody Map<String, String> request) {
		StudentBranch studentBranch = new StudentBranch();
		studentBranch.setBranchName(request.get("name"));
		try {
			return this.studentBranchRepository.save(studentBranch);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@GetMapping("/branchs")
	public List<StudentBranch> findAllBranches(){
		return this.studentBranchRepository.findAll();
	}
	
	
}
