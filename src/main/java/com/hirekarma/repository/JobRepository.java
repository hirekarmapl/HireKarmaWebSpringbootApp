package com.hirekarma.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hirekarma.beans.JobBean;
import com.hirekarma.model.Job;

@Repository("jobRepository")
public interface JobRepository extends JpaRepository<Job, Long>{
	
	@Query(value = "select u from Job u where u.deleteStatus = :deleteStatus and u.corporateId = :corporateId")
	List<Job> findJobsByUserId(@Param("corporateId")Long corporateId,@Param("deleteStatus")Boolean deleteStatus);

	@Query(value = "select u from Job u where u.deleteStatus='Active' ")
	List<Job> getJobAllDetails();
	
	//Job Details shown to Student Sended By University
	
	@Query("select j.jobId , j.jobTitle , j.category , j.jobType , j.wfhCheckbox , "
			+ "j.skills , j.city , j.openings , j.salary , j.about , j.description , u.id , u.studentResponseStatus "
			+ "from Job j inner join UniversityJobShareToStudent u on j.jobId = u.jobId "
			+ "where u.universityId = :universityId and u.studentId = :studentId")
	List<Object[]> getStudentJobAllDetails(@Param("universityId")Long universityId,@Param("studentId") Long studentId);

	@Query(value = "select count(*) from Job where jobId = :jobId ")
	Long getJobById(@Param("jobId") Long jobId);

	@Query("select j from Job j where j.jobId = :jobId")
	Job findByJobId(@Param("jobId")Long jobId);

}
