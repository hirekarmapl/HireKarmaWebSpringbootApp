package com.hirekarma.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hirekarma.model.Corporate;
import com.hirekarma.model.Webinar;
@Repository
public interface WebinarRepository extends JpaRepository<Webinar, String> {

	List<Webinar> findByCorporateOrCorporateIsNull(Corporate corporate);
	
//	for student
	@Query("select w from Webinar w "
			+ "where "
			+ "(w.isDisable = FALSE or w.isDisable is null) and "
			+ "(w.isAccepted = TRUE) and "
			+ "w.scheduledAt >= :currentTimeDate")
	List<Webinar> findAllWithScheduledAtAfterAndDisabledIsNullOrDisabledIsNotActiveAndIsAcceptedActive(@Param("currentTimeDate")LocalDateTime localDateTime );


}
