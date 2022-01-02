package com.hirekarma.serviceimpl;

import java.sql.Timestamp;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hirekarma.beans.UserBean;
import com.hirekarma.exception.CoporateUserDefindException;
import com.hirekarma.model.Organization;
import com.hirekarma.model.UserProfile;
import com.hirekarma.repository.OrganizationRepository;
import com.hirekarma.repository.UserRepository;
import com.hirekarma.service.CoporateUserService;

@Service("coporateUserService")
public class CoporateUserServiceImpl implements CoporateUserService{

	private static final Logger LOGGER = LoggerFactory.getLogger(CoporateUserServiceImpl.class);
	
//	@Autowired
//	private CoporateUserRepository coporateUserRepository;
	
	@Autowired
	private OrganizationRepository organizationRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

//	@Override
//	public CoporateUser insert(CoporateUser coporateUser) {
//		LOGGER.debug("Inside CoporateUserServiceImpl.insert(-)");
//		CoporateUser user=null;
//		HirekarmaPasswordVerifier verifier= null;
//		String encryptedPassword=null;
//		Organization organization=null;
//		try {
//			LOGGER.debug("Inside try block of CoporateUserServiceImpl.insert(-)");
//			verifier= new HirekarmaPasswordVerifier();
//			coporateUser.setUserType("Coporate User");
//			coporateUser.setStatus("Active");
//			encryptedPassword=verifier.getEncriptedString(coporateUser.getPassword());
//			coporateUser.setPassword(encryptedPassword);
//			user=coporateUserRepository.save(coporateUser);
//			organization=new Organization();
//			organization.setCorpUserId(user.getCorpUserId());
//			organization.setStatus("Active");
//			organizationRepository.save(organization);
//			LOGGER.info("Data successfully saved using CoporateUserServiceImpl.insert(-)");
//			return user;
//		}
//		catch (Exception e) {
//			LOGGER.error("Data Insertion failed using CoporateUserServiceImpl.insert(-): "+e);
//			throw new CoporateUserDefindException(e.getMessage());
//		}
//	}
	
