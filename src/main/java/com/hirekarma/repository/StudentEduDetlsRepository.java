package com.hirekarma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hirekarma.model.StudentEducationDetails;

@Repository("studentEduDetlsRepository")
public interface StudentEduDetlsRepository extends JpaRepository<StudentEducationDetails, Long>{

}
