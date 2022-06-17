package com.hirekarma.serviceimpl;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirekarma.beans.OnlineAssesmentResponseBean;
import com.hirekarma.beans.OnlineAssessmentBean;
import com.hirekarma.beans.QuestionAndAnswerStudentResponseBean;
import com.hirekarma.beans.StudentOnlineAssessmentAnswerRequestBean;
import com.hirekarma.controller.UniversityUserController;
import com.hirekarma.email.controller.EmailController;
import com.hirekarma.model.Corporate;
import com.hirekarma.model.OnlineAssessment;
import com.hirekarma.model.QuestionANdanswer;
import com.hirekarma.model.Student;
import com.hirekarma.model.StudentOnlineAssessment;
import com.hirekarma.model.StudentOnlineAssessmentAnswer;
import com.hirekarma.model.University;
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
	
	private static final Logger logger = LoggerFactory.getLogger(OnlineAssessmentServiceImpl.class);
	
	@Autowired
	EmailController emailController;
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
	public List<StudentOnlineAssessment> sendOnlineAssessmentToStudents(OnlineAssessmentBean onlineAssessmentBean) throws Exception {
		
		Optional<OnlineAssessment> optional = onlineAssessmentRepository.findById(onlineAssessmentBean.getOnlineAssessmentSlug());
		if(!optional.isPresent()) {
			throw new Exception("invalid slug");
		}
		OnlineAssessment onlineAssessment = optional.get();
		List<Student> students = studentRepository.findAllById(onlineAssessmentBean.getStudentIds());
		
//		create a studentOnlineAssessment
		List<StudentOnlineAssessment> studentOnlineAssessments = studentOnlineAssessmentService.createByListOfStudent(students, onlineAssessment);
		emailController.onlineAssessmentEmail(studentOnlineAssessments);
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
	public OnlineAssessment addOnlineAssessmentByCorporate(OnlineAssessmentBean bean, Corporate corporate) throws Exception {
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
	public OnlineAssessment addQuestionToOnlineAssesmentByCorporate(OnlineAssessmentBean onlineAssessmentBean,Corporate corporate) throws Exception {
/*
Note :- using onlineAssessment - > questionAndAnswer relation

 */
		
	
		OnlineAssessment onlineAssessment = onlineAssessmentRepository.getById(onlineAssessmentBean.getOnlineAssessmentSlug());
		
		if(onlineAssessment==null) {
			throw new Exception("onlineAssesment id incorrect");
		}
		if(onlineAssessment.getCorporate().getCorporateId().compareTo(corporate.getCorporateId())!=0){
			throw new Exception("unauthorized");
		}
		List<QuestionANdanswer> questionANdanswers =  getQuestionAndAnswerById(onlineAssessmentBean.getQuestions());
		List<QuestionANdanswer> questionANdanswersToBeAdded = new ArrayList<QuestionANdanswer>();
		logger.info("question and answer -> {} ",questionANdanswers);
//		checking if qna already exist
		for(QuestionANdanswer q:questionANdanswers) {
			if(!onlineAssessment.getQuestionANdanswers().contains(q)) {
				onlineAssessment.getQuestionANdanswers().add(q);
			}
		}
		
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
		logger.info("saving totla marks {}",totalMarks);
		logger.info("succesffully completed the function");
		return this.onlineAssessmentRepository.save(onlineAssessment);
		
	}

	public OnlineAssessment updateOnlineAssessmentForBeanNotNull(OnlineAssessment onlineAssessment,OnlineAssessmentBean onlineAssessmentBean) throws Exception {
		logger.info("inside updateOnlineAssessmentForBeanNotNull()");
		//		System.out.println(onlineAssessmentBean.toString());
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
		logger.info(" exiting updateOnlineAssessmentForBeanNotNull()");
		return onlineAssessment;
	}
	@Override
	public OnlineAssessment updateOnlineAssessmentByCorporate(OnlineAssessmentBean onlineAssessmentBean,Corporate corporate,String slug) throws Exception{
		Optional<OnlineAssessment> onlineAssessmentOptional = this.onlineAssessmentRepository.findById(slug);
		if(!onlineAssessmentOptional.isPresent()) {
			throw new Exception("please enter proper assesemnet id");
		}
		OnlineAssessment onlineAssessment = onlineAssessmentOptional.get();
		if(onlineAssessment.getCorporate()==null||onlineAssessment.getCorporate().getCorporateId().compareTo(corporate.getCorporateId())!=0) {
			throw new Exception("unauthorized");
		}
		return this.onlineAssessmentRepository.save(updateOnlineAssessmentForBeanNotNull(onlineAssessment,onlineAssessmentBean));
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
	public OnlineAssessment getCorporateOnlineAssessmentBySlug(Corporate corporate, String slug) throws Exception {
		
		Optional<OnlineAssessment> onlineAssessmentOptional = onlineAssessmentRepository.findById(slug);
		if(!onlineAssessmentOptional.isPresent()) {
			throw new Exception("invalid slug");
		}
		OnlineAssessment onlineAssessment = onlineAssessmentOptional.get();
		if(onlineAssessment.getCorporate().getCorporateId().compareTo(corporate.getCorporateId())!=0) {
			throw new Exception("unauthorized");
		}
		return onlineAssessment;
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
	public List<OnlineAssessmentBean> getOnlineAssesmentsAddedByCorporatedWithoutQNA(Corporate corporate) throws Exception {
		
		List<OnlineAssessmentBean> onlineAssessmentBeans  = new ArrayList<>();
		for(OnlineAssessment o : this.onlineAssessmentRepository.findAllByCorporate(corporate)) {
			OnlineAssessmentBean b = new OnlineAssessmentBean();
			BeanUtils.copyProperties(o, b);
			b.setOnlineAssessmentSlug(o.getSlug());
			onlineAssessmentBeans.add(b);
		}
		return onlineAssessmentBeans;
	}

	@Override
	public Map<String,Object> getAllQNAForStudentForOnlineAssessment(String token,
			String studentOnlineAssessmentSlug) throws Exception {
//	finding student
		String email = Validation.validateToken(token);
		Student student = this.studentRepository.findByStudentEmail(email);
//		online assesment validation
		Optional<StudentOnlineAssessment> optionalStudentOnlineAssessment = this.studentOnlineAssessmentRepository.findById(studentOnlineAssessmentSlug);
		if(!optionalStudentOnlineAssessment.isPresent()) {
			throw new Exception("invalid slug");
		}
		StudentOnlineAssessment studentOnlineAssessment = optionalStudentOnlineAssessment.get();
		OnlineAssessment onlineAssessment = studentOnlineAssessment.getOnlineAssessment();
		if(onlineAssessment.getDeleteStatus()) {
			throw new Exception("no such assessment");
		}
		if(studentOnlineAssessment!=null && studentOnlineAssessment.getStartedOn()!=null) {
			 Duration duration = Duration.between(studentOnlineAssessment.getStartedOn(), LocalDateTime.now());
			 long minutes = duration.toMinutes();
//			long minutes = ChronoUnit.MINUTES.between(studentOnlineAssessment.getStartedOn(), LocalDateTime.now());
			if(onlineAssessment.getTotalTime() < minutes  ) {
				throw new Exception("already attempted the test");
			}
		}
		else {
			studentOnlineAssessment.setStartedOn(LocalDateTime.now());
		}
//		setting the start timer
		
		studentOnlineAssessment.setStudentResponse(true);
		this.studentOnlineAssessmentRepository.save(studentOnlineAssessment);
//		get all question without answer
		List<QuestionAndAnswerStudentResponseBean> questionAndAnswerStudentResponseBeans = new ArrayList<QuestionAndAnswerStudentResponseBean>();
		for(QuestionANdanswer q:onlineAssessment.getQuestionANdanswers()) {
			QuestionAndAnswerStudentResponseBean questionAndAnswerStudentResponseBean = new QuestionAndAnswerStudentResponseBean();
			BeanUtils.copyProperties(q, questionAndAnswerStudentResponseBean);
			questionAndAnswerStudentResponseBeans.add(questionAndAnswerStudentResponseBean);
		}
//		inserting values
		Map<String,Object> response = new HashMap<String, Object>();
		response.put("QNA",questionAndAnswerStudentResponseBeans);
		onlineAssessment.setCorporate(null);
		onlineAssessment.setQuestionANdanswers(null);
		response.put("onlineAssessment", onlineAssessment);
		return response;
	}

	@Override
	public void submitAnswerForOnlineAssessmentByStudent(String studentOnlineAssessmentSlug,
			List<StudentOnlineAssessmentAnswerRequestBean> studentOnlineAssessmentAnswerRequestBeans,String token) throws Exception {
		String email = Validation.validateToken(token);
		Student student = this.studentRepository.findByStudentEmail(email);
//		getting studentONlineAssesment from slug
		Optional<StudentOnlineAssessment> optionalStudentOnlineAssessment = this.studentOnlineAssessmentRepository.findById(studentOnlineAssessmentSlug);
		if(!optionalStudentOnlineAssessment.isPresent()) {
			throw new Exception("invalid slug");
		}
		StudentOnlineAssessment studentOnlineAssessment = optionalStudentOnlineAssessment.get();		
		OnlineAssessment onlineAssessment = studentOnlineAssessment.getOnlineAssessment();

		int totalMarks = 0;
//		saving all the answera
		List<StudentOnlineAssessmentAnswer> studentOnlineAssessmentAnswers = new ArrayList<>();
		for(StudentOnlineAssessmentAnswerRequestBean s:studentOnlineAssessmentAnswerRequestBeans) {
			StudentOnlineAssessmentAnswer studentOnlineAssessmentAnswer = new StudentOnlineAssessmentAnswer();
			QuestionANdanswer questionANdanswer = this.questionAndAnswerRepository.findByuID(s.getQuestionId());
			if(questionANdanswer.getType().equals("MCQ")) {
				if(s.getAnswer()!=null) {
					String mcqAnswer = (String) s.getAnswer().get("answer");
					if(mcqAnswer.equals(questionANdanswer.getCorrectOption())) {
						totalMarks = totalMarks+ onlineAssessment.getMcqMarks();
					}
				}
			}
			else if(questionANdanswer.getType().equals("Coding")) {
			
					if(s.getAnswer()!=null) {
						Integer testCasePassed = (Integer) s.getAnswer().get("testCasesPased");
						
						totalMarks = totalMarks+ onlineAssessment.getCodingMarks()*testCasePassed;
					
					}
				
			}
			studentOnlineAssessmentAnswer.setQuestionANdanswer(questionANdanswer);
			studentOnlineAssessmentAnswer.setAnswer(s.getAnswer());
			studentOnlineAssessmentAnswer.setStudentOnlineAssessment(studentOnlineAssessment);
			studentOnlineAssessmentAnswer.setJsonAnswer(s.getAnswer().toJSONString());
			studentOnlineAssessmentAnswer.setStudent(student);
			studentOnlineAssessmentAnswer.setOnlineAssessment(onlineAssessment);
			System.out.println(s.getAnswer());
			studentOnlineAssessmentAnswers.add(studentOnlineAssessmentAnswer);
		}
		studentOnlineAssessment.setTotalMarksObtained(totalMarks);
		this.studentOnlineAssessmentRepository.save(studentOnlineAssessment); 
		this.studentOnlineAssessmentAnswerRepository.saveAll(studentOnlineAssessmentAnswers);
	}

	@Override
	public void deleteOnlineAssessmentBySlugAndCorporate(String slug, Corporate corporate) throws Exception {

		OnlineAssessment onlineAssessment = this.onlineAssessmentRepository.findBySlug(slug);
		if(onlineAssessment==null) {
			throw new NoSuchElementException("invalid bad request");
		}
		if(onlineAssessment.getCorporate().getCorporateId().compareTo(corporate.getCorporateId())!=0){
			throw new Exception("unauthorized");
		}
		onlineAssessment.setDeleteStatus(true);
		this.onlineAssessmentRepository.save(onlineAssessment);
	}
	
	@Override
	public void deleteOnlineAssessmentBySlugAndUniversity(String slug, University university) throws Exception {

		OnlineAssessment onlineAssessment = this.onlineAssessmentRepository.findBySlug(slug);
		if(onlineAssessment==null) {
			throw new NoSuchElementException("invalid bad request");
		}
		if(onlineAssessment.getUniversity().getUniversityId().compareTo(university.getUniversityId())!=0) {
			throw new Exception("invalid university Id");
		}
		
		onlineAssessment.setDeleteStatus(true);
		this.onlineAssessmentRepository.save(onlineAssessment);
	}
	@Override
	public void deleteOnlineAssessmentBySlugAndAdmin(String slug) throws Exception {

		OnlineAssessment onlineAssessment = this.onlineAssessmentRepository.findBySlug(slug);
		if(onlineAssessment==null) {
			throw new NoSuchElementException("invalid bad request");
		}
		if(onlineAssessment.getUniversity()!=null || onlineAssessment.getCorporate()!=null) {
			throw new Exception("admin access only");
		}
		onlineAssessment.setDeleteStatus(true);
		this.onlineAssessmentRepository.save(onlineAssessment);
	}
	@Override
	public void deleteOnlineAssessmentBySlugAndToken(String slug, String token) throws Exception {
		String email = Validation.validateToken(token);
		Corporate corporate = this.corporateRepository.findByEmail(email);
		deleteOnlineAssessmentBySlugAndCorporate(slug,corporate);
		
	}

	@Override
	public OnlineAssessment addOnlineAssessmentByUniversity(OnlineAssessmentBean bean, University university)
			throws Exception {
		OnlineAssessment onlineAssessment = new OnlineAssessment();
		onlineAssessment.setUniversity(university);
		return onlineAssessmentRepository.save(updateOnlineAssessmentForBeanNotNull(onlineAssessment, bean));
	}

	@Override
	public OnlineAssessment addQuestionToOnlineAssesmentByUniversity(OnlineAssessmentBean onlineAssessmentBean,
			University university) throws Exception {
		
		OnlineAssessment onlineAssessment = onlineAssessmentRepository.getById(onlineAssessmentBean.getOnlineAssessmentSlug());
		if(onlineAssessment==null) {
			throw new Exception("onlineAssesment id incorrect");
		}
		if(onlineAssessment.getUniversity().getUniversityId().compareTo(university.getUniversityId())!=0) {
			throw new Exception("invalid university Id");
		}
		
		
		List<QuestionANdanswer> questionANdanswers =  getQuestionAndAnswerById(onlineAssessmentBean.getQuestions());
		List<QuestionANdanswer> questionANdanswersToBeAdded = new ArrayList<QuestionANdanswer>();
//		logger.info("question and answer -> {} ",questionANdanswers);
		for(QuestionANdanswer q:questionANdanswers) {
			if(!onlineAssessment.getQuestionANdanswers().contains(q)) {
				onlineAssessment.getQuestionANdanswers().add(q);
			}
		}
		
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
		logger.info("saving totla marks {}",totalMarks);
		logger.info("succesffully completed the function");
		return this.onlineAssessmentRepository.save(onlineAssessment);
	}

	
//-------------------------- admin ---------------------------------

	@Override
	public List<OnlineAssessmentBean> getOnlineAssesmentsAddedByAdminWithoutQNA() throws Exception {
		
		List<OnlineAssessmentBean> onlineAssessmentBeans  = new ArrayList<>();
		for(OnlineAssessment o : this.onlineAssessmentRepository.findAllByAdmin()) {
			OnlineAssessmentBean b = new OnlineAssessmentBean();
			BeanUtils.copyProperties(o, b);
			b.setOnlineAssessmentSlug(o.getSlug());
			onlineAssessmentBeans.add(b);
		}
		return onlineAssessmentBeans;
	}
	@Override
	public OnlineAssessment addOnlineAssessmentByAdmin(OnlineAssessmentBean bean) throws Exception {
		
		OnlineAssessment onlineAssessment = new OnlineAssessment();
		
		return onlineAssessmentRepository.save(updateOnlineAssessmentForBeanNotNull(onlineAssessment, bean));
	}

	@Override
	public List<OnlineAssessment> getOnlineAssessmentCreatedByAdmin() {
		List<OnlineAssessment> onlineAssessments = this.onlineAssessmentRepository.findAllByAdmin();
		return onlineAssessments;
	}
	@Override
	public OnlineAssessment addQuestionToOnlineAssesmentByAdmin(OnlineAssessmentBean onlineAssessmentBean) throws Exception {
		
		OnlineAssessment onlineAssessment = onlineAssessmentRepository.getById(onlineAssessmentBean.getOnlineAssessmentSlug());
		
		if(onlineAssessment.getUniversity()!=null || onlineAssessment.getCorporate()!=null) {
			throw new Exception("admin access only");
		}
		if(onlineAssessment==null) {
			throw new Exception("onlineAssesment id incorrect");
		}
		
		List<QuestionANdanswer> questionANdanswers =  getQuestionAndAnswerById(onlineAssessmentBean.getQuestions());
		List<QuestionANdanswer> questionANdanswersToBeAdded = new ArrayList<QuestionANdanswer>();
//		logger.info("question and answer -> {} ",questionANdanswers);
		for(QuestionANdanswer q:questionANdanswers) {
			if(!onlineAssessment.getQuestionANdanswers().contains(q)) {
				onlineAssessment.getQuestionANdanswers().add(q);
			}
		}
		
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
		logger.info("saving totla marks {}",totalMarks);
		logger.info("succesffully completed the function");
		return this.onlineAssessmentRepository.save(onlineAssessment);
	}

	// get all assessment created by admin for public student
	@Override
	public List<OnlineAssesmentResponseBean> getAllOnlineAssessmentForPublic() throws Exception {		
		List<OnlineAssessment> onlineAssessments = this.onlineAssessmentRepository.findAllForPublic();
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
	public Map<String, Object> getAllQNAForPublicForAssessement(String onlineAssessmentSlug) throws Exception {

//			online assesment validation
			Optional<OnlineAssessment> optional = this.onlineAssessmentRepository.findById(onlineAssessmentSlug);
			if(!optional.isPresent()) {
				throw new Exception("invalid slug");
			}		
			OnlineAssessment onlineAssessment = optional.get();
			if(!onlineAssessment.isPublicly_available()) {
				throw new Exception("please sign up to continue the assessment");
			}
//			get all question without answer
			List<QuestionAndAnswerStudentResponseBean> questionAndAnswerStudentResponseBeans = new ArrayList<QuestionAndAnswerStudentResponseBean>();
			for(QuestionANdanswer q:onlineAssessment.getQuestionANdanswers()) {
				QuestionAndAnswerStudentResponseBean questionAndAnswerStudentResponseBean = new QuestionAndAnswerStudentResponseBean();
				BeanUtils.copyProperties(q, questionAndAnswerStudentResponseBean);
				questionAndAnswerStudentResponseBeans.add(questionAndAnswerStudentResponseBean);
			}
//			inserting values
			Map<String,Object> response = new HashMap<String, Object>();
			response.put("QNA",questionAndAnswerStudentResponseBeans);
			onlineAssessment.setCorporate(null);
			onlineAssessment.setQuestionANdanswers(null);
			response.put("onlineAssessment", onlineAssessment);
			return response;
	}

//	answer for public assesment
	@Override
	public Map<String, Object> submitResponseForPublicAssessment(String onlineAssesmentSlug,List<StudentOnlineAssessmentAnswerRequestBean> studentOnlineAssessmentAnswerRequestBeans) throws Exception {
	
		Map<String,Object> response = new HashMap<String,Object>(); 
		
		OnlineAssessment onlineAssessment = this.onlineAssessmentRepository.findBySlug(onlineAssesmentSlug);
		if(onlineAssessment==null) {
			throw new Exception("invalid slug");
		}
		int totalMarks = 0;
//		saving all the answera
		List<StudentOnlineAssessmentAnswer> studentOnlineAssessmentAnswers = new ArrayList<>();
		for(StudentOnlineAssessmentAnswerRequestBean s:studentOnlineAssessmentAnswerRequestBeans) {
			StudentOnlineAssessmentAnswer studentOnlineAssessmentAnswer = new StudentOnlineAssessmentAnswer();
			QuestionANdanswer questionANdanswer = this.questionAndAnswerRepository.findByuID(s.getQuestionId());
			if(questionANdanswer.getType().equals("MCQ")) {
				if(s.getAnswer()!=null) {
					String mcqAnswer = (String) s.getAnswer().get("answer");
					if(mcqAnswer.equals(questionANdanswer.getCorrectOption())) {
						totalMarks = totalMarks+ onlineAssessment.getMcqMarks();
					}
				}
			}
			else if(questionANdanswer.getType().equals("Coding")) {
				if(questionANdanswer.getType().equals("MCQ")) {
					if(s.getAnswer()!=null) {
						Integer testCasePassed = (Integer) s.getAnswer().get("testCasesPased");
						totalMarks = totalMarks+ onlineAssessment.getCodingMarks()*testCasePassed;
					
					}
				}
			}
			studentOnlineAssessmentAnswer.setQuestionANdanswer(questionANdanswer);
			studentOnlineAssessmentAnswer.setAnswer(s.getAnswer());
			studentOnlineAssessmentAnswer.setJsonAnswer(s.getAnswer().toJSONString());
			studentOnlineAssessmentAnswer.setOnlineAssessment(onlineAssessment);

			studentOnlineAssessmentAnswers.add(studentOnlineAssessmentAnswer);
	}
	response.put("studentOnlineAssessmentAnswers", studentOnlineAssessmentAnswers);
	response.put("onlineAssessment", onlineAssessment);
	response.put("totalMarks", totalMarks);
	return response;
	}
//	update online assessment
	@Override
	public OnlineAssessment updateOnlineAssessmentByAdmin(OnlineAssessmentBean onlineAssessmentBean,String slug) throws Exception {
		Optional<OnlineAssessment> onlineAssessmentOptional = this.onlineAssessmentRepository.findById(slug);
		if(!onlineAssessmentOptional.isPresent()) {
			throw new Exception("please enter proper assessment slug");
		}
		OnlineAssessment onlineAssessment = onlineAssessmentOptional.get();
		if(onlineAssessment.getCorporate()!=null || onlineAssessment.getUniversity()!=null) {
			throw new Exception("unauthorized");			
		}
		return this.onlineAssessmentRepository.save(updateOnlineAssessmentForBeanNotNull(onlineAssessment,onlineAssessmentBean));
	}
	
	@Override
	public OnlineAssessment getAdminOnlineAssessmentBySlug( String slug) throws Exception {
		
		Optional<OnlineAssessment> onlineAssessmentOptional = onlineAssessmentRepository.findById(slug);
		if(!onlineAssessmentOptional.isPresent()) {
			throw new Exception("invalid slug");
		}
		OnlineAssessment onlineAssessment = onlineAssessmentOptional.get();
		if(onlineAssessment.getCorporate()!=null || onlineAssessment.getUniversity()!=null) {
			throw new Exception("unauthorized");
		}
		return onlineAssessment;
	}

//------------------------------------- UNIVERSITY ---------------------
	
//	get all assessment
	@Override
	public List<OnlineAssessmentBean> getOnlineAssesmentsAddedByUniversityWithoutQNA(University university)
			throws Exception {
		List<OnlineAssessmentBean> onlineAssessmentBeans  = new ArrayList<>();
		for(OnlineAssessment o : this.onlineAssessmentRepository.findAllByUniversity(university)) {
			OnlineAssessmentBean b = new OnlineAssessmentBean();
			BeanUtils.copyProperties(o, b);
			b.setOnlineAssessmentSlug(o.getSlug());
			onlineAssessmentBeans.add(b);
		}
		return onlineAssessmentBeans;
	}
	
	@Override
	public OnlineAssessment updateOnlineAssessmentByUniversity(OnlineAssessmentBean onlineAssessmentBean,
			University university,String slug) throws Exception {
		Optional<OnlineAssessment> onlineAssessmentOptional = this.onlineAssessmentRepository.findById(slug);
		if(!onlineAssessmentOptional.isPresent()) {
			throw new Exception("please enter proper assessment slug");
		}
		OnlineAssessment onlineAssessment = onlineAssessmentOptional.get();
		if(onlineAssessment.getUniversity()==null ||onlineAssessment.getUniversity().getUniversityId().compareTo(university.getUniversityId())!=0) {
			throw new Exception("unauthorized");
		}
		
		return this.onlineAssessmentRepository.save(updateOnlineAssessmentForBeanNotNull(onlineAssessment,onlineAssessmentBean));
	}
	@Override
	public OnlineAssessment getUniversityOnlineAssessmentBySlug(University university, String slug) throws Exception {
		
		Optional<OnlineAssessment> onlineAssessmentOptional = onlineAssessmentRepository.findById(slug);
		if(!onlineAssessmentOptional.isPresent()) {
			throw new Exception("invalid slug");
		}
		OnlineAssessment onlineAssessment = onlineAssessmentOptional.get();
		if(onlineAssessment.getUniversity().getUniversityId().compareTo(university.getUniversityId())!=0) {
			throw new Exception("unauthorized");
		}
		return onlineAssessment;
	}
}
	
	


	
	

