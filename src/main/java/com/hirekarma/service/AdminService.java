package com.hirekarma.service;

import java.util.List;
import java.util.Map;

import com.hirekarma.beans.JobBean;
import com.hirekarma.beans.AdminShareJobToUniversityBean;

public interface AdminService {
	
	Map<String, Object> updateActiveStatus(Long id, boolean status);

	Map<String, Object> shareJob(AdminShareJobToUniversityBean adminShareJobToUniversityBean);

	List<?> displayJobList();
}
