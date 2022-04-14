package com.hirekarma.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hirekarma.model.Blog;
import com.hirekarma.model.Notice;
@Repository
public interface NoticeRepository extends JpaRepository<Notice, String> {
	Notice findBySlug(String slug);
	Long deleteBySlug(String slug);
	@Query("select n from Notice n inner join Student s on s.universityId = n.universityId where s.studentEmail = :studentEmail order by n.updatedOn desc")
	List<Notice> getAllNoticeForStudent(String studentEmail);
}
