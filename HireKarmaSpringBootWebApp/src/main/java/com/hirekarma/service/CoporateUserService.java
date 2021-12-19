package com.hirekarma.service;

import com.hirekarma.beans.CoporateUserBean;
import com.hirekarma.model.CoporateUser;

public interface CoporateUserService {
	public CoporateUser insert(CoporateUser coporateUser);
	public CoporateUserBean checkLoginCredentials(String email,String password);
	public CoporateUserBean updateCoporateUserProfile(CoporateUserBean bean);
	public CoporateUserBean findCorporateById(Long corpUserId);
}
