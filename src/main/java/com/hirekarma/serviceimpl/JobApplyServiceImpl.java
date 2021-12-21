package com.hirekarma.serviceimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirekarma.beans.JobApplyBean;
import com.hirekarma.exception.JobApplyException;
import com.hirekarma.model.JobApply;
import com.hirekarma.repository.JobApplyRepository;
import com.hirekarma.service.JobApplyService;

@Service("jobApplyService")
public class JobApplyServiceImpl implements JobApplyService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(JobApplyServiceImpl.class);
	
	@Autowired
	private JobApplyRepository jobApplyRepository;
	
	@Override
	public JobApplyBean insert(JobApplyBean jobApplyBean) {
		LOGGER.debug("Inside JobApplyServiceImpl.insert()");
		JobApply jobApply=null,jobApplyReturn=null;
		JobApplyBean applyBean=null;
		try {
			LOGGER.debug("Inside try block of JobApplyServiceImpl.insert()");
			jobApply=new JobApply();
			BeanUtils.copyProperties(jobApplyBean, jobApply);
			jobApply.setDeleteStatus("Active");
			jobApplyReturn=jobApplyRepository.save(jobApply);
			applyBean=new JobApplyBean();
			BeanUtils.copyProperties(jobApplyReturn, applyBean);
			LOGGER.info("Data saved using JobApplyServiceImpl.insert()");
			return applyBean;
		}
		catch (Exception e) {
			LOGGER.error("Data Insertion failed using JobApplyServiceImpl.insert(-): "+e);
			throw new JobApplyException(e.getMessage());
		}
	}

}
