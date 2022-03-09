package com.hirekarma.serviceimpl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.json.simple.parser.ParseException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirekarma.beans.OnlineAssesmentResponseBean;
import com.hirekarma.beans.OnlineAssessmentBean;
import com.hirekarma.beans.QuestionAndAnswerStudentResponseBean;
import com.hirekarma.beans.StudentOnlineAssessmentAnswerRequestBean;
import com.hirekarma.model.Corporate;
import com.hirekarma.model.OnlineAssessment;
import com.hirekarma.model.QuestionANdanswer;
import com.hirekarma.model.Student;
import com.hirekarma.model.StudentOnlineAssessment;
import com.hirekarma.model.StudentOnlineAssessmentAnswer;
import com.hirekarma.repository.CorporateRepository;
import com.hirekarma.repository.OnlineAssessmentRepository;
import com.hirekarma.repository.QuestionAndAnswerRepository;
import com.hirekarma.repository.StudentOnlineAssessmentAnswerRepository;
import com.hirekarma.repository.StudentOnlineAssessmentRepository;
import com.hirekarma.repository.StudentRepository;
import com.hirekarma.service.OnlineAssessmentService;
import com.hirekarma.service.StudentOnlineAssessmentService;
import com.hirekarma.utilty.Utility;
import com.hirekarma.utilty.Validation;

@Service("OnlineAssessmentService")
public class OnlineAssessmentServiceImpl implements OnlineAssessmentService {
	@Autowired
	QuestionAndAnswerRepository questionAndAnswerRepository;

	@Autowired
	CorporateRepository corporateRepository;
	
	@Autowired
	StudentRepository studentRepository;
	
	@Autowired
	OnlineAssessmentRepository onlineAssessmentRepository;
	
	@Autowired
	StudentOnlineAssessmentService studentOnlineAssessmentService;
	
	@Autowired
	StudentOnlineAssessmentRepository studentOnlineAssessmentRepository;
	
	@Autowired
	StudentOnlineAssessmentAnswerRepository studentOnlineAssessmentAnswerRepository;
	@Override
	public List<StudentOnlineAssessment> sendOnlineAssessmentToStudents(OnlineAssessmentBean onlineAssessmentBean,String token) throws Exception {
		String email = Validation.validateToken(token);
		Corporate corporate = this.corporateRepository.findByEmail(email);
		Optional<OnlineAssessment> optional = onlineAssessmentRepository.findById(onlineAssessmentBean.getOnlineAssessmentSlug());
		if(!optional.isPresent()) {
			throw new Exception("invalid slug");
		}
		OnlineAssessment onlineAssessment = optional.get();
		List<Student> students = studentRepository.findAllById(onlineAssessmentBean.getStudentIds());
		
//		create a studentOnlineAssessment
		List<StudentOnlineAssessment> studentOnlineAssessments = studentOnlineAssessmentService.createByListOfStudent(students, onlineAssessment);
		
		return studentOnlineAssessments;
	}
	
	@Override
	public List<OnlineAssesmentResponseBean> getAllOnlineAssessmentForStudent(String token) throws ParseException{
		String email = Validation.validateToken(token);
		Student student = this.studentRepository.findByStudentEmail(email);
		List<OnlineAssessment> onlineAssessments = this.studentOnlineAssessmentRepository.findOnlineAssessmentByStudent(student);
		List<OnlineAssesmentResponseBean> onlineAssesmentResponseBeans = new ArrayList<OnlineAssesmentResponseBean>();
		for(OnlineAssessment o: onlineAssessments) {
			OnlineAssesmentResponseBean onlineAssesmentResponseBean = new OnlineAssesmentResponseBean();
			BeanUtils.copyProperties(o, onlineAssesmentResponseBean);
			onlineAssesmentResponseBeans.add(onlineAssesmentResponseBean);

			System.out.println(onlineAssesmentResponseBeans);
		}
		return onlineAssesmentResponseBeans;
	}
	
