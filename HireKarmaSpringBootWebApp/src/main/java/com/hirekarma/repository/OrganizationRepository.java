package com.hirekarma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hirekarma.model.Organization;

@Repository("organizationRepository")
public interface OrganizationRepository extends JpaRepository<Organization, Long>{
	
}
