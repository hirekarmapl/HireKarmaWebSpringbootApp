package com.hirekarma.service;

import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.hirekarma.beans.MentorsWeekAvailability;
import com.hirekarma.model.Corporate;
@Service
public interface MentorService {

	public Map<String,Object> addWeekAvailablility(MentorsWeekAvailability mentorsWeekAvailability,Corporate corporate);
	
	
}
