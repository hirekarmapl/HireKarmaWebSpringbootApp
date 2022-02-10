package com.hirekarma.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hirekarma.model.Blog;
import com.hirekarma.model.Corporate;
@Repository
public interface BlogRepository extends JpaRepository<Blog, Integer> {

	Blog findBySlug(String slug);
	
	List<Blog> findBySlugAndCorporate(String slug,Corporate corporate);
	
	List<Blog> findByCorporate(Corporate corporate);
	
	List<Blog> findByIspublicTrue();
}
