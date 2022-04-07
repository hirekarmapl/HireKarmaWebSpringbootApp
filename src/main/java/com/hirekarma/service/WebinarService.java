package com.hirekarma.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.hirekarma.beans.WebinarRequest;
import com.hirekarma.model.Corporate;
import com.hirekarma.model.Webinar;

@Service
public interface WebinarService {
	Map<String,Object> addForCorporate(WebinarRequest webinar,Corporate corporate) throws Exception;
	Map<String,Object> findAllForCorporate(Corporate corporate) throws Exception;
	Map<String,Object> update(String webinarId,WebinarRequest webinar,Corporate corporate) throws Exception;
	Map<String,Object> updateIsAcceptedStatus(boolean status,String webinarId) throws Exception;
	Map<String,Object> findAllForAdmin() throws Exception;
	Map<String,Object> addForAdmin(WebinarRequest webinar) throws Exception;
	Map<String,Object> findAllForStudent() throws Exception;	
	Map<String,Object> updateIsDisableStatus(boolean status,String webinarId,Corporate corporate) throws Exception;
	
}
