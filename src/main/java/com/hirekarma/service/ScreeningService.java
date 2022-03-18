package com.hirekarma.service;

import java.util.List;
import java.util.Map;

import com.hirekarma.beans.ScreeningBean;

public interface ScreeningService {
	
	public Map<String,Object> createScreeningQuestion(ScreeningBean screeningBean);
	public Map<String,Object> createListScreeningQuestion(List<ScreeningBean> screeningBeans,Long corporateId);
	public Map<String,Object> updateScreeningQuestion(String slug,ScreeningBean screeningBean);
	public Map<String,Object> deleteScreeningQuestion(String slug);
	public Map<String,Object> sendScreeningQuestions(Long jobApplyId,String screeningSlug);
	public Map<String,Object> responseToScreeningQuestions(Long  response);
	public Map<String,Object> getScreeningQuestionsByScreeningTableId(String slug);
	public Map<String,Object> getAllScreeningQuestions();
	void sendScreeningQuestionToStudents(List<Long> jobApplyIds, String screeningSlug) throws Exception;
}
