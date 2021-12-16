package com.hirekarma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import com.hirekarma.model.UniversityUser;

@Repository("UniversityUserRepository")
public interface UniversityUserRepository extends JpaRepository<UniversityUser, Long> {
	
	@Query("select u from UniversityUser u where u.emailAddress = :email and u.password = :password")
	UniversityUser checkLoginCredentials(@Param("email")String email,@Param("password")String password);

}