package com.hirekarma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hirekarma.model.ScreeningQuestions;

@Repository("screeningQuestionsRepository")
public interface ScreeningQuestionsRepository extends JpaRepository<ScreeningQuestions, Long>{

}