	@Override
	public UserProfile insert(UserProfile userProfile) {
		LOGGER.debug("Inside CoporateUserServiceImpl.insert(-)");
		UserProfile user=null;
		Organization organization=null;
		try {
			LOGGER.debug("Inside try block of CoporateUserServiceImpl.insert(-)");
			userProfile.setUserType("corporate");
			userProfile.setStatus("Active");
			userProfile.setPassword(passwordEncoder.encode(userProfile.getPassword()));
			user=userRepository.save(userProfile);
			organization=new Organization();
			organization.setUserId(user.getUserId());
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

//	@Override
//	public CoporateUserBean checkLoginCredentials(String email, String password) {
//		LOGGER.debug("Inside CoporateUserServiceImpl.checkLoginCredentials(-,-)");
//		CoporateUserBean coporateUserBean=null;
//		CoporateUser coporateUser=null;
//		HirekarmaPasswordVerifier verifier= null;
//		String encryptedPassword=null;
//		try {
//			LOGGER.debug("Inside try block of CoporateUserServiceImpl.checkLoginCredentials(-,-)");
//			verifier= new HirekarmaPasswordVerifier();
//			encryptedPassword=verifier.getEncriptedString(password);
//			coporateUser=coporateUserRepository.checkLoginCredentials(email, encryptedPassword);
//			if(coporateUser!=null) {
//				LOGGER.info("user credential match using CoporateUserServiceImpl.checkLoginCredentials(-,-)");
//				coporateUserBean=new CoporateUserBean();
//				BeanUtils.copyProperties(coporateUser, coporateUserBean);
//				return coporateUserBean;
//			}
//			else {
//				LOGGER.info("user credential does not match using CoporateUserServiceImpl.checkLoginCredentials(-,-)");
//				return null;
//			}
//		}
//		catch (Exception e) {
//			LOGGER.error("Error occured in CoporateUserServiceImpl.checkLoginCredentials(-,-): "+e);
//			throw new CoporateUserDefindException(e.getMessage());
//		}
//	}

//	@Override
//	public CoporateUserBean updateCoporateUserProfile(CoporateUserBean bean) {
//		LOGGER.debug("Inside CoporateUserServiceImpl.updateCoporateUserProfile(-)");
//		CoporateUser coporateUser=null;
//		CoporateUser coporateUserReturn=null;
//		Optional<CoporateUser> optional=null;
//		CoporateUserBean coporateUserBean=null;
//		try {
//			LOGGER.debug("Inside try block of CoporateUserServiceImpl.updateCoporateUserProfile(-)");
//			optional=coporateUserRepository.findById(bean.getCorpUserId());
//			coporateUser=optional.get();
//			if(!optional.isEmpty()) {
//				if(coporateUser!=null) {
//					coporateUser.setName(bean.getName());
//					coporateUser.setEmail(bean.getEmail());
//					coporateUser.setPhoneNO(bean.getPhoneNO());
//					coporateUser.setProfileImage(bean.getProfileImage());
//					coporateUser.setAddress(bean.getAddress());
//					coporateUser.setUpdatedOn(new Timestamp(new java.util.Date().getTime()));
//					coporateUserReturn=coporateUserRepository.save(coporateUser);
//					coporateUserBean=new CoporateUserBean();
//					BeanUtils.copyProperties(coporateUserReturn, coporateUserBean);
//					LOGGER.info("Data Successfully updated using CoporateUserServiceImpl.updateCoporateUserProfile(-)");
//				}
//			}
//			return coporateUserBean;
//		}
//		catch (Exception e) {
//			LOGGER.error("Error occured in CoporateUserServiceImpl.updateCoporateUserProfile(-): "+e);
//			throw new CoporateUserDefindException(e.getMessage());
//		}
//	}
	
	@Override
	public UserBean updateCoporateUserProfile(UserBean bean) {
		LOGGER.debug("Inside CoporateUserServiceImpl.updateCoporateUserProfile(-)");
		UserProfile user=null;
		UserProfile userReturn=null;
		Optional<UserProfile> optional=null;
		UserBean userBean=null;
		try {
			LOGGER.debug("Inside try block of CoporateUserServiceImpl.updateCoporateUserProfile(-)");
			optional=userRepository.findById(bean.getUserId());
			if(!optional.isEmpty()) {
				user=optional.get();
				if(user!=null) {
					user.setName(bean.getName());
					user.setEmail(bean.getEmail());
					user.setPhoneNo(bean.getPhoneNo());
					user.setImage(bean.getImage());
					user.setAddress(bean.getAddress());
					user.setUpdatedOn(new Timestamp(new java.util.Date().getTime()));
					userReturn=userRepository.save(user);
					userBean=new UserBean();
					BeanUtils.copyProperties(userReturn, userBean);
					LOGGER.info("Data Successfully updated using CoporateUserServiceImpl.updateCoporateUserProfile(-)");
				}
			}
			return userBean;
		}
		catch (Exception e) {
			LOGGER.error("Error occured in CoporateUserServiceImpl.updateCoporateUserProfile(-): "+e);
			throw new CoporateUserDefindException(e.getMessage());
		}
	}

//	@Override
//	public CoporateUserBean findCorporateById(Long corpUserId) {
//		LOGGER.debug("Inside CoporateUserServiceImpl.findCorporateById(-)");
//		CoporateUser coporateUser=null;
//		Optional<CoporateUser> optional=null;
//		CoporateUserBean coporateUserBean=null;
//		try {
//			LOGGER.debug("Inside try block of CoporateUserServiceImpl.findCorporateById(-)");
//			optional=coporateUserRepository.findById(corpUserId);
//			if(!optional.isEmpty()) {
//				coporateUser=optional.get();
//				if(coporateUser!=null) {
//					coporateUserBean=new CoporateUserBean();
//					BeanUtils.copyProperties(coporateUser, coporateUserBean);
//					LOGGER.info("Data Successfully fetched using CoporateUserServiceImpl.findCorporateById(-)");
//				}
//			}
//			return coporateUserBean;
//		}
//		catch (Exception e) {
//			LOGGER.error("Error occured in CoporateUserServiceImpl.findCorporateById(-): "+e);
//			throw new CoporateUserDefindException(e.getMessage());
//		}
//	}
	
	@Override
	public UserBean findCorporateById(Long userId) {
		LOGGER.debug("Inside CoporateUserServiceImpl.findCorporateById(-)");
		UserProfile user=null;
		Optional<UserProfile> optional=null;
		UserBean userBean=null;
		try {
			LOGGER.debug("Inside try block of CoporateUserServiceImpl.findCorporateById(-)");
			optional=userRepository.findById(userId);
			if(!optional.isEmpty()) {
				user=optional.get();
				if(user!=null) {
					userBean=new UserBean();
					BeanUtils.copyProperties(user, userBean);
					LOGGER.info("Data Successfully fetched using CoporateUserServiceImpl.findCorporateById(-)");
				}
			}
			return userBean;
		}
		catch (Exception e) {
			LOGGER.error("Error occured in CoporateUserServiceImpl.findCorporateById(-): "+e);
			throw new CoporateUserDefindException(e.getMessage());
		}
	}

}