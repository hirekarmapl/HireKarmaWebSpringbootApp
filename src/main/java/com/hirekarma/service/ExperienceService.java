package com.hirekarma.service;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hirekarma.model.Experience;
@Repository
public interface ExperienceService {
public Experience addExperience(Experience experience,String token) throws Exception;
	
//	only authorized person will get his id
	public Experience getExperienceById(int id,String token) throws Exception;
	
	public List<Experience> getAllByUser(String token) throws Exception;
//	only authorized perosn will be available to delete his id
	public Boolean deleteById(int id,String token) throws Exception ;

	public Experience updateById(int id,String token,Experience experience) throws Exception;
	
}
