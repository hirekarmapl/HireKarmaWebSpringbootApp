package com.hirekarma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hirekarma.model.Corporate;

@Repository("corporateRepository")
public interface CorporateRepository extends JpaRepository<Corporate, Long> {

}
