package com.hirekarma.serviceimpl;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.apache.http.auth.AuthenticationException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.hirekarma.beans.HiringBean;
import com.hirekarma.beans.JobApplyBean;
import com.hirekarma.email.controller.EmailController;
import com.hirekarma.exception.JobApplyException;
import com.hirekarma.model.Corporate;
import com.hirekarma.model.Job;
import com.hirekarma.model.JobApply;
import com.hirekarma.model.Student;
import com.hirekarma.model.UserProfile;
import com.hirekarma.repository.CorporateRepository;
import com.hirekarma.repository.JobApplyRepository;
import com.hirekarma.repository.JobRepository;
import com.hirekarma.repository.StudentRepository;
import com.hirekarma.repository.UserRepository;
import com.hirekarma.service.JobApplyService;
import com.hirekarma.utilty.Validation;

@Service("jobApplyService")
public class JobApplyServiceImpl implements JobApplyService {

	private static final Logger LOGGER = LoggerFactory.getLogger(JobApplyServiceImpl.class);
	
	@Autowired
	private EmailController emailController;

	@Autowired
	private JobApplyRepository jobApplyRepository;

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private JobRepository jobRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CorporateRepository corporateRepository;

	@Override
	public JobApplyBean insert(JobApplyBean jobApplyBean, String token) {
		
		LOGGER.debug("Inside JobApplyServiceImpl.insert()");
		JobApply jobApply = null, jobApplyReturn = null;
		JobApplyBean applyBean = null;
		try {
			LOGGER.debug("Inside try block of JobApplyServiceImpl.insert()");
			String email = Validation.validateToken(token);
			UserProfile userProfile = this.userRepository.findUserByEmail(email);
			Student student = studentRepository.findByStudentEmail(email);
			
			if(student.getProfileUpdationStatus()==null || !student.getProfileUpdationStatus()) {
				throw new Exception("please update your profile first!");
			}
			if(userProfile.getSkills()==null || userProfile.getSkills().isEmpty() ) {
				throw new Exception("please enter some skills!");
			}
			if(userProfile.getEducations()==null || userProfile.getEducations().isEmpty() ) {
				throw new Exception("please complete your education detials");
			}
			Job job = jobRepository.getById(jobApplyBean.getJobId());
			if (job == null) {
				throw new Exception("no such job found");
			}
			
			jobApply = this.jobApplyRepository.findByStudentIdAndJobId(student.getStudentId(), job.getJobId());
			if(jobApply!=null) {
				throw new Exception("Already applied");
			}
			jobApply = new JobApply();
			BeanUtils.copyProperties(jobApplyBean, jobApply);
			jobApply.setDeleteStatus(false);
			jobApply.setApplicationStatus(false);
			jobApply.setCorporateId(job.getCorporateId());
			jobApply.setStudentId(student.getStudentId());
			jobApply.setJobId(job.getJobId());

			jobApplyReturn = jobApplyRepository.save(jobApply);

			applyBean = new JobApplyBean();
			BeanUtils.copyProperties(jobApplyReturn, applyBean);

			LOGGER.info("Data saved using JobApplyServiceImpl.insert()");

			return applyBean;
		} catch (Exception e) {
			LOGGER.error("Data Insertion failed using JobApplyServiceImpl.insert(-): " + e);
			throw new JobApplyException(e.getMessage());
		}
	}
	@Override
	public JSONObject hiringMeet(HiringBean hiringBean,String corporateEmail) throws Exception{
		Optional<JobApply> optional = this.jobApplyRepository.findById(hiringBean.getJobApplyId());
		
		if(!optional.isPresent()) {
			throw new NoSuchElementException("invalid job apply id");
		}
		LOGGER.info("inside hiring meet");
		JobApply jobApply = optional.get();
		Student student = this.studentRepository.findByStudentId(jobApply.getStudentId());
		Corporate corporate = this.corporateRepository.getById(jobApply.getCorporateId());
		if(!corporate.getCorporateEmail().equals(corporateEmail)) {
			throw new AuthenticationException("unauthorized");
		}
		LOGGER.info("got all the details");
		WebClient webClient = WebClient.create();
		String responseJson = webClient.get()
				.uri("https://hirekarma-rd.herokuapp.com/create_event?title="+hiringBean.getTitle()+"&startTime="+hiringBean.getStartTime()+"&endTime="+hiringBean.getEndTime()+"&attendee="+corporate.getCorporateEmail()+"&attendee2="+student.getStudentEmail())
//				 .uri(uriBuilder -> uriBuilder.path("/create_event")
//					        .queryParam("title",hiringBean.getTitle() )
//					        .queryParam("startTime",hiringBean.getStartTime())
//					        .queryParam("endTime", hiringBean.getEndTime())
//					        .queryParam("attendee", corporate.getCorporateEmail())
//					        .queryParam("attendee2", student.getStudentEmail())
//					        .build())
	               .retrieve()
	               .bodyToMono(String.class)
	               .block();
		LOGGER.info("{}",responseJson);
		JSONParser parser = new JSONParser(); 
		
		JSONObject json;
		json = (JSONObject) parser.parse(responseJson);
		JSONObject data = (JSONObject) json.get("data");
		String hangoutLink = (String) data.get("hangoutLink");
		LOGGER.info("{}",hangoutLink);
		jobApply.setMeetLink(hangoutLink);
		this.jobApplyRepository.save(jobApply);
		return json;

	}
@Override
	public void hireStudent(Long jobApplyId,String corporateEmail) throws Exception{
		Optional<JobApply> optional = this.jobApplyRepository.findById(jobApplyId);
		
		if(!optional.isPresent()) {
			throw new NoSuchElementException("invalid job apply id");
		}
		LOGGER.info("inside hireStudent");
		JobApply jobApply = optional.get();
		if(jobApply.getIsHire()) {
			throw new Exception("already hired");
		}
		Student student = this.studentRepository.findByStudentId(jobApply.getStudentId());
		Job job = this.jobRepository.getById(jobApply.getJobId());
		Corporate corporate = this.corporateRepository.getById(jobApply.getCorporateId());
		if(!corporate.getCorporateEmail().equals(corporateEmail)) {
			throw new AuthenticationException("unauthorized");
		}
		jobApply.setIsHire(true);
		this.jobApplyRepository.save(jobApply);
		emailController.hiredNotificationToStudent(student, job, corporate);
	}
}
