package com.hirekarma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hirekarma.model.Blog;
import com.hirekarma.model.Notice;
@Repository
public interface NoticeRepository extends JpaRepository<Notice, Integer> {
	Notice findBySlug(String slug);
	Long deleteBySlug(String slug);
}
