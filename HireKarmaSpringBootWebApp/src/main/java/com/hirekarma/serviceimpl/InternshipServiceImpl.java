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

import com.hirekarma.beans.InternshipBean;
import com.hirekarma.exception.InternshipException;
import com.hirekarma.model.Internship;
import com.hirekarma.repository.InternshipRepository;
import com.hirekarma.service.InternshipService;

@Service("internshipService")
public class InternshipServiceImpl implements InternshipService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(InternshipServiceImpl.class);
	
	@Autowired
	private InternshipRepository internshipRepository;

	@Override
	public InternshipBean insert(InternshipBean internshipBean) {
		LOGGER.debug("Inside InternshipServiceImpl.insert()");
		Internship internship=null;
		Internship internshipReturn=null;
		InternshipBean bean=null;
		byte[] image=null;
		try {
			LOGGER.debug("Inside try block of InternshipServiceImpl.insert()");
			image=internshipBean.getFile().getBytes();
			internshipBean.setDescriptionFile(image);
			internshipBean.setStatus("Not Active");
			internshipBean.setDeleteStatus("Active");
			internship=new Internship();
			BeanUtils.copyProperties(internshipBean, internship);
			internshipReturn=internshipRepository.save(internship);
			bean=new InternshipBean();
			BeanUtils.copyProperties(internshipReturn, bean);
			LOGGER.info("Data successfully saved using InternshipServiceImpl.insert(-)");
			return bean;
		}
		catch (Exception e) {
			LOGGER.error("Data Insertion failed using InternshipServiceImpl.insert(-): "+e);
			throw new InternshipException(e.getMessage());
		}
	}

	@Override
	public List<InternshipBean> findInternshipsByCorporateId(Long corpUserId) {
		LOGGER.debug("Inside InternshipServiceImpl.findInternshipsByCorporateId(-)");
		List<Internship> internships=null;
		List<InternshipBean> internshipBeans=null;
		InternshipBean internshipBean=null;
		boolean flag=false;
		try {
			LOGGER.debug("Inside try block of InternshipServiceImpl.findInternshipsByCorporateId(-)");
			internships=internshipRepository.findInternshipsByCorporateId(corpUserId);
			if(internships!=null) {
				if(internships.size()>0) {
					internshipBeans=new ArrayList<InternshipBean>();
					for (Internship internship : internships) {
						internshipBean=new InternshipBean();
						BeanUtils.copyProperties(internship, internshipBean);
						internshipBeans.add(internshipBean);
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
				LOGGER.info("Data successfully fetched using InternshipServiceImpl.findInternshipsByCorporateId(-)");
				return internshipBeans;
			}
			else {
				LOGGER.info("No internships are there. Get Result using InternshipServiceImpl.findInternshipsByCorporateId(-)");
				return internshipBeans;
			}
		}
		catch (Exception e) {
			LOGGER.error("Error occured in InternshipServiceImpl.findInternshipsByCorporateId(-): "+e);
			throw new InternshipException(e.getMessage());
		}
	}

	@Override
	public InternshipBean findInternshipById(Long internshipId) {
		LOGGER.debug("Inside InternshipServiceImpl.findInternshipById(-)");
		Internship internship=null;
		Optional<Internship> optional=null;
		InternshipBean internshipBean=null;
		try {
			LOGGER.debug("Inside try block of InternshipServiceImpl.findInternshipById(-)");
			optional=internshipRepository.findById(internshipId);
			if(!optional.isEmpty()) {
				internship=optional.get();
				if(internship!=null) {
					internshipBean=new InternshipBean();
					BeanUtils.copyProperties(internship, internshipBean);
					LOGGER.info("Data Successfully fetched using InternshipServiceImpl.findInternshipById(-)");
				}
			}
			return internshipBean;
		}
		catch (Exception e) {
			LOGGER.error("Error occured in InternshipServiceImpl.findInternshipById(-): "+e);
			throw new InternshipException(e.getMessage());
		}
	}

	@Override
	public List<InternshipBean> deleteInternshipById(Long internshipId) {
		LOGGER.debug("Inside InternshipServiceImpl.deleteInternshipById(-)");
		Internship internship=null;
		Optional<Internship> optional=null;
		Long corpUserId=null;
		List<InternshipBean> internshipBeans=null;
		try {
			LOGGER.debug("Inside try block of InternshipServiceImpl.deleteInternshipById(-)");
			optional=internshipRepository.findById(internshipId);
			if(!optional.isEmpty()) {
				internship=optional.get();
				if(internship!=null) {
					corpUserId=internship.getCorpUserId();
					internship.setDeleteStatus("InActive");
					internship.setUpdatedOn(new Timestamp(new java.util.Date().getTime()));
					internshipRepository.save(internship);
					internshipBeans=findInternshipsByCorporateId(corpUserId);
				}
			}
			LOGGER.debug("Data Successfully Deleted using InternshipServiceImpl.findInternshipById(-)");
			return internshipBeans;
		}
		catch (Exception e) {
			LOGGER.error("Error occured in InternshipServiceImpl.deleteInternshipById(-): "+e);
			throw new InternshipException(e.getMessage());
		}
	}

	@Override
	public InternshipBean updateInternshipById(InternshipBean internshipBean) {
		LOGGER.debug("Inside InternshipServiceImpl.updateInternshipById(-)");
		Internship internship=null;
		Internship internshipReturn=null;
		Optional<Internship> optional=null;
		InternshipBean bean=null;
		byte[] image=null;
		try {
			LOGGER.debug("Inside try block of InternshipServiceImpl.updateInternshipById(-)");
			image=internshipBean.getFile().getBytes();
			internshipBean.setDescriptionFile(image);
			optional=internshipRepository.findById(internshipBean.getInternshipId());
			if(!optional.isEmpty()) {
				internship=optional.get();
				if(internship!=null) {
					internship.setInternshipTitle(internshipBean.getInternshipTitle());
					internship.setInternshipType(internshipBean.getInternshipType());
					internship.setSkills(internshipBean.getSkills());
					internship.setCity(internshipBean.getCity());
					internship.setOpenings(internshipBean.getOpenings());
					internship.setSalary(internshipBean.getSalary());
					internship.setAbout(internshipBean.getAbout());
					internship.setDescription(internshipBean.getDescription());
					internship.setDescriptionFile(internshipBean.getDescriptionFile());
					internship.setUpdatedOn(new Timestamp(new java.util.Date().getTime()));
					internshipReturn=internshipRepository.save(internship);
					bean=new InternshipBean();
					BeanUtils.copyProperties(internshipReturn, bean);
					LOGGER.info("Data Successfully updated using InternshipServiceImpl.updateInternshipById(-)");
				}
			}
			return bean;
		}
		catch (Exception e) {
			LOGGER.error("Error occured in InternshipServiceImpl.updateInternshipById(-): "+e);
			throw new InternshipException(e.getMessage());
		}
	}
}
