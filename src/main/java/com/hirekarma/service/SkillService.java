package com.hirekarma.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hirekarma.model.Skill;
@Service
public interface SkillService {

	public Skill addSkill(Skill skill);
	void deleteSkill(int skillId);
	void updateSkill(int skillId,Skill skill);
	Skill getSkill(int id);
	
}
