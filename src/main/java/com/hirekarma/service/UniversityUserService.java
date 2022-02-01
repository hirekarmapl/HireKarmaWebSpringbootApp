package com.hirekarma.service;

import com.hirekarma.beans.UserBean;
import com.hirekarma.model.UserProfile;

public interface UniversityUserService {
	
//	public UniversityUser insert(UniversityUser UniversityUser);
//	public UniversityUserBean checkLoginCredentials(String email,String password);
//	public UniversityUserBean updateUniversityUserProfile(UniversityUserBean universityUserBean);
//	public UniversityUserBean findUniversityById(Long universityId);
	
	public UserProfile insert(UserProfile universityUser);
	public UserBean updateUniversityUserProfile(UserBean universityUserBean, String token);
	public UserBean findUniversityById(Long universityId);

}