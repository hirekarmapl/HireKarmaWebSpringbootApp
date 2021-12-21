package com.hirekarma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hirekarma.model.InternshipApply;

@Repository("internshipApplyRepository")
public interface InternshipApplyRepository extends JpaRepository<InternshipApply, Long>{

}
