package com.hirekarma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hirekarma.model.UniversityJobShare;

@Repository("universityJobShareRepository")
public interface UniversityJobShareRepository extends JpaRepository<UniversityJobShare, Long> {

}
	