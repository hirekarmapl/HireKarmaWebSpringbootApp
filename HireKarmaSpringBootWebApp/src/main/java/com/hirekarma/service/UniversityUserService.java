package com.hirekarma.service;

import com.hirekarma.beans.	UniversityUserBean;
import com.hirekarma.model.UniversityUser;

public interface UniversityUserService {
	
	public UniversityUser insert(UniversityUser UniversityUser);
	public UniversityUserBean checkLoginCredentials(String email,String password);

}