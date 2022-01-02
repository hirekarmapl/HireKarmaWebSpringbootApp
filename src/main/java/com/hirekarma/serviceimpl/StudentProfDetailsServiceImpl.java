package com.hirekarma.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirekarma.repository.StudentProfExpRepository;
import com.hirekarma.service.StudentProfDetailsService;

@Service("StudentProfDetailsServiceImpl")
public class StudentProfDetailsServiceImpl implements StudentProfDetailsService {
	
	@Autowired
	private StudentProfExpRepository repo;

}
