package com.hirekarma.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hirekarma.model.ScreeninQuestionOptions;

@Repository("screeninQuestionOptionsRepository")
public interface ScreeninQuestionOptionsRepository extends JpaRepository<ScreeninQuestionOptions, Long>{

	@Transactional
	@Modifying
	@Query("delete from ScreeninQuestionOptions where screeningTableId = ?1")
	void deleteScreeningQuestionOptions(Long screeningTableId);
}
