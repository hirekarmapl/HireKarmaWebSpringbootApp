package com.hirekarma.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hirekarma.model.InternshipApply;
import com.hirekarma.model.JobApply;

@Repository("internshipApplyRepository")
public interface InternshipApplyRepository extends JpaRepository<InternshipApply, Long>{
	@Query("select u from InternshipApply u where u.studentId = ?1")
	List<InternshipApply> getAllInternshipByStudentId(Long studentId);
	
	@Query("select ua from InternshipApply ua inner join Internship u on ua.internshipId = u.internshipId where ua.corporateId = :corporateId and (u.deleteStatus is false or u.deleteStatus is null)")
	List<InternshipApply> findByCorporateId(@Param("corporateId")Long corporateId);
	
	InternshipApply findByStudentIdAndInternshipId(Long studentId,Long internshipId);
}
