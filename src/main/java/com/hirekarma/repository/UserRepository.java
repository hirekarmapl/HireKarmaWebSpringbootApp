package com.hirekarma.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hirekarma.model.UserProfile;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<UserProfile, Long> {

	@Query("select u from UserProfile u where u.email = :email")
	UserProfile findUserByEmail(@Param("email") String email);

	@Query("select u from UserProfile u where u.userType = 'student' and u.status = 'Active'")
	List<UserProfile> getAllStudents();

	@Query("select u from UserProfile u where u.email = :email and u.userType = :usertype")
	UserProfile findByEmail(@Param("email") String tokenKey, @Param("usertype") String usertype);

	@Query("select count(*) from UserProfile where email = :email and userType = :userType ")
	Long getDetailsByEmail(@Param("email")String lowerCaseEmail ,@Param("userType")String userType);

}
