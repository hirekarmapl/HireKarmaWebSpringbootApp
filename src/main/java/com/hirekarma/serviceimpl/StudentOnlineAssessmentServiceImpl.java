package com.hirekarma.serviceimpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirekarma.model.OnlineAssessment;
import com.hirekarma.model.Student;
import com.hirekarma.model.StudentOnlineAssessment;
import com.hirekarma.repository.StudentOnlineAssessmentRepository;
import com.hirekarma.service.StudentOnlineAssessmentService;

@Service("StudentOnlineAssessmentService")
public class StudentOnlineAssessmentServiceImpl implements StudentOnlineAssessmentService {

	@Autowired
	StudentOnlineAssessmentRepository studentOnlineAssessmentRepository;
	
	@Override
	public List<StudentOnlineAssessment> createByListOfStudent(List<Student> students, OnlineAssessment onlineAssessment) {
		List<StudentOnlineAssessment> studentOnlineAssessments = new ArrayList<>();
		for(Student s: students) {
			StudentOnlineAssessment soa = new StudentOnlineAssessment();
			soa.setStudent(s);
			soa.setOnlineAssessment(onlineAssessment);
			studentOnlineAssessments.add(soa);			
		}
		
		return studentOnlineAssessmentRepository.saveAll(studentOnlineAssessments);
		
	}

}
