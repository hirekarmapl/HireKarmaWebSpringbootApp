package com.hirekarma.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hirekarma.model.Student;

@Repository("studentRepository")
public interface StudentRepository extends JpaRepository<Student, Long>,JpaSpecificationExecutor<Student>{

	@Query("select s from Student s where s.userId = :userId")
	Optional<Student> getStudentDetails(@Param("userId") Long userId);

	@Query("select count(*) from Student where studentEmail = :email")
	Long getDetailsByEmail(@Param("email")String lowerCaseEmail);

	@Query("select s.studentId from Student s "
			+ "inner join StudentBatch sbc on s.batch = sbc.id "
			+ "inner join StudentBranch sbr on s.branch = sbr.id "
			+ "where s.batch = :batchId and s.branch = :branchId and s.cgpa >= :cgpaId and s.universityId = :universityId")
	List<Long> getStudentList(@Param("batchId")Long batchId, @Param("branchId")Long branchId,@Param("cgpaId") Double cgpaId,@Param("universityId")Long universityId);

	List<Student> findByBatchAndBranchAndUniversityIdAndCgpaGreaterThanEqual(Long batch,Long branch, Long universityId,Double cgpa);
	
	List<Student> findByBatch(Long Batch);
	
	
	@Query("select studentId from Student")
	List<Long> getStudentList();

	@Query("select s from Student s where s.studentEmail = :email")
	List<Student> getDetailsByEmail1(@Param("email")String email);

	@Query("select s from Student s where s.universityId = :universityId")
	List<Student> getStudentListForUniversity(@Param("universityId")Long universityId);

	@Query("select s from Student s where s.studentId = :studentId")
	Student findByStudentId(@Param("studentId")Long studentId);

	@Query("select s.studentName , s.studentEmail , s.studentAddress , s.studentPhoneNumber , "
			+ "sbt.batchName , sbr.branchName , s.cgpa "
			+ "from Student s inner join UniversityJobShareToStudent u on s.studentId = u.studentId "
			+ "inner join StudentBatch sbt on s.batch = sbt.id "
			+ "inner join StudentBranch sbr on s.branch = sbr.id "
			+ "where u.universityId = :universityId  and u.jobId = :jobId and u.studentResponseStatus = 1")
	List<Object[]> findApplyStudentDetails(@Param("universityId") Long universityId,@Param("jobId")  Long jobId);
	
	@Query("select s,u,up "
			+ "from Student s inner join UniversityJobShareToStudent u on s.studentId = u.studentId "
			+ "inner join UserProfile up on s.userId = up.userId "
			+ "where u.universityId = :universityId  and u.jobId = :jobId ")
	List<Object[]> getAllStudentsWhomUniversitySharedJobByUniversityAndJob(@Param("universityId") Long universityId,@Param("jobId")  Long jobId);
	
	@Query("select s,u,sbt,sbr,up,m "
			+ "from Student s inner join UniversityJobShareToStudent u on s.studentId = u.studentId "
			+ "inner join UserProfile up on up.userId = s.userId "
			+ "left join Meet m on m.universityJobShareToStudent = u and m.endTime > (:now) "
			+ "left join StudentBatch sbt on s.batch = sbt.id "
			+ "left join StudentBranch sbr on s.branch = sbr.id "
			+ "where u.universityId = :universityId  and u.jobId = :jobId and u.studentResponseStatus = 1")
	List<Object[]> getAllStudentsReadyForCampusDriveByUniversiyAndJobForCorporate(@Param("universityId") Long universityId,@Param("jobId")  Long jobId,@Param("now") LocalDateTime now);
	
	
	@Query("select s "
			+ "from Student s inner join UniversityJobShareToStudent u on s.studentId = u.studentId "
			+ "left join StudentBatch sbt on s.batch = sbt.id "
			+ "left join StudentBranch sbr on s.branch = sbr.id "
			+ "where u.universityId = :universityId  and u.jobId = :jobId and u.studentResponseStatus = 1")
	List<Student> getAllStudentsReadyForCampusDriveByUniversiyAndJob(@Param("universityId") Long universityId,@Param("jobId")  Long jobId);
	
	
	@Query("select s from Student s where s.universityId = :universityId and s.batch = :batchId and s.branch = :branchId and s.cgpa >= :cgpa")
	List<Student> getStudentFilter(@Param("universityId")Long universityId,@Param("batchId") Long batchId,@Param("branchId") Long branchId,@Param("cgpa") Double cgpa);
	
	Student findByStudentEmail(String studentEmail);
	
	@Query("select sbh.id,sbh.batchName,sbr.id,sbr.branchName,u.universityId,u.universityName from StudentBatch sbh,StudentBranch sbr,University u where sbh.id = :batchId and sbr.id = :branchId and u.universityId = :universityId")
	List<Object[]> getBranchNBatchNUniversityIdNUniveristyNameFromIds(@Param("batchId")Long batchId,@Param("branchId")Long branchId,@Param("universityId") Long universityId);
	
}
