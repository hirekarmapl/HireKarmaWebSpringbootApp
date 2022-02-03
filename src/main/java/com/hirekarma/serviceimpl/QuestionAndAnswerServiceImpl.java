package com.hirekarma.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirekarma.beans.QuestionAndAnswerBean;
import com.hirekarma.beans.QuestionAndAnswerResponseBean;
import com.hirekarma.model.CodingAnswer;
import com.hirekarma.model.InputAnswer;
import com.hirekarma.model.LongAnswer;
import com.hirekarma.model.MCQAnswer;
import com.hirekarma.model.QuestionANdanswer;
import com.hirekarma.repository.MCQRepository;
import com.hirekarma.repository.QuestionAndAnswerRepository;
import com.hirekarma.service.QuestionAndANswerService;
@Service("QuestionAndAnswerServiceImpl")
public class QuestionAndAnswerServiceImpl implements QuestionAndANswerService {
	@Autowired
	QuestionAndAnswerRepository QARepo;
	@Autowired
	MCQRepository mcqRepo;

	public QuestionAndAnswerResponseBean CreateQuestionAndAnswer(List<QuestionAndAnswerBean> qAndA) {
		QuestionAndAnswerResponseBean bean=new QuestionAndAnswerResponseBean();
		for(int i=0;i<qAndA.size();i++) {
			QuestionAndAnswerBean QAbean=new QuestionAndAnswerBean();
			QuestionANdanswer QA=new QuestionANdanswer();
			QAbean=qAndA.get(i);
			String QType=QAbean.getType();
			if(QType.equals("QNA")) {
				createQNARecord(QAbean,QARepo,bean);				
			}
			else if(QType.equals("MCQ")) {
				createMCQRecord(QAbean,QARepo,bean);
				
				
			}else if(QType.equals("Input")) {
				createInputRecord(QAbean,QARepo,bean);
				
			}else if(QType.equals("Coding")) {
				String [] question=QAbean.getQuestion();
				QA.setQuestion(question[0]);
				QA.setType(QAbean.getType());
				QA.setCodingDescription(QAbean.getCodingDescription());
				String[] testCase=QAbean.getTestCase();
				QA.setCorporateId(QAbean.getCorporateId());
				QA.setuID(UUID.randomUUID().toString());
				List<CodingAnswer> codAns=new ArrayList();
				for(int x=0;x<testCase.length;x++) {
					CodingAnswer ans=new CodingAnswer();
					ans.setQ_uid(QA.getuID());
					ans.setTestCases(testCase[x]);
					codAns.add(ans);
				}
				QA.setCodingAnswer(codAns);
				QARepo.save(QA);
				bean.setStatus(200);
				bean.setMessage("Data Saved Successfully!!");
			}else {
				bean.setStatus(200);
				bean.setMessage("Saving Data Failed!!");
			}
		}
		return bean;
	}


	private void createInputRecord(QuestionAndAnswerBean QAbean, QuestionAndAnswerRepository QARepo,
			QuestionAndAnswerResponseBean bean) {
		QuestionANdanswer QA=new QuestionANdanswer();
		String [] question=QAbean.getQuestion();
		QA.setQuestion(question[0]);
		QA.setType(QAbean.getType());
		QA.setCorporateId(QAbean.getCorporateId());
		QA.setuID(UUID.randomUUID().toString());
		QARepo.save(QA);
		bean.setStatus(200);
		bean.setMessage("Data Saved Successfully!!");
		
	}


	private void createMCQRecord(QuestionAndAnswerBean QAbean, QuestionAndAnswerRepository QARepo,
			QuestionAndAnswerResponseBean bean) {
		QuestionANdanswer QA=new QuestionANdanswer();
		String [] question=QAbean.getQuestion();
		for(int j=0;j<question.length;j++) {
		QA.setQuestion(question[j]);
		QA.setType(QAbean.getType());
		QA.setuID(UUID.randomUUID().toString());
		String[] mcqAnswer=QAbean.getMcqAnswer();
		List<MCQAnswer> ans=new ArrayList();
		for(int k=0;k<mcqAnswer.length;k++) {
			MCQAnswer answer=new MCQAnswer();
			answer.setMcqAnswer(mcqAnswer[k]);
			answer.setQ_uid(QA.getuID());
			ans.add(answer);					
		}
		QA.setMcqAnswer(ans);
		QA.setCorporateId(QAbean.getCorporateId());			
		QARepo.save(QA);
		}
		bean.setStatus(200);
		bean.setMessage("Data Saved Successfully!!");
		
	}


