package com.hirekarma.serviceimpl;

import java.sql.Timestamp;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirekarma.beans.ShareJobBean;
import com.hirekarma.beans.UniversityJobShareBean;
import com.hirekarma.model.ShareJob;
import com.hirekarma.model.UniversityJobShare;
import com.hirekarma.repository.ShareJobRepository;
import com.hirekarma.repository.UniversityJobShareRepository;
import com.hirekarma.service.UniversityService;

@Service("universityServiceImpl")
public class UniversityServiceImpl implements UniversityService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UniversityServiceImpl.class);

	@Autowired
	private ShareJobRepository shareJobRepository;
	
	@Autowired
	private UniversityJobShareRepository universityJobShareRepository;

	@Override
	public ShareJobBean universityResponse(ShareJobBean jobBean) {
		ShareJobBean bean = null;
		ShareJob shareJob = null;
		try {
			LOGGER.debug("Inside UniversityServiceImpl.universityResponse(-)");
			Optional<ShareJob> optional = shareJobRepository.findById(jobBean.getShareJobId());
			shareJob = new ShareJob();
			shareJob = optional.get();
			if (shareJob != null) {
				bean = new ShareJobBean();
				shareJob.setUniversityResponseStatus(jobBean.getUniversityResponseStatus());
				shareJob.setRejectionFeedback(jobBean.getRejectionFeedback());
				shareJob.setUpdatedOn(new Timestamp(new java.util.Date().getTime()));
				shareJobRepository.save(shareJob);
				BeanUtils.copyProperties(shareJob, bean);
				bean.setResponse("SUCCESS");
			}
			LOGGER.info("Data Updated Successfully In UniversityServiceImpl.universityResponse(-)");
		} catch (Exception e) {
			bean.setResponse("FAILED");
			LOGGER.info("Data Updatation Failed In UniversityServiceImpl.universityResponse(-)" + e);
			throw e;
		}
		return bean;
	}

	@Override
	public UniversityJobShareBean shareJobStudent(UniversityJobShareBean universityJobShareBean) {

		UniversityJobShareBean jobShareBean = null;
		UniversityJobShare universityJobShare = null;
		
		try {
			LOGGER.debug("Inside UniversityServiceImpl.shareJobStudent(-)");
			if(universityJobShareBean != null) 
			{
				jobShareBean = new UniversityJobShareBean();
				for(int i = 0; i < universityJobShareBean.getStudentId().size(); i++)
				{
					universityJobShare = new UniversityJobShare();
					universityJobShare.setJobId(universityJobShareBean.getJobId());
					universityJobShare.setUniversityId(universityJobShareBean.getUniversityId());
					universityJobShare.setJobStatus("ACTIVE");
					universityJobShare.setStudentId(universityJobShareBean.getStudentId().get(i));
					universityJobShare.setCreatedBy("Biswa");
					universityJobShare.setCreatedOn(new Timestamp(new java.util.Date().getTime()));
					
					universityJobShareRepository.save(universityJobShare);
				}
				BeanUtils.copyProperties(universityJobShare, jobShareBean);
				jobShareBean.setResponse("SHARED");
				LOGGER.info("Data Updated Successfully In UniversityServiceImpl.shareJobStudent(-)");
			}
			
		} catch (Exception e) {
			jobShareBean.setResponse("FAILED");
			LOGGER.info("Data Updatation Failed In UniversityServiceImpl.shareJobStudent(-)" + e);
			throw e;
		}
		
		return jobShareBean;
	}

}
