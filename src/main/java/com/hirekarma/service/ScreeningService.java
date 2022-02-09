package com.hirekarma.service;

import java.util.Map;

import com.hirekarma.beans.ScreeningBean;

public interface ScreeningService {
	
	public Map<String,Object> createScreeningQuestion(ScreeningBean screeningBean);
	public Map<String,Object> updateScreeningQuestion(String slug,ScreeningBean screeningBean);

}
