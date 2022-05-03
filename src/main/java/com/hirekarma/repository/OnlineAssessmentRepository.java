package com.hirekarma.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hirekarma.model.Corporate;
import com.hirekarma.model.OnlineAssessment;
import com.hirekarma.model.University;
@Repository
public interface OnlineAssessmentRepository extends JpaRepository<OnlineAssessment, String> {
	OnlineAssessment findBySlug(String slug);
	
	@Query("select o from OnlineAssessment o where o.corporate = :corporate and (o.deleteStatus is null or o.deleteStatus is false)")
	List<OnlineAssessment> findAllByCorporate(@Param("corporate")Corporate corporate);
	
	@Query("select o from OnlineAssessment o where o.university = :university and (o.deleteStatus is null or o.deleteStatus is false)")
	List<OnlineAssessment> findAllByUniversity(@Param("university")University university);
	
	@Query("select o from OnlineAssessment o where o.university is null and o.corporate is null and (o.deleteStatus is null or o.deleteStatus is false)")
	List<OnlineAssessment> findAllByAdmin();
	
	@Query("select o from OnlineAssessment o where o.university is null and o.corporate is null and publicly_available is true and (o.deleteStatus is null or o.deleteStatus is false)")
	List<OnlineAssessment> findAllForPublic();
	
	OnlineAssessment findBySlugAndDeleteStatusFalse(String slug);
}
