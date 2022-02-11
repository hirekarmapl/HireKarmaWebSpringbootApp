package com.hirekarma.serviceimpl;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.apache.catalina.User;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;
import com.hirekarma.beans.JobBean;
import com.hirekarma.controller.CoporateUserController;
import com.hirekarma.model.Corporate;
import com.hirekarma.model.Job;
import com.hirekarma.model.Skill;
import com.hirekarma.model.Student;
import com.hirekarma.model.UserProfile;
import com.hirekarma.repository.SkillRespository;
import com.hirekarma.repository.StudentRepository;
import com.hirekarma.repository.UserRepository;
import com.hirekarma.service.SkillService;
import com.hirekarma.utilty.Validation;

@Service("SkillService")
public class SkillServiceImpl implements SkillService {
	private static final Logger LOGGER = LoggerFactory.getLogger(SkillServiceImpl.class);

	@Autowired
	SkillRespository skillRespository;

	@Override
	public Skill addSkill(Skill skill) {
		try {
			Skill savedSkill = skillRespository.save(skill);
			return savedSkill;
		} catch (Exception e) {
			throw e;
		}

	}

	@Override
	public void deleteSkill(int skillId){
		try {
			this.skillRespository.deleteById(skillId);
		}
		catch(Exception e){
			throw e;
		}

	}

	@Override
	public void updateSkill(int skillId, Skill skill) {
		try {
			skill.setId(skillId);
			addSkill(skill);
		}
		catch (Exception e) {
			throw e;
		}

	}

	@Override
	public Skill getSkill(int skillId) {
	Skill skill = 	this.skillRespository.getById(skillId);
	
	return skill;
	}

//	public List<Skills> addSkills(List<Skill> skills){
//		
//	}
}
