package com.hirekarma.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hirekarma.model.AdminShareJobToUniversity;

@Repository("adminShareJobToUniversityRepository")
public interface AdminShareJobToUniversityRepository extends JpaRepository<AdminShareJobToUniversity, Long> {

	@Query("select a.universityResponseStatus , a.shareJobId , j.jobTitle , j.category , "
			+ "j.jobType , j.wfhCheckbox , j.skills , j.city , j.openings , j.salary , j.about , j.description  "
			+ "from AdminShareJobToUniversity a inner join Job j on a.jobId = j.jobId "
			+ "where a.universityId = :id and j.deleteStatus= true")
	List<Object[]> getJobDetailsByUniversityId(@Param("id")Long id);

}
