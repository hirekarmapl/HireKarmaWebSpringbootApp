package com.hirekarma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hirekarma.model.StudentUser;

@Repository("studentUserRepository")
public interface StudentUserRepository extends JpaRepository<StudentUser, Long>{
	
	@Query("select u from StudentUser u where u.email = :email and u.password = :password")
	StudentUser checkLoginCredentials(@Param("email")String email,@Param("password")String password);
}
