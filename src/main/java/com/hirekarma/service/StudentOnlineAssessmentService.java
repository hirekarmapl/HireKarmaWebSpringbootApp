package com.hirekarma.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hirekarma.model.OnlineAssessment;
import com.hirekarma.model.Student;
import com.hirekarma.model.StudentOnlineAssessment;

@Service
public interface StudentOnlineAssessmentService {

	public List<StudentOnlineAssessment> createByListOfStudent(List<Student> students,OnlineAssessment onlineAssessment);
}
