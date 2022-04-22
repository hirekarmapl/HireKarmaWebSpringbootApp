package com.hirekarma.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hirekarma.model.ScreeningEntityParent;
@Repository	
public interface ScreeningEntityParentRepository extends JpaRepository<ScreeningEntityParent, String>{

	List<ScreeningEntityParent> findByCoporateIdAndDeletedFalse(Long coporateId);
	
	List<ScreeningEntityParent> findByUniversityIdAndDeletedFalse(Long universityId);
	
	@Query("select s from ScreeningEntityParent s where s.coporateId is null and s.universityId is null and s.deleted = FALSE ")
	List<ScreeningEntityParent> findByAdmin();
	
}
