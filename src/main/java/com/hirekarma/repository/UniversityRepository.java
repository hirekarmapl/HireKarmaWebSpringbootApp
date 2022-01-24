package com.hirekarma.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hirekarma.model.University;

@Repository("universityRepository")
public interface UniversityRepository extends JpaRepository<University, Long> {

	@Query("select u from University u where u.userId = :userId")
	Optional<University> getUniversityDetails(@Param("userId")Long userId);

	@Query("select count(*) from University where universityEmail = :email")
	Long getDetailsByEmail(@Param("email")String lowerCaseEmail);

	@Query("select u from University u where u.universityEmail = :email")
	University findByEmail(@Param("email")String email);

}
