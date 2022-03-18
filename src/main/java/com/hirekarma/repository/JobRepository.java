package com.hirekarma.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hirekarma.model.Job;

@Repository("jobRepository")
public interface JobRepository extends JpaRepository<Job, Long> {

	@Query(value = "select u from Job u where (u.deleteStatus = :deleteStatus or u.deleteStatus is null) and u.corporateId = :corporateId")
	List<Job> findJobsByUserId(@Param("corporateId") Long corporateId, @Param("deleteStatus") Boolean deleteStatus);

	@Query(value = "select u from Job u where u.deleteStatus= true ")
	List<Job> getJobAllDetails();

	// Job Details shown to Student Sended By University

	@Query("select j.jobId , j.jobTitle , j.category , j.jobType , j.wfhCheckbox , "
			+ "j.skills , j.city , j.openings , j.salary , j.about , j.description , u.id , u.studentResponseStatus,j.corporateId "
			+ "from Job j inner join UniversityJobShareToStudent u on j.jobId = u.jobId "
			+ "where u.universityId = :universityId and u.studentId = :studentId")
	List<Object[]> getStudentJobAllDetails(@Param("universityId") Long universityId,
			@Param("studentId") Long studentId);

	@Query(value = "select count(*) from Job where jobId = :jobId ")
	Long getJobById(@Param("jobId") Long jobId);

	@Query("select j from Job j where j.jobId = :jobId")
	Job findByJobId(@Param("jobId") Long jobId);

	@Query("select j from Job j where j.jobId = :jobId and j.corporateId = :corporateId")
	Optional<Job> getJobDetails(@Param("jobId") Long jobId, @Param("corporateId") Long corporateId);
	
	@Query("select j from Job j where j.status = TRUE and j.deleteStatus = FALSE and (j.forcampusDrive=FALSE or j.forcampusDrive=NULL)")
	List<Job> getAllJobsForStudents();
	

//	List<Job> getAllJobsForStudentSharedByUniversity();
	
	@Query("select j from Job j where (j.deleteStatus = FALSE or j.deleteStatus is null)")
	List<Job> getAllJobsForAdmin();
	
	@Query("select j from Job j where (j.deleteStatus = FALSE or j.deleteStatus is null) and (j.status=FALSE or j.status is null)")
	List<Job> getAllJobsForAdminForActivation();
	
	@Query("select j from Job j where (j.deleteStatus = FALSE or j.deleteStatus is null) and (j.status=TRUE ) and (j.forcampusDrive = false or j.forcampusDrive is null)")
	List<Job> getAllActivatedJobsForAdmin();
	
	@Modifying
	@Transactional
	@Query("update Job j set j.status = :status where j.jobId in (:jobIds)")
	void updateMutipleJobStatus(@Param("jobIds") List<Long> jobIds,@Param("status") Boolean status);

}
