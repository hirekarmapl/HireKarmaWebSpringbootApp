package com.hirekarma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hirekarma.model.Student;

@Repository("studentRepository")
public interface StudentRepository extends JpaRepository<Student, Long>{
	
	@Query("select u from Student u where u.email = :email and u.password = :password")
	Student checkLoginCredentials(@Param("email")String email,@Param("password")String password);
}
