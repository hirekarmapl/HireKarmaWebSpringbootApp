package com.hirekarma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hirekarma.model.ScreeninQuestionOptions;

@Repository("screeninQuestionOptionsRepository")
public interface ScreeninQuestionOptionsRepository extends JpaRepository<ScreeninQuestionOptions, Long>{

}
