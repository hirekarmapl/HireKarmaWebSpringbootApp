package com.hirekarma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hirekarma.model.StudentOnlineAssessmentAnswer;
@Repository
public interface StudentOnlineAssessmentAnswerRepository extends JpaRepository<StudentOnlineAssessmentAnswer, String> {

}
