package com.hirekarma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hirekarma.model.HireKarmaUser;

@Repository("hireKarmaUserRepository")
public interface HireKarmaUserRepository extends JpaRepository<HireKarmaUser, Long>{
	
	@Query("select u from HireKarmaUser u where u.email = :email and u.password = :password")
	HireKarmaUser checkLoginCredentials(@Param("email")String email,@Param("password")String password);
}
