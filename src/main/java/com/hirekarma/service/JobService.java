package com.hirekarma.service;

import java.util.List;

import org.json.simple.parser.ParseException;

import com.hirekarma.beans.JobBean;

public interface JobService {
	public JobBean insert(JobBean jobBean, String token);
	public List<JobBean> findJobsByUserId(String token) throws ParseException;
	public JobBean findJobById(Long jobId);
	public List<JobBean> deleteJobById(Long jobId, String token);
	public JobBean updateJobById(JobBean jobBean);
}
