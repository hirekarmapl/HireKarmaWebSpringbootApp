package com.hirekarma.service;

import java.util.List;
import java.util.Map;

import org.json.simple.parser.ParseException;

import com.hirekarma.beans.AdminShareJobToUniversityBean;
import com.hirekarma.beans.CampusDriveResponseBean;
import com.hirekarma.beans.UniversityJobShareToStudentBean;
import com.hirekarma.model.AdminShareJobToUniversity;
import com.hirekarma.model.University;

public interface UniversityService {

	Map<String, Object> universityResponse(AdminShareJobToUniversityBean jobBean);

	Map<String, Object> shareJobStudent(UniversityJobShareToStudentBean universityJobShareToStudentBean)
			throws Exception;

	CampusDriveResponseBean campusDriveRequest(CampusDriveResponseBean campus, String token) throws Exception;

	List<?> seeShareJobList(String token) throws Exception;

	List<?> studentDetails(String token) throws Exception;

	List<?> seeShareJobListToStudent(String token) throws Exception;

	List<?> studentFilter(String token, Long batchId, Long branchId, Double cgpa) throws ParseException;

	Map<String,Object> getAllJobsSharedByUniversity(University university);
}
