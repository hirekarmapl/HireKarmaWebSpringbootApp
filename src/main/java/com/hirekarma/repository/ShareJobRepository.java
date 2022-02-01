package com.hirekarma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hirekarma.model.AdminShareJobToUniversity;

@Repository("shareJobRepository")
public interface ShareJobRepository extends JpaRepository<AdminShareJobToUniversity, Long> {

}
