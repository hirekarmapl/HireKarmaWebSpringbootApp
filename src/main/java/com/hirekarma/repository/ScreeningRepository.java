package com.hirekarma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hirekarma.model.ScreeningModel;

@Repository("screeningRepository")
public interface ScreeningRepository extends JpaRepository<ScreeningModel, Long>{
	
	@Query("select u from ScreeningModel u where u.corporateId = ?1 and u.status = 'Active'")
	ScreeningModel getScreeningQuestionsByCorporateId(Long corporateId);
}
