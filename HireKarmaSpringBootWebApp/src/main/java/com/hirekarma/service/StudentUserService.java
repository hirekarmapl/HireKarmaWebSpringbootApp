package com.hirekarma.service;

import com.hirekarma.beans.StudentUserBean;
import com.hirekarma.model.StudentUser;

public interface StudentUserService {
	public StudentUser insert(StudentUser hireKarmaUser);
	public StudentUserBean checkLoginCredentials(String email,String password);
}
