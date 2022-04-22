package com.hirekarma.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hirekarma.model.ScreeningEntity;

@Repository("screeningEntityRepository")
public interface ScreeningEntityRepository extends JpaRepository<ScreeningEntity, Long>{
	
	@Query("select u.screeningTableId from ScreeningEntity u where u.slug = ?1")
	Long findScreeningEntityIdBySlug(String slug);
	
	@Query("select se from ScreeningEntity se where (se.corporateId = :corporateId) AND (se.universityId is null) AND se.deleted = FALSE")
	List<ScreeningEntity> findByCorporateId(@Param("corporateId") Long corporateId);
	

	@Query("select se from ScreeningEntity se where (se.universityId = :universityId ) AND (se.corporateId is null) AND se.deleted = FALSE")
	List<ScreeningEntity> findByUniveristyId(@Param("universityId") Long universityId);
	
	@Query("select se from ScreeningEntity se where (se.universityId is null ) AND (se.corporateId is null) AND se.deleted = FALSE")
	List<ScreeningEntity> findByAdmin();
	
}
