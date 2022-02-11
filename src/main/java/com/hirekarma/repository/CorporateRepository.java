package com.hirekarma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hirekarma.model.Corporate;

@Repository("corporateRepository")
public interface CorporateRepository extends JpaRepository<Corporate, Long> {

	@Query("select c from Corporate c where c.corporateEmail = :email")
	Corporate findByEmail(@Param("email")String email);

}
