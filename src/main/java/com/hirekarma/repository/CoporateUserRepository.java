package com.hirekarma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hirekarma.model.CoporateUser;

@Repository("coporateUserRepository")
public interface CoporateUserRepository extends JpaRepository<CoporateUser, Long>{
	@Query("select u from CoporateUser u where u.email = :email and u.password = :password")
	CoporateUser checkLoginCredentials(@Param("email")String email,@Param("password")String password);
}
