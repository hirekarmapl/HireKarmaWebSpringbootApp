package com.hirekarma.service;

import java.util.List;
import java.util.Map;

import com.hirekarma.beans.ScreeningBean;

public interface ScreeningService {
	
	public Map<String,Object> createScreeningQuestion(ScreeningBean screeningBean);
	public Map<String,Object> createListScreeningQuestion(List<ScreeningBean> screeningBeans,Long corporateId,Long universityId);
	public Map<String,Object> updateScreeningQuestion(String slug,ScreeningBean screeningBean,Long corporateId, Long universityId);
	public Map<String,Object> deleteScreeningQuestion(String slug,Long corporateId,Long universityId);
	public Map<String,Object> sendScreeningQuestions(Long jobApplyId,String screeningSlug,Long chatRoomId);
	public Map<String,Object> responseToScreeningQuestions(Long  response);
	public Map<String,Object> getScreeningQuestionsByScreeningTableId(String slug);
	public Map<String,Object> getAllScreeningQuestions();
	void sendScreeningQuestionToStudents(List<Long> jobApplyIds, String screeningSlug) throws Exception;
	void sendScreeningQuestionsToMultipleStudent(List<Long> jobApplyIds, List<String> screeningSlugs) throws Exception;
}
