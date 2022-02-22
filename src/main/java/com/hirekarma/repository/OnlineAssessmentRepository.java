package com.hirekarma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hirekarma.model.OnlineAssessment;
@Repository
public interface OnlineAssessmentRepository extends JpaRepository<OnlineAssessment, Integer> {

}
