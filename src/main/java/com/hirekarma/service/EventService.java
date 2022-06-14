package com.hirekarma.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.hirekarma.beans.EventBean;
import com.hirekarma.model.Corporate;
import com.hirekarma.model.University;
import com.hirekarma.model.UserProfile;

@Service
public interface EventService {

	Map<String,Object> updateEvent(EventBean eventBean,String slug,Corporate corporate,University university) throws Exception;
	Map<String,Object> getAllEventsForPublic() throws Exception;
	Map<String,Object> getAllEvents(Corporate corporate, University university) throws Exception;
	Map<String,Object> createEvent(EventBean eventBean) throws Exception;
	Map<String,Object> addUsersForEvent(String slug,List<Long> userIds,Corporate corporate,University university) throws Exception;
	Map<String,Object> deleteById(String slug,Corporate corporate,University university) throws Exception;
}
