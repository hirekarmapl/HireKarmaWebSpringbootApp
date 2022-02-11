package com.hirekarma.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hirekarma.model.StudentBranch;

@Repository("studentBranchRepository")
public interface StudentBranchRepository extends JpaRepository<StudentBranch, Long> {

	@Query("select id , branchName from StudentBranch")
	List<Object[]> getBranchList();

}
