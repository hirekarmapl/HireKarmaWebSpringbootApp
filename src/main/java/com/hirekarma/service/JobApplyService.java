package com.hirekarma.service;

import org.json.simple.JSONObject;

import com.hirekarma.beans.HiringBean;
import com.hirekarma.beans.JobApplyBean;

public interface JobApplyService {
	public JobApplyBean insert(JobApplyBean jobApplyBean, String token);

	JSONObject hiringMeet(HiringBean hiringBean,String corporateEmail) throws Exception;

	void hireStudent(Long jobApplyId, String corporateEmail) throws Exception;
}
