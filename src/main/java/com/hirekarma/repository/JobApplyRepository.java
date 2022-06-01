package com.hirekarma.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hirekarma.model.JobApply;

@Repository("jobApplyRepository")
public interface JobApplyRepository extends JpaRepository<JobApply, Long>{
	
	@Query("select u from JobApply u where u.studentId = ?1")
	List<JobApply> getAllJobApplicationsByStudentId(Long studentId);
	
	@Query("select u from JobApply u where u.corporateId = :corporateId")
	List<JobApply> getAllJobApplicationsByCorporate(@Param("corporateId")Long corporateId);
	
	JobApply findByStudentIdAndJobId(Long studentId,Long jobId);
	
	@Query("select ja,s,up ,c.chatRoomId "
			+ "from JobApply ja "
			+ "inner join Student s on s.studentId = ja.studentId "
			+ "inner join UserProfile up on up.userId=s.userId "
			+ "left join ChatRoom c on c.jobApplyId=ja.jobApplyId "
			+ "where ja.jobId = :jobId")
	List<Object[]> findJobApplyAndStudentAndUserProfileAndChatRoomByJobId(@Param("jobId")Long jobId);
	
	@Modifying
	@Transactional
	@Query("update JobApply ja set ja.applicationStatus = true where ja.studentId in (:studentIds) and ja.jobId=:jobId")
	void updateApplicationStatusByJobIdAndStudentIds(@Param("jobId")Long jobId,@Param("studentIds")List<Long> studentIds);
	
	@Query("select count(jobApplyId) from JobApply where isHire = :status")
	Long countByIsHire(@Param("status") boolean status);
}
