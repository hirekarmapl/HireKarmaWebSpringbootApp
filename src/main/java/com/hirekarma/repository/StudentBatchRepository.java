package com.hirekarma.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hirekarma.model.StudentBatch;

@Repository("studentBatchRepository")
public interface StudentBatchRepository extends JpaRepository<StudentBatch, Long> {

	@Query("select id , batchName from StudentBatch")
	List<Object[]> getBatchList();

}
