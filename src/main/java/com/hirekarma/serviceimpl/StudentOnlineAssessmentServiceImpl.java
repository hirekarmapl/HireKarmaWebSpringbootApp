package com.hirekarma.serviceimpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirekarma.beans.StudentOnlineAssessmentAnswerBean;
import com.hirekarma.model.OnlineAssessment;
import com.hirekarma.model.Student;
import com.hirekarma.model.StudentOnlineAssessment;
import com.hirekarma.model.StudentOnlineAssessmentAnswer;
import com.hirekarma.repository.OnlineAssessmentRepository;
import com.hirekarma.repository.StudentOnlineAssessmentAnswerRepository;
import com.hirekarma.repository.StudentOnlineAssessmentRepository;
import com.hirekarma.repository.StudentRepository;
import com.hirekarma.service.StudentOnlineAssessmentService;

@Service("StudentOnlineAssessmentService")
public class StudentOnlineAssessmentServiceImpl implements StudentOnlineAssessmentService {

	@Autowired
	StudentOnlineAssessmentRepository studentOnlineAssessmentRepository;
	
	@Autowired
	StudentOnlineAssessmentAnswerRepository studentOnlineAssessmentAnswerRepository;
	
	@Autowired
	StudentRepository studentRepository;
	
	@Autowired
	OnlineAssessmentRepository onlineAssessmentRepository;
	
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
	@Override
	public Map<String,Object> getAllAnswerByAStudent(StudentOnlineAssessmentAnswerBean studentOnlineAssessmentAnswerBean) throws Exception{
		Optional<Student> optionalStudent = this.studentRepository.findById(studentOnlineAssessmentAnswerBean.getStudentId());
		if(!optionalStudent.isPresent()) {
			throw new Exception("no such student found");
		}
		Optional<StudentOnlineAssessment> optionalStudentOnlineAssessment = this.studentOnlineAssessmentRepository.findById(studentOnlineAssessmentAnswerBean.getStudentAssesmentSlug());
		if(!optionalStudentOnlineAssessment.isPresent()) {
			throw new Exception("invalid slug");
		}
		StudentOnlineAssessment studentOnlineAssessment = optionalStudentOnlineAssessment.get();		
		OnlineAssessment onlineAssessment = studentOnlineAssessment.getOnlineAssessment();
		if(onlineAssessment==null) {
			throw new Exception("invalid request");
		}
		onlineAssessment.setQuestionANdanswers(null);
		Map<String, Object> response = new HashMap<String, Object>();
		List<StudentOnlineAssessmentAnswer>  studentOnlineAssessmentAnswers = this.studentOnlineAssessmentAnswerRepository.findByStudentAndOnlineAssessment(optionalStudent.get(), onlineAssessment);
		response.put("studentOnlineAssessment", studentOnlineAssessment);
		response.put("studentOnlineAssessmentAnswers",studentOnlineAssessmentAnswers);
		response.put("student", optionalStudent.get());
		response.put("onlineAssessment", onlineAssessment);
		return response;
		
//		Optional<Student> optionalStudent = this.studentRepository.findById(studentOnlineAssessmentAnswerBean.getStudentId());
//		if(!optionalStudent.isPresent()) {
//			throw new Exception("no such student found");
//		}
//		OnlineAssessment onlineAssessment = this.onlineAssessmentRepository.findBySlug(studentOnlineAssessmentAnswerBean.getOnlineAssessmentSlug());
//		if(onlineAssessment==null) {
//			throw new Exception("no such online asessment found");
//		}
//		onlineAssessment.setQuestionANdanswers(null);
//		StudentOnlineAssessment studentOnlineAssessment = this.studentOnlineAssessmentRepository.findByStudentAndOnlineAssessment(optionalStudent.get(), onlineAssessment);
//		Map<String, Object> response = new HashMap<String, Object>();
//		List<StudentOnlineAssessmentAnswer>  studentOnlineAssessmentAnswers = this.studentOnlineAssessmentAnswerRepository.findByStudentAndOnlineAssessment(optionalStudent.get(), onlineAssessment);
//		response.put("studentOnlineAssessment", studentOnlineAssessment);
//		response.put("studentOnlineAssessmentAnswers",studentOnlineAssessmentAnswers);
//		response.put("student", optionalStudent.get());
//		response.put("onlineAssessment", onlineAssessment);
//		return response;
	}

}
