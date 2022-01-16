package com.hirekarma.service;

import com.hirekarma.beans.ShareJobBean;
import com.hirekarma.model.Job;

public interface AdminService {
	Job updateActiveStatus(Long id, String status);
	ShareJobBean shareJob(ShareJobBean shareJobBean);
}
