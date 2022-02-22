package com.hirekarma.service;

import org.springframework.stereotype.Service;

import com.hirekarma.model.StudentBranch;

@Service
public interface StudentBranchService {

	StudentBranch addStudentBranch(StudentBranch studentBranch, int streamId) throws Exception;
	
}
