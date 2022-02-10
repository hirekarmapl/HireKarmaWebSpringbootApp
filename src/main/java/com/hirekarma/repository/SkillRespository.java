package com.hirekarma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hirekarma.model.Skill;
@Repository
public interface SkillRespository extends JpaRepository<Skill, Integer> {

	Skill findByName(String name);
	
}
