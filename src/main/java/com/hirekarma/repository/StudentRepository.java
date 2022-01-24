package com.hirekarma.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hirekarma.model.Student;

@Repository("studentRepository")
public interface StudentRepository extends JpaRepository<Student, Long>{

	@Query("select s from Student s where s.userId = :userId")
	Optional<Student> getStudentDetails(@Param("userId") Long userId);

	@Query("select count(*) from Student where studentEmail = :email")
	Long getDetailsByEmail(@Param("email")String lowerCaseEmail);

	@Query("select s.studentId from Student s "
			+ "inner join StudentBatch sbc on s.batch = sbc.id "
			+ "inner join StudentBranch sbr on s.branch = sbr.id "
			+ "inner join StudentCGPA scg on s.cgpa = scg.id")
	List<Long> getStudentList(@Param("batchId")Long batchId, @Param("branchId")Long branchId,@Param("cgpaId") Long cgpaId);

	@Query("select studentId from Student")
	List<Long> getStudentList();
	
	
}
