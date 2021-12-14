package com.hirekarma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hirekarma.model.HireKarmaUser;

@Repository("hireKarmaUserRepository")
public interface HireKarmaUserRepository extends JpaRepository<HireKarmaUser, Long>{

}
