package com.hirekarma.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hirekarma.model.CampusDriveResponse;
import com.hirekarma.model.University;

@Repository("campusDriveResponseRepository")
public interface CampusDriveResponseRepository extends JpaRepository<CampusDriveResponse, Long> {

	@Query("select count(*) from CampusDriveResponse where universityId = :universityId and corporateId = :corporateId and jobId = :jobId")
	Long findSharedCampus(@Param("universityId") Long universityId, @Param("corporateId") Long corporateId,
			@Param("jobId") Long jobId);

	CampusDriveResponse findByUniversityIdAndJobId(Long universityId,Long jobId);
	@Query("select u from CampusDriveResponse u where u.corporateId = ?1")
	List<CampusDriveResponse> getAllCampusDriveResponseByCorporateId(Long corporateId);
	
	@Query("select u from University u where u.universityId = ?1")
	University getUniversityByUniversityId(Long universityId);

}
