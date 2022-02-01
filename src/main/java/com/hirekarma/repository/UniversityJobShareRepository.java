package com.hirekarma.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hirekarma.model.UniversityJobShareToStudent;

@Repository("universityJobShareRepository")
public interface UniversityJobShareRepository extends JpaRepository<UniversityJobShareToStudent, Long> {

	@Query("select u from UniversityJobShareToStudent u where u.universityId = :universityId")
	List<UniversityJobShareToStudent> getSharedJobList(@Param("universityId")Long id);

}
	