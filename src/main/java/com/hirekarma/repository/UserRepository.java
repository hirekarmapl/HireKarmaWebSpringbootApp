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
//	select u.* from user_profile u inner join  tbl_student student on student.user_id = u.user_id where student.university_id = 21 and status='Active';
	@Query("select u from UserProfile u inner join  Student student on student.userId = u.userId where student.universityId = :universityId and u.status='Active'")
	List<UserProfile> getAllStudents(@Param("universityId")Long universityId);

	@Query("select u from UserProfile u where u.email = :email and u.userType = :usertype")
	UserProfile findByEmail(@Param("email") String tokenKey, @Param("usertype") String usertype);

	@Query("select count(*) from UserProfile where email = :email and userType = :userType ")
	Long getDetailsByEmail(@Param("email")String lowerCaseEmail ,@Param("userType")String userType);
	@Query("select u,c from UserProfile u,Corporate c where u.email =:email and c.corporateEmail =:email")
	List<Object[]> findUserProfileAndCorporateByEmail(String email);
	
	@Query("select up,c,u,s from UserProfile up "
			+ "left join Corporate c on c.userProfile = up.userId "
			+ "left join University u on u.userId =  up.userId "
			+ "left join Student s on s.userId = up.userId "
			+ "where up.email=:email")
	List<Object[]> findUserAndAssociatedEntity(@Param("email")String email);

}
