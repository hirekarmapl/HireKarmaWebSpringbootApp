package com.hirekarma.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirekarma.model.Stream;
import com.hirekarma.model.StudentBranch;
import com.hirekarma.repository.StreamRepository;
import com.hirekarma.repository.StudentBranchRepository;
import com.hirekarma.service.StreamService;

@Service("StreamService")
public class StreamServiceImpl implements StreamService {

	@Autowired
	StreamRepository streamRepository;
	
	@Autowired
	StudentBranchRepository studentBranchRepository;
	
	public Stream addStream(Stream stream) {
		return this.streamRepository.save(stream);
	}
	
public Stream addBranchtoStream(List<Long> branchIds,int streamId) throws Exception {
	List<StudentBranch> branches = new ArrayList<>();
	Stream stream = streamRepository.getById(streamId);
	
	for(Long i: branchIds) {
		StudentBranch studentBranch = studentBranchRepository.getById(i);
		if(studentBranch==null) {
			throw new Exception("ID's are wrong");
		}
		studentBranch.setStream(stream);
		branches.add(studentBranch);
		
	}

	System.out.println(" "+stream);

	return this.streamRepository.save(stream);
	
}
}
