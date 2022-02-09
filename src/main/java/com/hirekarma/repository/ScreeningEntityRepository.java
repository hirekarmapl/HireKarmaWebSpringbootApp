package com.hirekarma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hirekarma.model.ScreeningEntity;

@Repository("screeningEntityRepository")
public interface ScreeningEntityRepository extends JpaRepository<ScreeningEntity, Long>{
	
	@Query("select u.screeningTableId from ScreeningEntity u where u.slug = ?1")
	Long findScreeningEntityIdBySlug(String slug);
}
