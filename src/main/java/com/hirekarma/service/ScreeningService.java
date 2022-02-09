package com.hirekarma.service;

import java.util.Map;

import com.hirekarma.beans.ScreeningBean;

public interface ScreeningService {
	
	public Map<String,Object> createScreeningQuestion(ScreeningBean screeningBean);
	public Map<String,Object> updateScreeningQuestion(String slug,ScreeningBean screeningBean);
	public Map<String,Object> deleteScreeningQuestion(String slug);
	public Map<String,Object> sendScreeningQuestions(Long jobApplyId,String screeningSlug);
	public Map<String,Object> responseToScreeningQuestions(String response);
}
