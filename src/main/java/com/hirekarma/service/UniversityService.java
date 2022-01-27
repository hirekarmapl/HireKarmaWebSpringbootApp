package com.hirekarma.service;

import java.util.List;
import java.util.Map;

import com.hirekarma.beans.AdminShareJobToUniversityBean;
import com.hirekarma.beans.CampusDriveResponseBean;
import com.hirekarma.beans.UniversityJobShareToStudentBean;
import com.hirekarma.model.AdminShareJobToUniversity;

public interface UniversityService {

	Map<String, Object> universityResponse(AdminShareJobToUniversityBean jobBean);

	Map<String, Object> shareJobStudent(UniversityJobShareToStudentBean universityJobShareToStudentBean)
			throws Exception;

	CampusDriveResponseBean campusDriveRequest(CampusDriveResponseBean campus, String token) throws Exception;

	List<?> seeShareJobList(String token) throws Exception;

	List<?> studentDetails(String token) throws Exception;

}
