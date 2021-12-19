package com.hirekarma.serviceimpl;

import java.sql.Timestamp;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirekarma.beans.CoporateUserBean;
import com.hirekarma.exception.CoporateUserDefindException;
import com.hirekarma.model.CoporateUser;
import com.hirekarma.model.Organization;
import com.hirekarma.repository.CoporateUserRepository;
import com.hirekarma.repository.OrganizationRepository;
import com.hirekarma.service.CoporateUserService;
import com.hirekarma.utilty.HirekarmaPasswordVerifier;

@Service("coporateUserService")
public class CoporateUserServiceImpl implements CoporateUserService{

	private static final Logger LOGGER = LoggerFactory.getLogger(CoporateUserServiceImpl.class);
	
	@Autowired
	private CoporateUserRepository coporateUserRepository;
	
	@Autowired
	private OrganizationRepository organizationRepository;

	@Override
	public CoporateUser insert(CoporateUser coporateUser) throws CoporateUserDefindException{
		LOGGER.debug("Inside CoporateUserServiceImpl.insert(-)");
		CoporateUser user=null;
		HirekarmaPasswordVerifier verifier= null;
		String encryptedPassword=null;
		Organization organization=null;
		try {
			LOGGER.debug("Inside try block of CoporateUserServiceImpl.insert(-)");
			verifier= new HirekarmaPasswordVerifier();
			coporateUser.setUserType("Coporate User");
			coporateUser.setStatus("Active");
			encryptedPassword=verifier.getEncriptedString(coporateUser.getPassword());
			coporateUser.setPassword(encryptedPassword);
			user=coporateUserRepository.save(coporateUser);
			organization=new Organization();
			organization.setCorpUserId(user.getCorpUserId());
			organization.setStatus("Active");
			organizationRepository.save(organization);
			LOGGER.info("Data successfully saved using CoporateUserServiceImpl.insert(-)");
			return user;
		}
		catch (Exception e) {
			LOGGER.error("Data Insertion failed using CoporateUserServiceImpl.insert(-): "+e);
			throw new CoporateUserDefindException(e.getMessage());
		}
	}

	@Override
	public CoporateUserBean checkLoginCredentials(String email, String password) throws CoporateUserDefindException{
		LOGGER.debug("Inside CoporateUserServiceImpl.checkLoginCredentials(-,-)");
		CoporateUserBean coporateUserBean=null;
		CoporateUser coporateUser=null;
		HirekarmaPasswordVerifier verifier= null;
		String encryptedPassword=null;
		try {
			LOGGER.debug("Inside try block of CoporateUserServiceImpl.checkLoginCredentials(-,-)");
			verifier= new HirekarmaPasswordVerifier();
			encryptedPassword=verifier.getEncriptedString(password);
			coporateUser=coporateUserRepository.checkLoginCredentials(email, encryptedPassword);
			if(coporateUser!=null) {
				LOGGER.info("user credential match using CoporateUserServiceImpl.checkLoginCredentials(-,-)");
				coporateUserBean=new CoporateUserBean();
				BeanUtils.copyProperties(coporateUser, coporateUserBean);
				return coporateUserBean;
			}
			else {
				LOGGER.info("user credential does not match using CoporateUserServiceImpl.checkLoginCredentials(-,-)");
				return null;
			}
		}
		catch (Exception e) {
			LOGGER.error("Error occured in CoporateUserServiceImpl.checkLoginCredentials(-,-): "+e);
			throw new CoporateUserDefindException(e.getMessage());
		}
	}

	@Override
	public CoporateUserBean updateCoporateUserProfile(CoporateUserBean bean) throws CoporateUserDefindException{
		LOGGER.debug("Inside CoporateUserServiceImpl.updateCoporateUserProfile(-)");
		CoporateUser coporateUser=null;
		CoporateUser coporateUserReturn=null;
		Optional<CoporateUser> optional=null;
		CoporateUserBean coporateUserBean=null;
		try {
			LOGGER.debug("Inside try block of CoporateUserServiceImpl.updateCoporateUserProfile(-)");
			optional=coporateUserRepository.findById(bean.getCorpUserId());
			coporateUser=optional.get();
			if(coporateUser!=null) {
				coporateUser.setName(bean.getName());
				coporateUser.setEmail(bean.getEmail());
				coporateUser.setPhoneNO(bean.getPhoneNO());
				coporateUser.setProfileImage(bean.getProfileImage());
				coporateUser.setAddress(bean.getAddress());
				coporateUser.setUpdatedOn(new Timestamp(new java.util.Date().getTime()));
				coporateUserReturn=coporateUserRepository.save(coporateUser);
				coporateUserBean=new CoporateUserBean();
				BeanUtils.copyProperties(coporateUserReturn, coporateUserBean);
				LOGGER.info("Data Successfully updated using CoporateUserServiceImpl.updateCoporateUserProfile(-)");
			}
			return coporateUserBean;
		}
		catch (Exception e) {
			LOGGER.error("Error occured in CoporateUserServiceImpl.updateCoporateUserProfile(-): "+e);
			throw new CoporateUserDefindException(e.getMessage());
		}
	}

	@Override
	public CoporateUserBean findCorporateById(Long corpUserId) {
		LOGGER.debug("Inside CoporateUserServiceImpl.findCorporateById(-)");
		CoporateUser coporateUser=null;
		Optional<CoporateUser> optional=null;
		CoporateUserBean coporateUserBean=null;
		try {
			LOGGER.debug("Inside try block of CoporateUserServiceImpl.findCorporateById(-)");
			optional=coporateUserRepository.findById(corpUserId);
			if(!optional.isEmpty()) {
				coporateUser=optional.get();
				if(coporateUser!=null) {
					coporateUserBean=new CoporateUserBean();
					BeanUtils.copyProperties(coporateUser, coporateUserBean);
					LOGGER.info("Data Successfully fetched using CoporateUserServiceImpl.findCorporateById(-)");
				}
			}
			return coporateUserBean;
		}
		catch (Exception e) {
			LOGGER.error("Error occured in CoporateUserServiceImpl.findCorporateById(-): "+e);
			throw new CoporateUserDefindException(e.getMessage());
		}
	}

}
