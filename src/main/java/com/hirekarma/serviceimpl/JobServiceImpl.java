package com.hirekarma.serviceimpl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirekarma.beans.JobBean;
import com.hirekarma.exception.JobException;
import com.hirekarma.model.Job;
import com.hirekarma.repository.JobRepository;
import com.hirekarma.service.JobService;

@Service("jobService")
public class JobServiceImpl implements JobService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(JobServiceImpl.class);
	
	@Autowired
	private JobRepository jobRepository;

	@Override
	public JobBean insert(JobBean jobBean) {
		LOGGER.debug("Inside JobServiceImpl.insert()");
		Job job=null;
		Job jobReturn=null;
		JobBean bean=null;
		byte[] image=null;
		try {
			LOGGER.debug("Inside try block of JobServiceImpl.insert()");
			image=jobBean.getFile().getBytes();
			jobBean.setDescriptionFile(image);
			jobBean.setStatus("Not Active");
			jobBean.setDeleteStatus("Active");
			job=new Job();
			BeanUtils.copyProperties(jobBean, job);
			jobReturn=jobRepository.save(job);
			bean=new JobBean();
			BeanUtils.copyProperties(jobReturn, bean);
			LOGGER.info("Data successfully saved using JobServiceImpl.insert(-)");
			return bean;
		}
		catch (Exception e) {
			LOGGER.error("Data Insertion failed using JobServiceImpl.insert(-): "+e);
			throw new JobException(e.getMessage());
		}
	}

	@Override
	public List<JobBean> findJobsByCorporateId(Long corpUserId) {
		LOGGER.debug("Inside JobServiceImpl.findJobsByCorporateId(-)");
		List<Job> jobs=null;
		List<JobBean> jobBeans=null;
		JobBean jobBean=null;
		boolean flag=false;
		try {
			LOGGER.debug("Inside try block of JobServiceImpl.findJobsByCorporateId(-)");
			jobs=jobRepository.findJobsByCorporateId(corpUserId);
			if(jobs!=null) {
				if(jobs.size()>0) {
					jobBeans=new ArrayList<JobBean>();
					for (Job job : jobs) {
						jobBean=new JobBean();
						BeanUtils.copyProperties(job, jobBean);
						jobBeans.add(jobBean);
					}
					flag=true;
				}
				else {
					flag=false;
				}
			}
			else {
				flag=false;
			}
			if(flag) {
				LOGGER.info("Data successfully fetched using JobServiceImpl.findJobsByCorporateId(-)");
				return jobBeans;
			}
			else {
				LOGGER.info("No jobs are there. Get Result using JobServiceImpl.findJobsByCorporateId(-)");
				return jobBeans;
			}
		}
		catch (Exception e) {
			LOGGER.error("Error occured in JobServiceImpl.findJobsByCorporateId(-): "+e);
			throw new JobException(e.getMessage());
		}
	}

	@Override
	public JobBean findJobById(Long jobId) {
		LOGGER.debug("Inside JobServiceImpl.findJobById(-)");
		Job job=null;
		Optional<Job> optional=null;
		JobBean jobBean=null;
		try {
			LOGGER.debug("Inside try block of JobServiceImpl.findJobById(-)");
			optional=jobRepository.findById(jobId);
			if(!optional.isEmpty()) {
				job=optional.get();
				if(job!=null) {
					jobBean=new JobBean();
					BeanUtils.copyProperties(job, jobBean);
					LOGGER.info("Data Successfully fetched using JobServiceImpl.findJobById(-)");
				}
			}
			return jobBean;
		}
		catch (Exception e) {
			LOGGER.error("Error occured in JobServiceImpl.findJobById(-): "+e);
			throw new JobException(e.getMessage());
		}
	}

	@Override
	public List<JobBean> deleteJobById(Long jobId) {
		LOGGER.debug("Inside JobServiceImpl.deleteJobById(-)");
		Job job=null;
		Optional<Job> optional=null;
		Long corpUserId=null;
		List<JobBean> jobBeans=null;
		try {
			LOGGER.debug("Inside try block of JobServiceImpl.deleteJobById(-)");
			optional=jobRepository.findById(jobId);
			if(!optional.isEmpty()) {
				job=optional.get();
				if(job!=null) {
					corpUserId=job.getCorpUserId();
					job.setDeleteStatus("InActive");
					job.setUpdatedOn(new Timestamp(new java.util.Date().getTime()));
					jobRepository.save(job);
					jobBeans=findJobsByCorporateId(corpUserId);
				}
			}
			LOGGER.debug("Data Successfully Deleted using JobServiceImpl.findJobById(-)");
			return jobBeans;
		}
		catch (Exception e) {
			LOGGER.error("Error occured in JobServiceImpl.deleteJobById(-): "+e);
			throw new JobException(e.getMessage());
		}
	}

	@Override
	public JobBean updateJobById(JobBean jobBean) {
		LOGGER.debug("Inside JobServiceImpl.updateJobById(-)");
		Job job=null;
		Job jobReturn=null;
		Optional<Job> optional=null;
		JobBean bean=null;
		byte[] image=null;
		try {
			LOGGER.debug("Inside try block of JobServiceImpl.updateJobById(-)");
			image=jobBean.getFile().getBytes();
			jobBean.setDescriptionFile(image);
			optional=jobRepository.findById(jobBean.getJobId());
			if(!optional.isEmpty()) {
				job=optional.get();
				if(job!=null) {
					job.setJobTitle(jobBean.getJobTitle());
					job.setCategory(jobBean.getCategory());
					job.setJobType(jobBean.getJobType());
					job.setWfhCheckbox(jobBean.getWfhCheckbox());
					job.setSkills(jobBean.getSkills());
					job.setCity(jobBean.getCity());
					job.setOpenings(jobBean.getOpenings());
					job.setSalary(jobBean.getSalary());
					job.setAbout(jobBean.getAbout());
					job.setDescription(jobBean.getDescription());
					job.setDescriptionFile(jobBean.getDescriptionFile());
					job.setUpdatedOn(new Timestamp(new java.util.Date().getTime()));
					jobReturn=jobRepository.save(job);
					bean=new JobBean();
					BeanUtils.copyProperties(jobReturn, bean);
					LOGGER.info("Data Successfully updated using JobServiceImpl.updateJobById(-)");
				}
			}
			return bean;
		}
		catch (Exception e) {
			LOGGER.error("Error occured in JobServiceImpl.updateJobById(-): "+e);
			throw new JobException(e.getMessage());
		}
	}

}
