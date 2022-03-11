package com.hirekarma.serviceimpl;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirekarma.beans.JobApplyBean;
import com.hirekarma.exception.JobApplyException;
import com.hirekarma.model.Job;
import com.hirekarma.model.JobApply;
import com.hirekarma.model.Student;
import com.hirekarma.model.UserProfile;
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
	private JobApplyRepository jobApplyRepository;

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private JobRepository jobRepository;
	
	@Autowired
	private UserRepository userRepository;

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

}
