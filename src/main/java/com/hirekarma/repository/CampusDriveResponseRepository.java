package com.hirekarma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hirekarma.model.CampusDriveResponse;

@Repository("campusDriveResponseRepository")
public interface CampusDriveResponseRepository extends JpaRepository<CampusDriveResponse, Long> {

	@Query("select count(*) from CampusDriveResponse where universityId = :universityId and corporateId = :corporateId and jobId = :jobId")
	Long findSharedCampus(@Param("universityId") Long universityId, @Param("corporateId") Long corporateId,
			@Param("jobId") Long jobId);
//	Long findRegisteredStudentForCampus();
}
