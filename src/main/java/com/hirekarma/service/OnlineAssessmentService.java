package com.hirekarma.service;

import java.util.List;

import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import com.hirekarma.beans.OnlineAssessmentBean;
import com.hirekarma.model.OnlineAssessment;

@Service
public interface OnlineAssessmentService {

	OnlineAssessment addOnlineAssessmentByCorporate(OnlineAssessmentBean bean,String token) throws Exception;
	
	OnlineAssessment addQuestionToOnlineAssesmentByCorporate(String onlineAssessmentId,List<Integer> questionariesId,String token) throws Exception;
	
	OnlineAssessment updateQuestionOfOnlineAssessmentByCorporate(String onlineAssessmentId,List<Integer> questionariesId,String token) throws Exception;
	
	List<OnlineAssessment> getOnlineAssesmentsAddedByCorporated(String token) throws Exception;
	
	List<OnlineAssessmentBean> getOnlineAssesmentsAddedByCorporatedWithoutQNA(String token) throws Exception;
	
	OnlineAssessment updateOnlineAssessment(OnlineAssessmentBean onlineAssessmentBean,String token,String slug) throws Exception;
	
	OnlineAssessment getOnlineAssessmentBySlug(String token,String slug) throws Exception;
	
	void deleteQuestionofOnlineAssessment(OnlineAssessmentBean onlineAssesmentBean	,String slug) throws Exception;

	public OnlineAssessment sendOnlineAssessmentToStudents(OnlineAssessmentBean onlineAssessmentBean,String token) throws Exception ;

	public List<OnlineAssessment> getAllOnlineAssessmentForStudent(String token) throws ParseException;
}
