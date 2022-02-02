package com.hirekarma.service;

import com.hirekarma.beans.JobApplyBean;

public interface JobApplyService {
	public JobApplyBean insert(JobApplyBean jobApplyBean, String token);
}
