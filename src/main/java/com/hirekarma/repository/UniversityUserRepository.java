package com.hirekarma.repository;

import org.springframework.data.repository.query.Param;

import com.hirekarma.model.UniversityUser;

//@Repository("universityUserRepository")
public interface UniversityUserRepository /* extends JpaRepository<UniversityUser, Long>*/ {
	
//	@Query("select u from UniversityUser u where u.emailAddress = :email and u.password = :password")
	UniversityUser checkLoginCredentials(@Param("email")String email,@Param("password")String password);

}