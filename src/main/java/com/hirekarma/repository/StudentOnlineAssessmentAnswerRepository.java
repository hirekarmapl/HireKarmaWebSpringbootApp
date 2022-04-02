package com.hirekarma.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hirekarma.model.OnlineAssessment;
import com.hirekarma.model.Student;
import com.hirekarma.model.StudentOnlineAssessmentAnswer;
@Repository
public interface StudentOnlineAssessmentAnswerRepository extends JpaRepository<StudentOnlineAssessmentAnswer, String> {
public List<StudentOnlineAssessmentAnswer>  findByStudent(Student student);
public List<StudentOnlineAssessmentAnswer> findByStudentAndOnlineAssessment(Student student,OnlineAssessment onlineAssessment); 
}
