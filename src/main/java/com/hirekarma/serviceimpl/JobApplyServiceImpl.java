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
import com.hirekarma.model.JobApply;
import com.hirekarma.model.Student;
import com.hirekarma.repository.JobApplyRepository;
import com.hirekarma.repository.StudentRepository;
import com.hirekarma.service.JobApplyService;

@Service("jobApplyService")
public class JobApplyServiceImpl implements JobApplyService {

	private static final Logger LOGGER = LoggerFactory.getLogger(JobApplyServiceImpl.class);

	@Autowired
	private JobApplyRepository jobApplyRepository;

	@Autowired
	private StudentRepository studentRepository;

	@Override
	public JobApplyBean insert(JobApplyBean jobApplyBean, String token) {
		LOGGER.debug("Inside JobApplyServiceImpl.insert()");
		JobApply jobApply = null, jobApplyReturn = null;
		JobApplyBean applyBean = null;
		List<Student> studentList = new ArrayList<Student>();
		try {
			LOGGER.debug("Inside try block of JobApplyServiceImpl.insert()");

			String[] chunks1 = token.split(" ");
			String[] chunks = chunks1[1].split("\\.");
			Base64.Decoder decoder = Base64.getUrlDecoder();

			String payload = new String(decoder.decode(chunks[1]));
			JSONParser jsonParser = new JSONParser();
			Object obj = jsonParser.parse(payload);

			JSONObject jsonObject = (JSONObject) obj;

			String email = (String) jsonObject.get("sub");

			studentList = studentRepository.getDetailsByEmail1(email);

			if (studentList != null && studentList.size() <= 1) {

				jobApply = new JobApply();
				BeanUtils.copyProperties(jobApplyBean, jobApply);
				jobApply.setDeleteStatus(false);
				jobApply.setApplicationStatus(false);
				jobApply.setCorporateId(jobApplyBean.getCorporateId());
				jobApply.setStudentId(jobApplyBean.getStudentId());
				jobApply.setJobId(jobApplyBean.getJobId());

				jobApplyReturn = jobApplyRepository.save(jobApply);

				applyBean = new JobApplyBean();
				BeanUtils.copyProperties(jobApplyReturn, applyBean);

				LOGGER.info("Data saved using JobApplyServiceImpl.insert()");
			}
			return applyBean;
		} catch (Exception e) {
			LOGGER.error("Data Insertion failed using JobApplyServiceImpl.insert(-): " + e);
			throw new JobApplyException(e.getMessage());
		}
	}

}
