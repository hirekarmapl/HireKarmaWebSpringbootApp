package com.hirekarma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hirekarma.model.ShareJob;

@Repository
public interface ShareJobRepository extends JpaRepository<ShareJob, Long> {

}
