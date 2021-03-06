package com.hirekarma.service;

import java.util.List;
import java.util.Map;

import com.hirekarma.beans.CampusDriveResponseBean;
import com.hirekarma.beans.JobApplyResponseBean;
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

	public UserBean updateCoporateUserProfile(UserBean userBean,String token)throws Exception;

	public UserBean findCorporateById(Long userId);

	public List<StudentDetails> corporateCampusResponse(CampusDriveResponseBean campus);

	public List<Corporate> corporateList();

	public List<StudentDetails> applyStudentDetails(CampusDriveResponseBean campus, String token);
	
	public Map<String,Object> shortListStudent(Long corporateId, Long jobApplyId);
	
	public Map<String,Object> getAllJobApplicationsByCorporate(Long corporateId);
	
	public Map<String,Object> getAllCampusDriveInvitesByCorporateId(Long corporateId);
	
	public List<JobApplyResponseBean> getAllJobsApplicationForCorporate(Long corporateId);

	Map<String, Object> shortListStudentForInternship(Long corporateId, Long jobApplyId);
	
	public Map<String,Object> getAllApplicantsForJobByJobId(Long jobId);
	
	public Map<String, Object> shortListStudentForJobByJobApplyIdsdAndJobId(List<Long> jobApplyIds,Long jobId) throws Exception;

	Corporate updateCorporateFromUserProfileNotNull(UserProfile userProfile, Corporate corporate, UserBean bean);

	double getCorporateProfileUpdateStatus(Corporate corporate, UserProfile userProfile);
	
	void updateMentorStatus(Corporate corporate,boolean status) throws Exception;
}
