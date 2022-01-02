package com.hirekarma.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;

import com.hirekarma.repository.StudentProfExpRepository;
import com.hirekarma.service.StudentProfExpService;

public class StudentProfExpServiceImpl implements StudentProfExpService {
	
	@Autowired
	private StudentProfExpRepository repo;

}
