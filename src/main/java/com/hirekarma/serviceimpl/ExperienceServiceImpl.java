package com.hirekarma.serviceimpl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirekarma.model.Education;
import com.hirekarma.model.Experience;
import com.hirekarma.model.UserProfile;
import com.hirekarma.repository.ExperienceRepository;
import com.hirekarma.repository.UserRepository;
import com.hirekarma.service.ExperienceService;
import com.hirekarma.utilty.Validation;

import ch.qos.logback.classic.Logger;
@Service("ExperienceService")
public class ExperienceServiceImpl implements ExperienceService {

	@Autowired
	UserRepository userRepository ;
	
	@Autowired
	ExperienceRepository experienceRepository;
	@Override
	public Experience addExperience(Experience experience, String token) throws Exception {
		String email = Validation.validateToken(token);
		UserProfile userProfile = userRepository.findByEmail(email, "student");
		if(userProfile==null) {
			throw new Exception("user not found");
			
		}
		
		experience.setUserProfile(userProfile);
		return this.experienceRepository.save(experience);
	
	}

	@Override
	public Experience getExperienceById(int id, String token) throws Exception {
		String email = Validation.validateToken(token);
		UserProfile userProfile = userRepository.findByEmail(email, "student");
		if(userProfile==null) {
			throw new Exception("user not found");
			
		}
		
		Experience experience = this.experienceRepository.getById(id);
		if(experience.getUserProfile().getUserId()!=userProfile.getUserId()) {
			throw new Exception("unauthorized");
		}
		
		return  experience;
	}

	@Override
	public List<Experience> getAllByUser(String token) throws Exception {
		// TODO Auto-generated method stub
		String email = Validation.validateToken(token);
		UserProfile userProfile = userRepository.findByEmail(email, "student");
		if(userProfile==null) {
			throw new Exception("user not found");
		}
		
		return userProfile.getExperiences();
	}

	@Override
	public Boolean deleteById(int id, String token) throws Exception {
		String email = Validation.validateToken(token);
		UserProfile userProfile = userRepository.findByEmail(email, "student");
		if(userProfile==null) {
			throw new Exception("user not found");
		}
		Experience experience = this.experienceRepository.getById(id);
		if(experience.getUserProfile().getUserId()!=userProfile.getUserId()) {
			throw new Exception("unauthorized");
		}
		this.experienceRepository.deleteById(id);
		return true;
	}

	@Override
	public Experience updateById(int id, String token, Experience experience) throws Exception {
		String email = Validation.validateToken(token);
		UserProfile userProfile = userRepository.findByEmail(email, "student");
		if(userProfile==null) {
			throw new Exception("user not found");
		}
		Experience experienceDB = this.experienceRepository.getById(id);
		if(experienceDB==null) {
			System.out.println("null");
		}
		if(experienceDB.getUserProfile().getUserId()!=userProfile.getUserId()) {
			throw new Exception("unauthorized");
		}

		experienceDB.update(experience, userProfile);
		
		return this.experienceRepository.save(experienceDB);
		
	}

}
