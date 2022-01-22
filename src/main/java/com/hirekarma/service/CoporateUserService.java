package com.hirekarma.service;

import com.hirekarma.beans.CampusDriveResponseBean;
import com.hirekarma.beans.UserBean;
import com.hirekarma.model.UserProfile;

public interface CoporateUserService {
//	public CoporateUser insert(CoporateUser coporateUser);
//	public CoporateUserBean checkLoginCredentials(String email,String password);
//	public CoporateUserBean updateCoporateUserProfile(CoporateUserBean bean);
//	public CoporateUserBean findCorporateById(Long corpUserId);

	public UserProfile insert(UserProfile userProfile);

	public UserBean updateCoporateUserProfile(UserBean userBean);

	public UserBean findCorporateById(Long userId);

	public CampusDriveResponseBean corporateCampusResponse(CampusDriveResponseBean campus);
}
