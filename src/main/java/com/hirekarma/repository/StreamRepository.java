package com.hirekarma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hirekarma.model.Stream;

@Repository
public interface StreamRepository extends JpaRepository<Stream, Integer> {

	
}
