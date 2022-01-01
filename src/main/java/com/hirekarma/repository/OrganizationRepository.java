package com.hirekarma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hirekarma.model.Organization;

@Repository("organizationRepository")
public interface OrganizationRepository extends JpaRepository<Organization, Long> {
	
	@Query(value = "select u from Organization u where u.status='Active' and u.userId = :userId")
	Organization findOrganizationByUserId(@Param("userId")Long userId);
	
}
