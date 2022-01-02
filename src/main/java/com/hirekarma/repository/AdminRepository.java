package com.hirekarma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hirekarma.model.Job;

@Repository("adminRepository")
public interface AdminRepository extends JpaRepository<Job, Long> {

}
