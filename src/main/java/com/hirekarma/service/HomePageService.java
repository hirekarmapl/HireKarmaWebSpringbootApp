package com.hirekarma.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.hirekarma.beans.HomePageBean;

@Service
public interface HomePageService {
	Map<String,Object> add(HomePageBean homePageBean) throws Exception;
	Map<String,Object> noOfJobPosted();
	Map<String,Object> noOfJobApplications();
	Map<String,Object> noOfStudents();
	Map<String, Object> noOfStudentsHired();
	Map<String, Object> noOfOngoingDrive();
}
