package com.hirekarma.service;

import java.util.List;

import org.json.simple.parser.ParseException;

import com.hirekarma.beans.JobBean;
import com.hirekarma.beans.JobResponseBean;
import com.hirekarma.model.Job;

public interface JobService {
	public JobBean insert(JobBean jobBean, String token);
	public List<JobBean> findJobsByUserId(String token) throws ParseException;
	public JobBean findJobById(Long jobId, String token) throws ParseException;
	public List<JobBean> deleteJobById(Long jobId, String token) throws ParseException;
	public JobBean updateJobById(JobBean jobBean, String token) throws ParseException;
	
	//updated verison of saveJob
	public JobResponseBean saveJob(JobBean jobBean,String token) throws Exception;
	public List<JobResponseBean> getAllJobsForAdmin() throws Exception;
	public List<JobResponseBean> getAllJobsForStudent() throws Exception;
	public List<JobResponseBean> getAllJobsAccordingToToken(String token) throws Exception;
	
//	updateversion
	public Job updateJobById2(JobBean jobBean, String token) throws Exception;
}
