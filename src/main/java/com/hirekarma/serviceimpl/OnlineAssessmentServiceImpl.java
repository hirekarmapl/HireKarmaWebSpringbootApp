package com.hirekarma.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirekarma.beans.OnlineAssessmentBean;
import com.hirekarma.model.Corporate;
import com.hirekarma.model.OnlineAssessment;
import com.hirekarma.model.QuestionANdanswer;
import com.hirekarma.repository.CorporateRepository;
import com.hirekarma.repository.OnlineAssessmentRepository;
import com.hirekarma.repository.QuestionAndAnswerRepository;
import com.hirekarma.service.OnlineAssessmentService;
import com.hirekarma.utilty.Utility;
import com.hirekarma.utilty.Validation;

@Service("OnlineAssessmentService")
public class OnlineAssessmentServiceImpl implements OnlineAssessmentService {
	@Autowired
	QuestionAndAnswerRepository questionAndAnswerRepository;

	@Autowired
	CorporateRepository corporateRepository;
	
	@Autowired
	OnlineAssessmentRepository onlineAssessmentRepository;
	
	@Override
	public OnlineAssessment addOnlineAssessmentByCorporate(OnlineAssessmentBean bean, String token) throws Exception {
		
		String email = Validation.validateToken(token);
		Corporate corporate = corporateRepository.findByEmail(email);
		
		OnlineAssessment onlineAssessment = new OnlineAssessment();
	
		onlineAssessment.setTitle(bean.getTitle());
		onlineAssessment.setCorporate(corporate);
		onlineAssessment.setSlug(Utility.createSlug(""));
		return onlineAssessmentRepository.save(onlineAssessment);
		
		
	}
	
	public List<QuestionANdanswer> getQuestionAndAnswerById(List<Integer> questionariesId) throws Exception{
		
		List<QuestionANdanswer> questionANdanswers =  new ArrayList<>();
		for(Integer q : questionariesId) {
			QuestionANdanswer questionANdanswer = questionAndAnswerRepository.getById((long) q);
			if(questionANdanswer==null) {
				throw new Exception("please check your list");
			}
			questionANdanswers.add(questionANdanswer);
			
		}
		return questionANdanswers;
	}
	@Override
	public OnlineAssessment addQuestionToOnlineAssesmentByCorporate(int onlineAssessmentId,
			List<Integer> questionariesId, String token) throws Exception {
		String email = Validation.validateToken(token);
		
		Corporate corporate = corporateRepository.findByEmail(email);
		OnlineAssessment onlineAssessment = onlineAssessmentRepository.getById(onlineAssessmentId);
		
		if(onlineAssessment==null) {
			throw new Exception("onlineAssesment id incorrect");
		}
		
		List<QuestionANdanswer> questionANdanswers =  getQuestionAndAnswerById(questionariesId);
		onlineAssessment.getQuestionANdanswers().addAll(questionANdanswers);
		
		return this.onlineAssessmentRepository.save(onlineAssessment);
		
	}

	public OnlineAssessment updateOnlineAssessmentForBeanNotNull(OnlineAssessment onlineAssessment,OnlineAssessmentBean onlineAssessmentBean) {
		System.out.println(onlineAssessmentBean.toString());
		if(onlineAssessmentBean.getTitle()!=null) {
			onlineAssessment.setTitle(onlineAssessmentBean.getTitle());
		}
		if(onlineAssessmentBean.getTotalMarks()>=0) {
			
			onlineAssessment.setTotalMarks(onlineAssessmentBean.getTotalMarks());
		}
		if(onlineAssessmentBean.getCodingMarks()>=0) {
			onlineAssessment.setCodingMarks(onlineAssessmentBean.getCodingMarks());
		}
		if(onlineAssessmentBean.getMcqMarks()>=0) {
			onlineAssessment.setMcqMarks(onlineAssessmentBean.getMcqMarks());
		}
		if(onlineAssessmentBean.getParagraphMarks()>=0) {
			onlineAssessment.setParagraphMarks(onlineAssessmentBean.getParagraphMarks());
		}
		if(onlineAssessmentBean.getQnaMarks()>=0) {
			onlineAssessment.setQnaMarks(onlineAssessmentBean.getQnaMarks());
		}
		return onlineAssessment;
	}
	
	public OnlineAssessment updateOnlineAssessment(OnlineAssessmentBean onlineAssessmentBean,String token,String slug) throws Exception{
		OnlineAssessment onlineAssessment = this.onlineAssessmentRepository.findBySlug(slug);
		if(onlineAssessment==null) {
			throw new Exception("please enter proper assesemnet id");
		}
		return this.onlineAssessmentRepository.save(updateOnlineAssessmentForBeanNotNull(onlineAssessment,onlineAssessmentBean));
	}
	
	@Override
	public OnlineAssessment updateQuestionOfOnlineAssessmentByCorporate(int onlineAssessmentId,
			List<Integer> questionariesId, String token) throws Exception {
		OnlineAssessment onlineAssessment = onlineAssessmentRepository.getById(onlineAssessmentId);
		
		if(onlineAssessment==null) {
			throw new Exception("onlineAssesment id incorrect");
		}
		
		List<QuestionANdanswer> questionANdanswers =  getQuestionAndAnswerById(questionariesId);
	
		onlineAssessment.setQuestionANdanswers(questionANdanswers);
		
		return this.onlineAssessmentRepository.save(onlineAssessment);
	}

	@Override
	public List<OnlineAssessment> getOnlineAssesmentsAddedByCorporated(String token) throws Exception {
		String email = Validation.validateToken(token);
		Corporate corporate = corporateRepository.findByEmail(email);
		return corporate.getOnlineAssessments();
	}

	@Override
	public OnlineAssessment getOnlineAssessmentBySlug(String token, String slug) throws Exception {
		String email =  Validation.validateToken(token);
		OnlineAssessment onlineAssessment = onlineAssessmentRepository.findBySlug(slug);
		if(onlineAssessment==null) {
			throw new Exception("Bad Request");
		}
		return onlineAssessment;
	}
	
	


	
	

}
