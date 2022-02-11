package com.hirekarma.service;

import java.util.List;
import java.util.Map;

import com.hirekarma.beans.AdminShareJobToUniversityBean;
import com.hirekarma.beans.BadgeShareBean;
import com.hirekarma.model.Corporate;

public interface AdminService {
	
	Map<String, Object> updateActiveStatus(Long id, boolean status);

	Map<String, Object> shareJob(AdminShareJobToUniversityBean adminShareJobToUniversityBean);

	List<?> displayJobList();

	List<?> displayUniversityList();

	Corporate shareBadge(BadgeShareBean badgeShareBean);
}
