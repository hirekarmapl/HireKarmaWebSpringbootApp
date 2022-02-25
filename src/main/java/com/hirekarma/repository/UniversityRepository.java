package com.hirekarma.repository;

import java.util.List;
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

	@Query("select u.universityId from University u where u.universityEmail = :email")
	Long findIdByEmail(@Param("email") String email);

	@Query("select s from University s where s.universityEmail = :email")
	List<University> getDetailsByEmail1(@Param("email")String email);

	@Query("select u from University u where u.status = ?1 ")
	List<University> displayUniversityList(boolean status);
	
	@Query("select u.universityId from University u where  u.universityId in (:universityIds)")
	List<Object[]> findUniveristyByIds(@Param("universityIds")List<Long> universityIds);
	

}
