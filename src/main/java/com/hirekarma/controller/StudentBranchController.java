package com.hirekarma.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirekarma.model.StudentBranch;
import com.hirekarma.service.StudentBranchService;

@RestController
@CrossOrigin
@RequestMapping("/hirekarma/")
public class StudentBranchController {

	@Autowired
	StudentBranchService studentBranchService;
	@PostMapping("/branch/")
	public StudentBranch addBranch(@RequestBody Map<String, String> request) {
		StudentBranch studentBranch = new StudentBranch();
		studentBranch.setBranchName(request.get("name"));
	
			
		
		try {
			return this.studentBranchService.addStudentBranch(studentBranch,Integer.parseInt(request.get("streamId")) );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
