package com.hirekarma.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hirekarma.model.Internship;

@Repository("internshipRepository")
public interface InternshipRepository extends JpaRepository<Internship, Long>{
	@Query(value = "select u from Internship u where u.deleteStatus = :deleteStatus and u.corporateId = :corporateId")
	List<Internship> findInternshipsUserId(@Param("corporateId")Long corporateId, @Param("deleteStatus") Boolean deleteStatus);
}
