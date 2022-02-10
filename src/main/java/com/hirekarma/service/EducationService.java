package com.hirekarma.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hirekarma.model.Education;
import com.hirekarma.model.Project;
@Service
public interface EducationService {
//	can be added by anyone, the user will be set according to token
	public Education addEducation(Education education,String token) throws Exception;
	
//	only authorized person will get his id
	public Education getEducationById(int id,String token) throws Exception;
	
	public List<Education> getAllByUser(String token) throws Exception;
//	only authorized perosn will be available to delete his id
	public boolean deleteById(int id,String token) throws Exception ;

	public Education updateById(int id,String token,Education education) throws Exception;
	
}
