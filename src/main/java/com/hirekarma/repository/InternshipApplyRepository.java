package com.hirekarma.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hirekarma.model.InternshipApply;
import com.hirekarma.model.JobApply;

@Repository("internshipApplyRepository")
public interface InternshipApplyRepository extends JpaRepository<InternshipApply, Long>{
	@Query("select u from InternshipApply u where u.studentId = ?1")
	List<InternshipApply> getAllInternshipByStudentId(Long studentId);
}
