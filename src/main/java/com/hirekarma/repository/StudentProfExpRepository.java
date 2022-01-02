package com.hirekarma.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hirekarma.model.StudentProfessionalExperience;

@Repository("studentProfExpRepository")
public interface StudentProfExpRepository extends JpaRepository<StudentProfessionalExperience, Long> {

	@Transactional
	@Modifying
	@Query("delete from StudentProfessionalExperience u where u.userId=?1")
	void deleteStudentProfExpByUserId(Long userId);

}
