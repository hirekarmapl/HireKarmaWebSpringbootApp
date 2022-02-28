package com.hirekarma.repository;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hirekarma.model.Job;
import com.hirekarma.model.Student;
import com.hirekarma.model.UniversityJobShareToStudent;

@Repository("universityJobShareRepository")
public interface UniversityJobShareRepository extends JpaRepository<UniversityJobShareToStudent, Long> {

	@Query("select u from UniversityJobShareToStudent u where u.universityId = :universityId")
	List<UniversityJobShareToStudent> getSharedJobList(@Param("universityId")Long id);

//	select j.* from job j where j.job_id in(select distinct(u.job_id) 
//			from university_job_share_to_student u where u.university_id=12);

	@Query("select j from Job j where j.jobId in (:jobIds)")
	List<Job> findAllDistinctJobByUniversityId(@Param("jobIds")List<Integer> jobIds);
	
	@Query(nativeQuery = true, value="select j.* from job j where j.job_id in "
			+ "(select distinct(u.job_id) from university_job_share_to_student u where u.university_id=:universityId)")
	List<Job> findDistinctJobFromUniversityid(@Param("universityId") Long universityId);
	
//	select * from tbl_student s inner join university_job_share_to_student u 
//	on s.student_id =u.student_id  where job_id =11 limit 1;
	@Query("select s from Student s inner join UniversityJobShareToStudent u"
			+" on s.studentId = u.studentId where u.jobId = :jobId ")
	List<Student> findStudentsByJobId(@Param("jobId")Long jobId);
	
	List<UniversityJobShareToStudent> findByUniversityId(Long universityId);
	
}
	