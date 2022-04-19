package com.hirekarma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hirekarma.model.ScreeningEntityParent;
@Repository	
public interface ScreeningEntityParentRepository extends JpaRepository<ScreeningEntityParent, String>{

}
