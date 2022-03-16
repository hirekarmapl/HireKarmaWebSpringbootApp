package com.hirekarma.service;

import java.util.List;
import java.util.Set;

import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import com.hirekarma.beans.OnlineAssesmentResponseBean;
import com.hirekarma.beans.OnlineAssessmentBean;
import com.hirekarma.beans.QuestionAndAnswerStudentResponseBean;
import com.hirekarma.beans.StudentOnlineAssessmentAnswerRequestBean;
import com.hirekarma.model.Corporate;
import com.hirekarma.model.OnlineAssessment;
import com.hirekarma.model.StudentOnlineAssessment;

@Service
public interface OnlineAssessmentService {
	public void deleteOnlineAssessmentBySlugAndCorporate(String slug,Corporate corporate);
	
	public void deleteOnlineAssessmentBySlugAndToken(String slug,String token) throws ParseException;

	OnlineAssessment addOnlineAssessmentByCorporate(OnlineAssessmentBean bean,String token) throws Exception;
	
	OnlineAssessment addQuestionToOnlineAssesmentByCorporate(OnlineAssessmentBean onlineAssessmentBean,String token) throws Exception;
	
	OnlineAssessment updateQuestionOfOnlineAssessmentByCorporate(String onlineAssessmentId,List<Integer> questionariesId,String token) throws Exception;
	
	List<OnlineAssessment> getOnlineAssesmentsAddedByCorporated(String token) throws Exception;
	
	List<OnlineAssessmentBean> getOnlineAssesmentsAddedByCorporatedWithoutQNA(String token) throws Exception;
	
	OnlineAssessment updateOnlineAssessment(OnlineAssessmentBean onlineAssessmentBean,String token,String slug) throws Exception;
	
	OnlineAssessment getOnlineAssessmentBySlug(String token,String slug) throws Exception;
	
	void deleteQuestionofOnlineAssessment(OnlineAssessmentBean onlineAssesmentBean	,String slug) throws Exception;

	public List<StudentOnlineAssessment> sendOnlineAssessmentToStudents(OnlineAssessmentBean onlineAssessmentBean,String token) throws Exception ;

	public List<OnlineAssesmentResponseBean> getAllOnlineAssessmentForStudent(String token) throws ParseException;

	public List<QuestionAndAnswerStudentResponseBean> getAllQNAForStudentOfOnlineAssessment(String token,String onlineAssessmentSlug) throws Exception;

	public void submitAnswerForOnlineAssessmentByStudent(String onlineAssessmentSlug,List<StudentOnlineAssessmentAnswerRequestBean> studentOnlineAssessmentAnswerRequestBeans,String token) throws Exception;
}