	private void createQNARecord(QuestionAndAnswerBean QAbean, QuestionAndAnswerRepository QARepo2, QuestionAndAnswerResponseBean bean) {
		String [] question=QAbean.getQuestion();
		for(int j=0;j<question.length;j++) {
			QuestionANdanswer QAs=new QuestionANdanswer();
		QAs.setQuestion(question[j]);
		QAs.setType(QAbean.getType());
		QAs.setCorporateId(QAbean.getCorporateId());
		QAs.setuID(UUID.randomUUID().toString());
		QARepo.save(QAs);
		}
		bean.setStatus(200);
		bean.setMessage("Data Saved Successfully!!");
		
	}


	public QuestionAndAnswerResponseBean updateQuestionAndAnswer(List<QuestionAndAnswerBean> qAndA) {
		QuestionAndAnswerResponseBean bean=new QuestionAndAnswerResponseBean();
		for(int i=0;i<qAndA.size();i++) {	
			QuestionAndAnswerBean QAbean=new QuestionAndAnswerBean();
			QAbean=qAndA.get(i);
			String QType=QAbean.getType();
			if(QType.equals("QNA")) {
				QuestionANdanswer QABeanDetail=new QuestionANdanswer();
				QABeanDetail=QARepo.findByuID(QAbean.getuId());
				LongAnswer lAns=new LongAnswer();
				if(QABeanDetail==null) {
					bean.setStatus(207);
					bean.setMessage("Data Can not be Updated because it is not found!!");
					return bean;
				}else {
				String [] question=QAbean.getQuestion();
				QABeanDetail.setQuestion((question[0]==null || question[0].equals(""))?QABeanDetail.getQuestion():question[0]);
				QABeanDetail.setType((QAbean.getType()==null ||QAbean.getType().equals(""))?QABeanDetail.getType():QAbean.getType());
				QABeanDetail.setCorporateId((QAbean.getCorporateId()==null ||QAbean.getCorporateId().equals(""))?QABeanDetail.getCorporateId():QAbean.getCorporateId());
				lAns.setId(QABeanDetail.getId());
				lAns.setLongAnswer(QAbean.getLongAnswer());
				lAns.setUid(QABeanDetail.getuID());
				QABeanDetail.setLongAnswer(lAns);
				QARepo.save(QABeanDetail);
				bean.setStatus(200);
				bean.setMessage("Data Updated Successfully!!");
				}
				
			}else if(QType.equals("MCQ")) {
				QuestionANdanswer QABeanDetail=new QuestionANdanswer();
				QABeanDetail=QARepo.findByuID(QAbean.getuId());
				if(QABeanDetail==null) {
					bean.setStatus(207);
					bean.setMessage("Data Can not be Updated because it is not found!!");
					return bean;
				}else {					
				String [] question=QAbean.getQuestion();
				QABeanDetail.setQuestion((question[0]==null || question[0].equals(""))?QABeanDetail.getQuestion():question[0]);
				QABeanDetail.setType((QAbean.getType()==null || QAbean.getType().equals(""))?QABeanDetail.getType():QAbean.getType());
				QABeanDetail.setCorporateId((QAbean.getCorporateId()==null || QAbean.getCorporateId().equals(""))?QABeanDetail.getCorporateId():QAbean.getCorporateId());
				List<MCQAnswer> mcqAns=mcqRepo.findAllByUid(QABeanDetail.getId().toString());
				List<MCQAnswer> mcq=QABeanDetail.getMcqAnswer();
				List<MCQAnswer> mcqAnswer=QAbean.getMcqAns();
				List<MCQAnswer> ans=new ArrayList();
				for(int k=0;k<mcq.size();k++) {
				MCQAnswer mcqA=mcq.get(k);
				MCQAnswer answer=new MCQAnswer();
				for(int l=0;l<mcqAnswer.size();l++) {
					MCQAnswer mcqAn=mcqAnswer.get(l);					
				if(mcqA.getId()==mcqAn.getId()) {					
					answer.setId(mcqAn.getId());
					answer.setMcqAnswer((mcqAn.getMcqAnswer()==null || mcqAn.getMcqAnswer().equals(""))?QABeanDetail.getMcqAnswer().get(k).getMcqAnswer():mcqAn.getMcqAnswer());					
					answer.setQ_uid(QABeanDetail.getuID());
					answer.setUid(QABeanDetail.getId().toString());
					ans.add(answer);	
					continue;
				}
				}
				answer.setId(mcqA.getId());
				answer.setMcqAnswer(mcqA.getMcqAnswer());					
				answer.setQ_uid(QABeanDetail.getuID());
				answer.setUid(QABeanDetail.getId().toString());
				ans.add(answer);
				}
				QABeanDetail.setMcqAnswer(ans);
				
				QARepo.save(QABeanDetail);
				bean.setStatus(200);
				bean.setMessage("Data Updated Successfully!!");
				
				
				}
			}else if(QType.equals("Input")) {
				QuestionANdanswer QABeanDetail=new QuestionANdanswer();
				QABeanDetail=QARepo.findByuID(QAbean.getuId());
				if(QABeanDetail==null) {
					bean.setStatus(207);
					bean.setMessage("Data Can not be Updated because it is not found!!");
					return bean;
				}else {
					InputAnswer answer=new InputAnswer();
				String [] question=QAbean.getQuestion();
				QABeanDetail.setQuestion((question[0]==null || question[0].equals(""))?QABeanDetail.getQuestion():question[0]);
				QABeanDetail.setType((QAbean.getType()==null || QAbean.getType().equals(""))?QABeanDetail.getType():QAbean.getType());
				QABeanDetail.setCorporateId((QAbean.getCorporateId()==null || QAbean.getCorporateId().equals(""))?QABeanDetail.getCorporateId():QAbean.getCorporateId());
				answer.setId(QABeanDetail.getId());
				answer.setInputAnswer(QAbean.getInputAnswer());
				answer.setUid(QABeanDetail.getuID());
				QABeanDetail.setInputAnswer(answer);
				QARepo.save(QABeanDetail);
				bean.setStatus(200);
				bean.setMessage("Data Updated Successfully!!");
				}
			}else if(QType.equals("Coding")) {
				QuestionANdanswer QABeanDetail=new QuestionANdanswer();
				QABeanDetail=QARepo.findByuID(QAbean.getuId());
				if(QABeanDetail==null) {
					bean.setStatus(207);
					bean.setMessage("Data Can not be Updated because it is not found!!");
					return bean;
				}else {
				String [] question=QAbean.getQuestion();
				QABeanDetail.setQuestion((question[0]==null || question[0].equals(""))?QABeanDetail.getQuestion():question[0]);
				QABeanDetail.setType((QAbean.getType()==null || QAbean.getType().equals(""))?QABeanDetail.getType():QAbean.getType());
				QABeanDetail.setCodingDescription((QAbean.getCodingDescription()==null || QAbean.getCodingDescription().equals(""))?QABeanDetail.getCodingDescription():QAbean.getCodingDescription());
				QABeanDetail.setCorporateId((QAbean.getCorporateId()==null || QAbean.getCorporateId().equals(""))?QABeanDetail.getCorporateId():QAbean.getCorporateId());
				QARepo.save(QABeanDetail);
				bean.setStatus(200);
				bean.setMessage("Data Updated Successfully!!");
				}
			}
		}
		return bean;
	}

	@Override
	public QuestionANdanswer getQNADetail(String qNA_id) {		
		return QARepo.findByuIDAndStatusNot(qNA_id,"deleted");
	}

	@Override
	public QuestionAndAnswerResponseBean deleteQNADetail(String qNA_id) {
		QuestionANdanswer QABeanDetail=new QuestionANdanswer();
		QuestionAndAnswerResponseBean bean=new QuestionAndAnswerResponseBean();
		QABeanDetail=QARepo.findByuID(qNA_id);
		if(QABeanDetail==null) {
			bean.setStatus(207);
			bean.setMessage("Data Can not be deleted because it is not found!!");
			return bean;
		}else {
			QABeanDetail.setStatus("deleted");
		}
		QARepo.save(QABeanDetail);
		bean.setStatus(200);
		bean.setMessage("Data deleted successfully!!");
		return bean;
	}

}
