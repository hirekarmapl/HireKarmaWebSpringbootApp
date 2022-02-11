package com.hirekarma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hirekarma.model.Organization;

@Repository("organizationRepository")
public interface OrganizationRepository extends JpaRepository<Organization, Long> {
	
	@Query(value = "select u from Organization u where u.status = :status and u.corporateId = :corporateId and u.organizationId = :organizationId")
	Organization findOrganizationByUserId(@Param("corporateId")Long corporateId,@Param("status") Boolean status,@Param("organizationId") Long organizationId);

	@Query(value = "select u from Organization u where u.status = :status and u.corporateId = :corporateId")
	Organization findOrganizationByCorporateId(@Param("corporateId")Long corporateId,@Param("status") Boolean status);
	
}
