package com.hirekarma.repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hirekarma.model.Experience;
import com.hirekarma.model.UserProfile;
@Repository
public interface ExperienceRepository extends JpaRepository<Experience, Integer>
{
	Long deleteByUserProfile(UserProfile userProfile);
	
	
}
