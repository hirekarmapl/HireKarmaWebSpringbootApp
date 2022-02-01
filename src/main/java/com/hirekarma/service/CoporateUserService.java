package com.hirekarma.service;

import java.util.List;

import com.hirekarma.beans.CampusDriveResponseBean;
import com.hirekarma.beans.StudentDetails;
import com.hirekarma.beans.UserBean;
import com.hirekarma.model.Corporate;
import com.hirekarma.model.UserProfile;

public interface CoporateUserService {
//	public CoporateUser insert(CoporateUser coporateUser);
//	public CoporateUserBean checkLoginCredentials(String email,String password);
//	public CoporateUserBean updateCoporateUserProfile(CoporateUserBean bean);
//	public CoporateUserBean findCorporateById(Long corpUserId);

	public UserProfile insert(UserProfile userProfile);

	public UserBean updateCoporateUserProfile(UserBean userBean);

	public UserBean findCorporateById(Long userId);

	public List<StudentDetails> corporateCampusResponse(CampusDriveResponseBean campus);

	public List<Corporate> corporateList();

	public List<StudentDetails> applyStudentDetails(CampusDriveResponseBean campus, String token);
}
