package com.hirekarma.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hirekarma.model.StudentEducationDetails;

@Repository("studentEduDetlsRepository")
public interface StudentEduDetlsRepository extends JpaRepository<StudentEducationDetails, Long>{
	
	@Transactional
	@Modifying
	@Query("delete from StudentEducationDetails u where u.userId=?1")
	void deleteStudentEduDtlsByUserId(Long userId);
}
