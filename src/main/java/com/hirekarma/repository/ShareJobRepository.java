package com.hirekarma.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hirekarma.model.AdminShareJobToUniversity;

@Repository("shareJobRepository")
public interface ShareJobRepository extends JpaRepository<AdminShareJobToUniversity, Long> {

	@Query("select a from AdminShareJobToUniversity a inner join  Job j on a.jobId = j.jobId where a.universityId = :universityId and a.jobId = :jobId and j.corporateId = :corporateId")
	List<Object> getRequestVerificationDetails(@Param("universityId")Long universityId,@Param("jobId") Long jobId,@Param("corporateId") Long corporateId);

}
