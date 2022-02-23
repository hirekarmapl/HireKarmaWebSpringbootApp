package com.hirekarma.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirekarma.model.Stream;
import com.hirekarma.model.StudentBranch;
import com.hirekarma.repository.StreamRepository;
import com.hirekarma.repository.StudentBranchRepository;
import com.hirekarma.service.StudentBranchService;

@Service("StudentBranchService")
public class StudentBranchServiceImpl implements StudentBranchService {

	@Autowired
	StudentBranchRepository studentBranchRepository;
	
	@Autowired
	StreamRepository streamRepository;
	@Override
	public StudentBranch addStudentBranch(StudentBranch studentBranch, int streamId) throws Exception{
		Stream stream = streamRepository.getById(streamId);
		if(stream==null) {
			throw new Exception("no such stream ");
		}
		
		studentBranch.setStream(stream);
		return this.studentBranchRepository.save(studentBranch);

	}

}
