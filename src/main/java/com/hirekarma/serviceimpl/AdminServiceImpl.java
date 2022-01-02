package com.hirekarma.serviceimpl;

import java.sql.Timestamp;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirekarma.model.Job;
import com.hirekarma.repository.AdminRepository;
import com.hirekarma.service.AdminService;

@Service("adminServiceImpl")
public class AdminServiceImpl implements AdminService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AdminServiceImpl.class);
	
	@Autowired
	private AdminRepository adminRepository;

	@Override
	public Job updateActiveStatus(Long id, String status) {
		Job job = null;
		try {
			LOGGER.debug("Inside AdminServiceImpl.updateActiveStatus(-)");
			Optional<Job> optional = adminRepository.findById(id);
			job = optional.get();
			if(job != null)
			{
				job.setStatus(status);
				job.setUpdatedOn(new Timestamp(new java.util.Date().getTime()));
				adminRepository.save(job);
			}
			LOGGER.info("Data Updated Successfully In AdminServiceImpl.updateActiveStatus(-)");
		}
		catch (Exception e) {
			LOGGER.info("Data Updated Failed In AdminServiceImpl.updateActiveStatus(-)"+e);
			throw e;
		}
		return job;
	}

}
