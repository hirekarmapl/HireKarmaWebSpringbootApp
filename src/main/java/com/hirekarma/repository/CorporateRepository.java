package com.hirekarma.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hirekarma.model.Corporate;
import com.hirekarma.model.Job;

@Repository("corporateRepository")
public interface CorporateRepository extends JpaRepository<Corporate, Long> {

	@Query("select c from Corporate c where c.corporateEmail = :email")
	Corporate findByEmail(@Param("email")String email);
	
	Corporate findByUserProfile(Long userProfile);
	
	@Query("select j from Job j where j.forcampusDrive=TRUE and deleteStatus=FALSE and j.corporateId= :corporateId")
	List<Job> findInActiveCampusDriveJobsForCorporate(@Param("corporateId")Long corporateId);

}
