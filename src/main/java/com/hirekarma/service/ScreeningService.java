package com.hirekarma.service;

import java.util.Map;

import com.hirekarma.beans.ScreeningBean;

public interface ScreeningService {
	
	public Map<String,Object> createScreeningQuestion(ScreeningBean screeningBean);

}
