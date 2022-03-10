package com.hirekarma.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hirekarma.model.JobApply;

@Repository("jobApplyRepository")
public interface JobApplyRepository extends JpaRepository<JobApply, Long>{
	
	@Query("select u from JobApply u where u.studentId = ?1")
	List<JobApply> getAllJobApplicationsByStudentId(Long studentId);
	
	@Query("select u from JobApply u where u.corporateId = :corporateId")
	List<JobApply> getAllJobApplicationsByCorporate(@Param("corporateId")Long corporateId);
	
	JobApply findByStudentIdAndJobId(Long studentId,Long jobId);
	
}
