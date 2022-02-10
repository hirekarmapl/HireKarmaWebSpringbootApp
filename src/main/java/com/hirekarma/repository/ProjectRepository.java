package com.hirekarma.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hirekarma.model.Project;
import com.hirekarma.model.UserProfile;
@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
 List<Project> findByUserProfile(UserProfile userProfile);
}
