package com.hirekarma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hirekarma.model.JobApply;

@Repository("jobApplyRepository")
public interface JobApplyRepository extends JpaRepository<JobApply, Long>{
	
}
