package com.hirekarma.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hirekarma.model.AdminShareJobToUniversity;

@Repository("adminShareJobToUniversityRepository")
public interface AdminShareJobToUniversityRepository extends JpaRepository<AdminShareJobToUniversity, Long> {

	@Query("select a , j  "
			+ "from AdminShareJobToUniversity a inner join Job j on a.jobId = j.jobId "
			+ "where a.universityId = :id and j.deleteStatus= false")
	List<Object[]> getJobDetailsByUniversityId(@Param("id")Long id);
	
	@Query("select u from AdminShareJobToUniversity u where u.jobId = :jobId and u.universityId =:universityId ")
	AdminShareJobToUniversity findByJobIdAndUniversityId(@Param("jobId")Long jobId,@Param("universityId")Long universityId);

}
