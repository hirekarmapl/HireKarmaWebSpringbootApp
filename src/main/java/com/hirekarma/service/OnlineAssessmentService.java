package com.hirekarma.service;

import java.util.List;
import java.util.Map;
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
import com.hirekarma.model.University;

@Service
public interface OnlineAssessmentService {
//	------------------ COMMON ------------------------
	public List<StudentOnlineAssessment> sendOnlineAssessmentToStudents(OnlineAssessmentBean onlineAssessmentBean) throws Exception ;

	
	
	public void deleteOnlineAssessmentBySlugAndToken(String slug,String token) throws ParseException, Exception;



	OnlineAssessment updateQuestionOfOnlineAssessmentByCorporate(String onlineAssessmentId,List<Integer> questionariesId,String token) throws Exception;

	




	void deleteQuestionofOnlineAssessment(OnlineAssessmentBean onlineAssesmentBean	,String slug) throws Exception;

	
	public List<OnlineAssesmentResponseBean> getAllOnlineAssessmentForStudent(String token) throws ParseException;


	
//	get qna for student with particular assessment
	public Map<String,Object> getAllQNAForStudentForOnlineAssessment(String token,String studentOnlineAssessmentSlug) throws Exception;

	public void submitAnswerForOnlineAssessmentByStudent(String onlineAssessmentSlug,List<StudentOnlineAssessmentAnswerRequestBean> studentOnlineAssessmentAnswerRequestBeans,String token) throws Exception;

//-----------------public
	
//	response for public assessment
	public Map<String,Object> submitResponseForPublicAssessment(String onlineAssesmentSlug,List<StudentOnlineAssessmentAnswerRequestBean> studentOnlineAssessmentAnswerRequestBeans) throws Exception;

	
//---------------------- corporate ------------------
	
//	get all assessment for corporate without qna
	List<OnlineAssessmentBean> getOnlineAssesmentsAddedByCorporatedWithoutQNA(Corporate corporate) throws Exception;
	
//	get all assessment for corporate
	List<OnlineAssessment> getOnlineAssesmentsAddedByCorporated(String token) throws Exception;
	
//	create online assessment by corporate
	OnlineAssessment addOnlineAssessmentByCorporate(OnlineAssessmentBean bean,Corporate corporate) throws Exception;
	
//	add questions to particular online Assessement by corporate
	OnlineAssessment addQuestionToOnlineAssesmentByCorporate(OnlineAssessmentBean onlineAssessmentBean,Corporate corporate) throws Exception;

	
//	update online assessment by corporate
	OnlineAssessment updateOnlineAssessmentByCorporate(OnlineAssessmentBean onlineAssessmentBean,Corporate corporate,String slug) throws Exception;
	
	
//	get question and answer by slug
	OnlineAssessment getCorporateOnlineAssessmentBySlug(Corporate corporate,String slug) throws Exception;
	
//	delete a online assessment
	public void deleteOnlineAssessmentBySlugAndCorporate(String slug,Corporate corporate) throws Exception;
	
	
	
	
	
//	-------------------- ADMIN -----------------------------
	
//	get all assessment for admin without qna
	List<OnlineAssessmentBean> getOnlineAssesmentsAddedByAdminWithoutQNA() throws Exception;
	
//	create online assessment by admin
	OnlineAssessment addOnlineAssessmentByAdmin(OnlineAssessmentBean bean) throws Exception;
	

//	add questions to particular online Assessement by admin
	OnlineAssessment addQuestionToOnlineAssesmentByAdmin(OnlineAssessmentBean onlineAssessmentBean) throws Exception;

//	get all assesment created by admin
	List<OnlineAssessment> getOnlineAssessmentCreatedByAdmin();	
	
//	get all assessment created by admin for public
	public List<OnlineAssesmentResponseBean> getAllOnlineAssessmentForPublic() throws Exception;
	
//	get qna for public with particular assessment
	public Map<String,Object> getAllQNAForPublicForAssessement(String onlineAssessmentSlug) throws Exception;

//	update assesment
	OnlineAssessment updateOnlineAssessmentByAdmin(OnlineAssessmentBean onlineAssessmentBean, String slug)
			throws Exception;
	
	
//	get a assessment by slug
	OnlineAssessment getAdminOnlineAssessmentBySlug(String slug) throws Exception;
	
//	delete a online assessment
	void deleteOnlineAssessmentBySlugAndAdmin(String slug) throws Exception;
	
	
//	------------------ university -----------------------
	
//	get all assessment for corporate without qna
	List<OnlineAssessmentBean> getOnlineAssesmentsAddedByUniversityWithoutQNA(University university) throws Exception;
	
//	create online assessment by university	
	OnlineAssessment addOnlineAssessmentByUniversity(OnlineAssessmentBean bean,University university) throws Exception;

//	add questions to particular online Assessement by university
	OnlineAssessment addQuestionToOnlineAssesmentByUniversity(OnlineAssessmentBean onlineAssessmentBean,University university) throws Exception;
	

//	update online assessment by university
	OnlineAssessment updateOnlineAssessmentByUniversity(OnlineAssessmentBean onlineAssessmentBean,University university,String slug) throws Exception;

//	get assessment by slug
	OnlineAssessment getUniversityOnlineAssessmentBySlug(University university, String slug) throws Exception;




	void deleteOnlineAssessmentBySlugAndUniversity(String slug, University university) throws Exception;


	
	
}
