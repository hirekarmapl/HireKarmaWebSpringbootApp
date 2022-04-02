package com.hirekarma.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.hirekarma.beans.StudentOnlineAssessmentAnswerBean;
import com.hirekarma.model.OnlineAssessment;
import com.hirekarma.model.Student;
import com.hirekarma.model.StudentOnlineAssessment;
import com.hirekarma.model.StudentOnlineAssessmentAnswer;

@Service
public interface StudentOnlineAssessmentService {

	public List<StudentOnlineAssessment> createByListOfStudent(List<Student> students,OnlineAssessment onlineAssessment);

	public Map<String,Object> getAllAnswerByAStudent(StudentOnlineAssessmentAnswerBean studentOnlineAssessmentAnswerBean) throws Exception;
}
