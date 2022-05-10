package com.hirekarma.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hirekarma.model.Blog;
import com.hirekarma.model.Notice;
@Repository
public interface NoticeRepository extends JpaRepository<Notice, String> {
	Notice findBySlug(String slug);
	Long deleteBySlug(String slug);
	@Query("select n from Notice n where n.universityId= :universityId and n.deadLine>= :now order by deadLine desc")
	List<Notice> findByUniversityId(@Param("universityId") Long universityId,LocalDateTime now);
	
	@Query("select n from Notice n where n.corporateId= :corporateId and n.deadLine>= :now order by deadLine desc")
	List<Notice> findByCorporateId(@Param("corporateId") Long corporateId,LocalDateTime now);
	@Query("select n from Notice n inner join Student s on s.universityId = n.universityId where s.studentEmail = :studentEmail order by n.updatedOn desc")
	List<Notice> getAllNoticeForStudent(String studentEmail);
}
