package com.hirekarma.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
	
	@Query("select soa.student,soa.totalMarksObtained from StudentOnlineAssessment soa "
			+ "where soa.onlineAssessment.slug = :onlineAssessmentSlug and soa.onlineAssessment.corporate is null and soa.onlineAssessment.university is null")
	List<Object[]> findLeaderBoardByOnlineAssessment(@Param("onlineAssessmentSlug")String onlineAssessmentSlug);
	
	@Query("select soa.student,soa.totalMarksObtained from StudentOnlineAssessment soa "
			+ "inner join StudentOnlineAssessment soa1 "
			+ "on soa.onlineAssessment = soa1.onlineAssessment "
			+ "where soa.slug = :studentOnlineAssessmentSlug and soa.onlineAssessment.corporate is null and soa.onlineAssessment.university is null")
	List<Object[]> findLeaderBoardByStudentOnlineAssessment(@Param("studentOnlineAssessmentSlug")String studentOnlineAssessmentSlug);
	@Query("select soa.student from StudentOnlineAssessment soa where soa.onlineAssessment = :onlineAssessment")
	List<Student> findStudentByOnlineAssessment(OnlineAssessment onlineAssessment);
	
	@Query("select soa.student,soa.slug from StudentOnlineAssessment soa where soa.onlineAssessment =:onlineAssessment")
	List<Object[]> findStudentOnlineAssessmentAndStudentByOnlineAssessment(@Param("onlineAssessment") OnlineAssessment onlineAssessment);
}
