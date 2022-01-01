package com.hirekarma.service;

import java.util.List;

import com.hirekarma.beans.JobBean;

public interface JobService {
	public JobBean insert(JobBean jobBean);
	public List<JobBean> findJobsByUserId(Long userId);
	public JobBean findJobById(Long jobId);
	public List<JobBean> deleteJobById(Long jobId);
	public JobBean updateJobById(JobBean jobBean);
}
