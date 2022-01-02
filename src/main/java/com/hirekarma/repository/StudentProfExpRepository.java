package com.hirekarma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hirekarma.model.StudentProfessionalExperience;

@Repository("studentProfExpRepository")
public interface StudentProfExpRepository extends JpaRepository<StudentProfessionalExperience, Long> {

}
