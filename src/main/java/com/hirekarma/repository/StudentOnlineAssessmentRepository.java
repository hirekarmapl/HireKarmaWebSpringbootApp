package com.hirekarma.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hirekarma.model.OnlineAssessment;
import com.hirekarma.model.Student;
import com.hirekarma.model.StudentOnlineAssessment;

@Repository
public interface StudentOnlineAssessmentRepository extends JpaRepository<StudentOnlineAssessment, String> {

	List<StudentOnlineAssessment> findByStudent(Student students);
	
	StudentOnlineAssessment findByStudentAndOnlineAssessment(Student student,OnlineAssessment onlineAssessment);
	
	@Query("select soa.onlineAssessment from StudentOnlineAssessment soa where soa.student = :student")
	List<OnlineAssessment> findOnlineAssessmentByStudent(Student student);
	
	List<StudentOnlineAssessment> findByOnlineAssessment(OnlineAssessment onlineAssessment);
	
	@Query("select soa.student from StudentOnlineAssessment soa where soa.onlineAssessment = :onlineAssessment")
	List<Student> findStudentByOnlineAssessment(OnlineAssessment onlineAssessment);
}
