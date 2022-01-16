package com.hirekarma.service;

import com.hirekarma.beans.JobBean;
import com.hirekarma.beans.ShareJobBean;

public interface AdminService {
	
	JobBean updateActiveStatus(Long id, String status);

	ShareJobBean shareJob(ShareJobBean shareJobBean);
}
