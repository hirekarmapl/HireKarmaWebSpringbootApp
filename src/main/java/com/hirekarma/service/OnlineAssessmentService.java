package com.hirekarma.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hirekarma.beans.OnlineAssessmentBean;
import com.hirekarma.model.OnlineAssessment;

@Service
public interface OnlineAssessmentService {

	OnlineAssessment addOnlineAssessmentByCorporate(OnlineAssessmentBean bean,String token) throws Exception;
	
	OnlineAssessment addQuestionToOnlineAssesmentByCorporate(int onlineAssessmentId,List<Integer> questionariesId,String token) throws Exception;
	
	OnlineAssessment updateQuestionOfOnlineAssessmentByCorporate(int onlineAssessmentId,List<Integer> questionariesId,String token) throws Exception;
	
	List<OnlineAssessment> getOnlineAssesmentsAddedByCorporated(String token) throws Exception;
	
	OnlineAssessment updateOnlineAssessment(OnlineAssessmentBean onlineAssessmentBean,String token) throws Exception;
	
}
