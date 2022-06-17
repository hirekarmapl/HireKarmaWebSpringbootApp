package com.hirekarma.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hirekarma.model.OnlineAssessment;
import com.hirekarma.model.Student;
import com.hirekarma.model.StudentOnlineAssessmentAnswer;
@Repository
public interface StudentOnlineAssessmentAnswerRepository extends JpaRepository<StudentOnlineAssessmentAnswer, String> {
public List<StudentOnlineAssessmentAnswer>  findByStudent(Student student);
public List<StudentOnlineAssessmentAnswer> findByStudentAndOnlineAssessment(Student student,OnlineAssessment onlineAssessment);
@Query("select soaa from StudentOnlineAssessmentAnswer soaa "
		+ "inner join Student s on soaa.student = s "
		+ "inner join OnlineAssessment oa on soaa.onlineAssessment = oa "
		+ "inner join StudentOnlineAssessment soa on soaa.studentOnlineAssessment = soa "
		+ "where s.studentId =:studentId and oa.slug = :online_assessment_slug and soa.slug=:student_online_assessment_slug")
public List<StudentOnlineAssessmentAnswer> findStudentAnswers(@Param("studentId") Long studentId,@Param("online_assessment_slug") String online_assessment_slug,@Param("student_online_assessment_slug")String student_online_assessment_slug);
}
