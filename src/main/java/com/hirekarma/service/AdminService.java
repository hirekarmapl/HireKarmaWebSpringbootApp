package com.hirekarma.service;

import com.hirekarma.beans.JobBean;
import com.hirekarma.beans.ShareJobBean;
import com.hirekarma.model.Job;
import com.hirekarma.model.UserProfile;

public interface AdminService {
	JobBean updateActiveStatus(Long id, String status);

	ShareJobBean shareJob(ShareJobBean shareJobBean);
}
