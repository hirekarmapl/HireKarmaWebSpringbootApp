package com.hirekarma.serviceimpl;

import java.sql.Timestamp;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirekarma.beans.OrganizationBean;
import com.hirekarma.exception.OrganizationUserDefindException;
import com.hirekarma.model.Organization;
import com.hirekarma.repository.OrganizationRepository;
import com.hirekarma.service.OrganizationService;

@Service("organizationService")
public class OrganizationServiceImpl implements OrganizationService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OrganizationServiceImpl.class);
	
	@Autowired
	private OrganizationRepository organizationRepository;

	@Override
	@Transactional
	public OrganizationBean updateOrganizationDetails(OrganizationBean bean) {
		LOGGER.debug("Inside OrganizationServiceImpl.updateOrganizationDetails(-)");
		Organization organization=null;
		Organization organizationReturn=null;
		OrganizationBean organizationBean=null;
		try {
			LOGGER.debug("Inside try block of OrganizationServiceImpl.updateOrganizationDetails(-)");
			organization=organizationRepository.findOrganizationByUserId(bean.getUserId());
			if(organization!=null) {
				organization.setOrgName(bean.getOrgName());
				organization.setOrgEmail(bean.getOrgEmail());
				organization.setCinGstNum(bean.getCinGstNum());
				organization.setLogo(bean.getLogo());
				organization.setDescription(bean.getDescription());
				organization.setUpdatedOn(new Timestamp(new java.util.Date().getTime()));
				organizationReturn=organizationRepository.save(organization);
				organizationBean=new OrganizationBean();
				BeanUtils.copyProperties(organizationReturn, organizationBean);
				LOGGER.info("Data Successfully updated using OrganizationServiceImpl.updateOrganizationDetails(-)");
			}
			return organizationBean;
		}
		catch (Exception e) {
			LOGGER.error("Error occured in OrganizationServiceImpl.updateOrganizationDetails(-): "+e);
			throw new OrganizationUserDefindException(e.getMessage());
		}
	}

	@Override
	public OrganizationBean findOrganizationByUserId(Long userId) {
		LOGGER.debug("Inside OrganizationServiceImpl.findOrganizationByCorporateId(-)");
		Organization organization=null;
		OrganizationBean organizationBean=null;
		try {
			LOGGER.debug("Inside try block of OrganizationServiceImpl.findOrganizationByCorporateId(-)");
			organization=organizationRepository.findOrganizationByUserId(userId);
			if(organization!=null) {
				organizationBean=new OrganizationBean();
				BeanUtils.copyProperties(organization, organizationBean);
				LOGGER.info("Data Successfully fetched using OrganizationServiceImpl.findOrganizationByCorporateId(-)");
			}
			return organizationBean;
		}
		catch (Exception e) {
			LOGGER.error("Error occured in OrganizationServiceImpl.findOrganizationByCorporateId(-): "+e);
			throw new OrganizationUserDefindException(e.getMessage());
		}
	}

}
