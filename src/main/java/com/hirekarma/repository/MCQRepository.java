package com.hirekarma.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hirekarma.model.MCQAnswer;



public interface MCQRepository extends JpaRepository<MCQAnswer, Long> {

	List<MCQAnswer> findAllByUid(String string);

}
