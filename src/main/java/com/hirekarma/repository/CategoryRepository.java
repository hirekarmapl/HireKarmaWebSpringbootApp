package com.hirekarma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hirekarma.model.Category;
@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

	public Category findByName(String name);
}
