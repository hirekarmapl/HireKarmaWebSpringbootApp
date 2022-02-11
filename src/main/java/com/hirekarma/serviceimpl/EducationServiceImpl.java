package com.hirekarma.serviceimpl;

import java.util.List;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirekarma.model.Education;
import com.hirekarma.model.UserProfile;
import com.hirekarma.repository.EducationRepository;
import com.hirekarma.repository.UserRepository;
import com.hirekarma.service.EducationService;
import com.hirekarma.utilty.Validation;
@Service("EducationService")
public class EducationServiceImpl implements EducationService {
	
	@Autowired
	UserRepository userRepository ;

	@Autowired
	EducationRepository educationRepository;
	@Override
	public Education addEducation(Education education, String token) throws Exception {
		// TODO Auto-generated method stub
		String email = Validation.validateToken(token);
		UserProfile userProfile = userRepository.findByEmail(email, "student");
		if(userProfile==null) {
			throw new Exception("user not found");
			
		}
		education.setUserProfile(userProfile);
		return this.educationRepository.save(education);
	
	}

	@Override
	public Education getEducationById(int id, String token) throws Exception {
		String email = Validation.validateToken(token);
		UserProfile userProfile = userRepository.findByEmail(email, "student");
		if(userProfile==null) {
			throw new Exception("user not found");
			
		}
		Education education = this.educationRepository.getById(id);
		if(education.getUserProfile().getUserId()!=userProfile.getUserId()) {
			throw new Exception("unauthorized");
		}
		
		return  education;
	}

	@Override
	public List<Education> getAllByUser(String token) throws Exception {
		String email = Validation.validateToken(token);
		UserProfile userProfile = userRepository.findByEmail(email, "student");
		if(userProfile==null) {
			throw new Exception("user not found");
		}
		
		return userProfile.getEducations();
	}

	@Override
	public boolean deleteById(int id, String token) throws Exception {
		// TODO Auto-generated method stub
		String email = Validation.validateToken(token);
		UserProfile userProfile = userRepository.findByEmail(email, "student");
		if(userProfile==null) {
			throw new Exception("user not found");
		}
		Education education = this.educationRepository.getById(id);
		if(education.getUserProfile().getUserId()!=userProfile.getUserId()) {
			throw new Exception("unauthorized");
		}
		this.educationRepository.deleteById(id);
		return true;
	}

	@Override
	public Education updateById(int id, String token, Education education) throws Exception {
		// TODO Auto-generated method stub
		String email = Validation.validateToken(token);
		UserProfile userProfile = userRepository.findByEmail(email, "student");
		if(userProfile==null) {
			throw new Exception("user not found");
		}
		Education educationDB = this.educationRepository.getById(id);
		if(educationDB.getUserProfile().getUserId()!=userProfile.getUserId() ) {
			throw new Exception("unauthorized");
		}
		education.update(education, userProfile);
		return this.educationRepository.save(education);
		
	}

}
