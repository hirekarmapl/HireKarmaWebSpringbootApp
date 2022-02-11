package com.hirekarma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hirekarma.model.Education;
@Repository
public interface EducationRepository extends JpaRepository<Education, Integer> {

}
