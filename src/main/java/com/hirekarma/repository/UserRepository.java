package com.hirekarma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hirekarma.model.UserProfile;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<UserProfile, Long>{
	
	@Query("select u from UserProfile u where u.email = :email")
	UserProfile findUserByEmail(@Param("email")String email);
}
