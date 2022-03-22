package com.hirekarma.service;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import com.hirekarma.beans.UserBean;
import com.hirekarma.beans.UserBeanResponse;
import com.hirekarma.model.University;
import com.hirekarma.model.UserProfile;

public interface UniversityUserService {
	
//	public UniversityUser insert(UniversityUser UniversityUser);
//	public UniversityUserBean checkLoginCredentials(String email,String password);
//	public UniversityUserBean updateUniversityUserProfile(UniversityUserBean universityUserBean);
//	public UniversityUserBean findUniversityById(Long universityId);
	
	public UserProfile insert(UserProfile universityUser);
	public UserBeanResponse updateUniversityUserProfile(UserBean universityUserBean, String token);
	public UserBean findUniversityById(Long universityId);
	ResponseEntity<Resource> getDummyExcelForStudentImport();
	double getProfileUpdateStatusForUniversity(University university, UserProfile universityUserProfile);
}