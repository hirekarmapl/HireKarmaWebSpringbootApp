package com.hirekarma.service;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;

import com.hirekarma.beans.AdminShareJobToUniversityBean;
import com.hirekarma.beans.BadgeShareBean;
import com.hirekarma.model.AdminShareJobToUniversity;
import com.hirekarma.model.Corporate;

public interface AdminService {
	
	Map<String, Object> updateActiveStatus(Long id, boolean status);

	Map<String, Object> shareJob(AdminShareJobToUniversityBean adminShareJobToUniversityBean);

	List<?> displayJobList();

	List<?> displayUniversityList();

	Corporate shareBadge(BadgeShareBean badgeShareBean);
	
	AdminShareJobToUniversity requestCorporateToUpdateJD(long adminShareJobId) throws Exception;
	
	AdminShareJobToUniversity updateShareJob(JSONObject lookup,long id) throws Exception;
	
	//better version of above api
	public Map<String,Object> shareJob2(AdminShareJobToUniversityBean adminShareJobToUniversityBean) throws Exception;
}
