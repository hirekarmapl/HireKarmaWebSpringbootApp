package com.hirekarma.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hirekarma.model.Job;

@Repository("jobRepository")
public interface JobRepository extends JpaRepository<Job, Long>{
	@Query(value = "select u from Job u where u.deleteStatus='Active' and u.corpUserId = :corpUserId")
	List<Job> findJobsByCorporateId(@Param("corpUserId")Long corpUserId);
}
