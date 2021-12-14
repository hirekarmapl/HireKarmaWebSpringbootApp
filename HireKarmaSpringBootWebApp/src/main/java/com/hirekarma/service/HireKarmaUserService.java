package com.hirekarma.service;

import com.hirekarma.beans.HireKarmaUserBean;
import com.hirekarma.model.HireKarmaUser;

public interface HireKarmaUserService {
	public HireKarmaUser insert(HireKarmaUser hireKarmaUser);
	public HireKarmaUserBean checkLoginCredentials(String email,String password);
}