	@Override
	public OnlineAssessment addOnlineAssessmentByCorporate(OnlineAssessmentBean bean, String token) throws Exception {
		
		String email = Validation.validateToken(token);
		Corporate corporate = corporateRepository.findByEmail(email);
		
		OnlineAssessment onlineAssessment = new OnlineAssessment();
		
		onlineAssessment.setCorporate(corporate);
		return onlineAssessmentRepository.save(updateOnlineAssessmentForBeanNotNull(onlineAssessment, bean));
			
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
	public OnlineAssessment addQuestionToOnlineAssesmentByCorporate(OnlineAssessmentBean onlineAssessmentBean,String token) throws Exception {
		String email = Validation.validateToken(token);
		
		Corporate corporate = corporateRepository.findByEmail(email);
		OnlineAssessment onlineAssessment = onlineAssessmentRepository.getById(onlineAssessmentBean.getOnlineAssessmentSlug());
		
		if(onlineAssessment==null) {
			throw new Exception("onlineAssesment id incorrect");
		}
		
		List<QuestionANdanswer> questionANdanswers =  getQuestionAndAnswerById(onlineAssessmentBean.getQuestions());
		onlineAssessment.getQuestionANdanswers().addAll(questionANdanswers);
		
//		counting total marks
		int totalMarks = onlineAssessment.getTotalMarks();
		for(QuestionANdanswer q: questionANdanswers) {
			if(q.getType().equals("QNA")) {
				totalMarks += onlineAssessment.getQnaMarks();
			}
			else if(q.getType().equals("MCQ")) {
				totalMarks += onlineAssessment.getMcqMarks();
			}
			else if(q.getType().equals("Input")) {
				totalMarks += onlineAssessment.getParagraphMarks();
			}
			else if(q.getType().equals("Coding")) {
				totalMarks += onlineAssessment.getCodingMarks();
			}
		}
		onlineAssessment.setTotalMarks(totalMarks);
		return this.onlineAssessmentRepository.save(onlineAssessment);
		
	}

	public OnlineAssessment updateOnlineAssessmentForBeanNotNull(OnlineAssessment onlineAssessment,OnlineAssessmentBean onlineAssessmentBean) throws Exception {
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
		if(onlineAssessmentBean.getTotalTime()!=0) {
			onlineAssessment.setTotalTime(onlineAssessmentBean.getTotalTime());
		}
		if(onlineAssessmentBean.getScheduledAt()!=null) {
			try{
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			
			LocalDateTime dateTime = LocalDateTime.parse(onlineAssessmentBean.getScheduledAt(), formatter);
			onlineAssessment.setLocalDateTime(dateTime);
			}
			catch(Exception e) {
				throw new Exception("wrong dateFormat");
			}
		}
		return onlineAssessment;
	}
	
	public OnlineAssessment updateOnlineAssessment(OnlineAssessmentBean onlineAssessmentBean,String token,String slug) throws Exception{
		Optional<OnlineAssessment> onlineAssessment = this.onlineAssessmentRepository.findById(slug);
		if(!onlineAssessment.isPresent()) {
			throw new Exception("please enter proper assesemnet id");
		}
		return this.onlineAssessmentRepository.save(updateOnlineAssessmentForBeanNotNull(onlineAssessment.get(),onlineAssessmentBean));
	}
	
	@Override
	public OnlineAssessment updateQuestionOfOnlineAssessmentByCorporate(String onlineAssessmentId,
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
		Optional<OnlineAssessment> onlineAssessment = onlineAssessmentRepository.findById(slug);
		if(!onlineAssessment.isPresent()) {
			throw new Exception("Bad Request");
		}
		return onlineAssessment.get();
	}

	@Override
	public void deleteQuestionofOnlineAssessment(OnlineAssessmentBean onlineAssesmentBean, String slug)
			throws Exception {
		OnlineAssessment onlineAssessment = onlineAssessmentRepository.getById(onlineAssesmentBean.getOnlineAssessmentSlug());
		
		if(onlineAssessment==null) {
			throw new Exception("onlineAssesment id incorrect");
		}
		
		List<QuestionANdanswer> questionANdanswers =  getQuestionAndAnswerById(onlineAssesmentBean.getQuestions());
		if(questionANdanswers.size()!=onlineAssesmentBean.getQuestions().size()) {
			throw new Exception("Please check your list properly");
		}
		
		int totalMarks = onlineAssessment.getTotalMarks();
		for(QuestionANdanswer q: questionANdanswers) {
			if(q.getType().equals("QNA")) {
				totalMarks -= onlineAssessment.getQnaMarks();
			}
			else if(q.getType().equals("MCQ")) {
				totalMarks -= onlineAssessment.getMcqMarks();
			}
			else if(q.getType().equals("Input")) {
				totalMarks -= onlineAssessment.getParagraphMarks();
			}
			else if(q.getType().equals("Coding")) {
				totalMarks -= onlineAssessment.getCodingMarks();
			}
		}
		onlineAssessment.setTotalMarks(totalMarks);
		onlineAssessment.getQuestionANdanswers().removeAll(questionANdanswers);
		
		this.onlineAssessmentRepository.save(onlineAssessment);
		
	}

	@Override
	public List<OnlineAssessmentBean> getOnlineAssesmentsAddedByCorporatedWithoutQNA(String token) throws Exception {
		String email = Validation.validateToken(token);
		Corporate corporate = corporateRepository.findByEmail(email);
		List<OnlineAssessmentBean> onlineAssessmentBeans  = new ArrayList<>();
		for(OnlineAssessment o : corporate.getOnlineAssessments()) {
			OnlineAssessmentBean b = new OnlineAssessmentBean();
			BeanUtils.copyProperties(o, b);
			b.setOnlineAssessmentSlug(o.getSlug());
			onlineAssessmentBeans.add(b);
		}
		return onlineAssessmentBeans;
	}

	@Override
	public List<QuestionAndAnswerStudentResponseBean> getAllQNAForStudentOfOnlineAssessment(String token,
			String onlineAssessmentSlug) throws Exception {
		String email = Validation.validateToken(token);
		Student student = this.studentRepository.findByStudentEmail(email);
		Optional<OnlineAssessment> optional = this.onlineAssessmentRepository.findById(onlineAssessmentSlug);
		if(!optional.isPresent()) {
			throw new Exception("invalid slug");
		}
		
		OnlineAssessment onlineAssessment = optional.get();
		StudentOnlineAssessment studentOnlineAssessment = this.studentOnlineAssessmentRepository.findByStudentAndOnlineAssessment(student, onlineAssessment);
		studentOnlineAssessment.setStartedOn(LocalDateTime.now());
		this.studentOnlineAssessmentRepository.save(studentOnlineAssessment);
		
		List<QuestionAndAnswerStudentResponseBean> questionAndAnswerStudentResponseBeans = new ArrayList<QuestionAndAnswerStudentResponseBean>();
		for(QuestionANdanswer q:onlineAssessment.getQuestionANdanswers()) {
			QuestionAndAnswerStudentResponseBean questionAndAnswerStudentResponseBean = new QuestionAndAnswerStudentResponseBean();
			BeanUtils.copyProperties(q, questionAndAnswerStudentResponseBean);
			questionAndAnswerStudentResponseBeans.add(questionAndAnswerStudentResponseBean);
		}
		return questionAndAnswerStudentResponseBeans;
	}

	@Override
	public void submitAnswerForOnlineAssessmentByStudent(String onlineAssessmentSlug,
			List<StudentOnlineAssessmentAnswerRequestBean> studentOnlineAssessmentAnswerRequestBeans,String token) throws Exception {
		String email = Validation.validateToken(token);
		Student student = this.studentRepository.findByStudentEmail(email);
		List<StudentOnlineAssessmentAnswer> studentOnlineAssessmentAnswers = new ArrayList<>();
		for(StudentOnlineAssessmentAnswerRequestBean s:studentOnlineAssessmentAnswerRequestBeans) {
			StudentOnlineAssessmentAnswer studentOnlineAssessmentAnswer = new StudentOnlineAssessmentAnswer();
			QuestionANdanswer questionANdanswer = this.questionAndAnswerRepository.findByuID(s.getQuestionId());
			studentOnlineAssessmentAnswer.setQuestionANdanswer(questionANdanswer);
			studentOnlineAssessmentAnswer.setAnswer(s.getAnswer());
			studentOnlineAssessmentAnswer.setJsonAnswer(s.getAnswer().toString());
			studentOnlineAssessmentAnswer.setStudent(student);
			System.out.println(s.getAnswer());
			studentOnlineAssessmentAnswers.add(studentOnlineAssessmentAnswer);
		}
		this.studentOnlineAssessmentAnswerRepository.saveAll(studentOnlineAssessmentAnswers);
	}

}
	
	


	
	

