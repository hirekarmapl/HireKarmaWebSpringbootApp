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
import com.hirekarma.exception.UniversityUserDefindException;
import com.hirekarma.model.UserProfile;
import com.hirekarma.repository.UserRepository;
import com.hirekarma.service.UniversityUserService;

@Service("universityUserService")
public class UniversityUserServiceImpl implements UniversityUserService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UniversityUserServiceImpl.class);
	
//	@Autowired 
//	private UniversityUserRepository universityUserRepository;
	
	@Autowired 
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	

//	@Override
//	public UniversityUser insert(UniversityUser universityUser) {
//		LOGGER.debug("Inside UniversityUserServiceImpl.insert(-)");
//		UniversityUser sityUser=null;
//		HirekarmaPasswordVerifier verifier= null;
//		String encryptedPassword=null;
//		try {
//			LOGGER.debug("Inside try block of UniversityUserServiceImpl.insert(-)");
//			verifier= new HirekarmaPasswordVerifier();
//			universityUser.setUserType("University User");
//			universityUser.setStatus("Active");
//			encryptedPassword=verifier.getEncriptedString(universityUser.getPassword());
//			universityUser.setPassword(encryptedPassword);
//			sityUser=universityUserRepository.save(universityUser);
//			LOGGER.info("Data successfully saved using UniversityUserServiceImpl.insert(-)");
//			return sityUser;	
//					
//		}catch (Exception e ) {
//			LOGGER.error("Data Insertion failed using UniversityUserServiceImpl.insert(-): "+e);
//			throw e;	
//		}		
//	}
	
	@Override
	public UserProfile insert(UserProfile universityUser) {
		LOGGER.debug("Inside UniversityUserServiceImpl.insert(-)");
		UserProfile sityUser=null;
		try {
			LOGGER.debug("Inside try block of UniversityUserServiceImpl.insert(-)");
			universityUser.setUserType("university");
			universityUser.setStatus("Active");
			universityUser.setPassword(passwordEncoder.encode(universityUser.getPassword()));
			sityUser=userRepository.save(universityUser);
			LOGGER.info("Data successfully saved using UniversityUserServiceImpl.insert(-)");
			return sityUser;	
					
		}catch (Exception e ) {
			LOGGER.error("Data Insertion failed using UniversityUserServiceImpl.insert(-): "+e);
			throw e;	
		}		
	}

//	@Override
//	public UniversityUserBean checkLoginCredentials(String email, String password) {
//		
//		LOGGER.debug("Inside UniversityUserServiceImpl.insert(-)");
//		UniversityUserBean sityUserBean=null;
//		UniversityUser sityUser=null;
//		HirekarmaPasswordVerifier verifier= null;
//		String encryptedPassword=null;
//		try {
//			LOGGER.debug("Inside try block of UniversityUserServiceImpl.checkLoginCredentials(-,-)");
//			verifier= new HirekarmaPasswordVerifier();
//			encryptedPassword=verifier.getEncriptedString(password);
//			sityUser= universityUserRepository.checkLoginCredentials(email, encryptedPassword);
//			if(sityUser!=null) {
//				LOGGER.info("user credential match using UniversityUserServiceImpl.checkLoginCredentials(-,-)");
//				sityUserBean=new UniversityUserBean();
//				BeanUtils.copyProperties(sityUser, sityUserBean);
//				return sityUserBean;
//			}
//			else {
//				LOGGER.info("user credential does not match using UniversityUserServiceImpl.checkLoginCredentials(-,-)");
//				return null;
//			} 
//		}
//		catch(Exception e ) {
//			LOGGER.info("Error occured in UniversityUserServiceImpl.checkLoginCredentials(-,-): "+e);
//			throw e;
//		}
//	
//	}

//	@Override
//	public UniversityUserBean updateUniversityUserProfile(UniversityUserBean universityUserBean) {
//		LOGGER.debug("Inside UniversityUserServiceImpl.updateUniversityUserProfile(-)");
//		UniversityUser universityUser=null;
//		UniversityUser universityUserReturn=null;
//		Optional<UniversityUser> optional=null;
//		UniversityUserBean universityUserBeanReturn=null;
//		try {
//			LOGGER.debug("Inside try block of UniversityUserServiceImpl.updateUniversityUserProfile(-)");
//			optional=universityUserRepository.findById(universityUserBean.getUniversityId());
//			if(!optional.isEmpty()) {
//				universityUser=optional.get();
//				if(universityUser!=null) {
//					universityUser.setUniversityName(universityUserBean.getUniversityName());
//					universityUser.setEmailAddress(universityUserBean.getEmailAddress());
//					universityUser.setPhoneNo(universityUserBean.getPhoneNo());
//					universityUser.setUniversityEmailAddress(universityUserBean.getUniversityEmailAddress());
//					universityUser.setUniversityImage(universityUserBean.getUniversityImage());
//					universityUser.setUpdatedOn(new Timestamp(new java.util.Date().getTime()));
//					universityUser.setUniversityAddress(universityUserBean.getUniversityAddress());
//					universityUserReturn=universityUserRepository.save(universityUser);
//					universityUserBeanReturn=new UniversityUserBean();
//					BeanUtils.copyProperties(universityUserReturn, universityUserBeanReturn);
//					LOGGER.info("Data Successfully updated using UniversityUserServiceImpl.updateUniversityUserProfile(-)");
//				}
//			}
//			return universityUserBeanReturn;
//		}
//		catch (Exception e) {
//			LOGGER.error("Error occured in UniversityUserServiceImpl.updateUniversityUserProfile(-): "+e);
//			throw new UniversityUserDefindException(e.getMessage());
//		}
//	}
	
	@Override
	public UserBean updateUniversityUserProfile(UserBean universityUserBean) {
		LOGGER.debug("Inside UniversityUserServiceImpl.updateUniversityUserProfile(-)");
		UserProfile universityUser=null;
		UserProfile universityUserReturn=null;
		Optional<UserProfile> optional=null;
		UserBean universityUserBeanReturn=null;
		try {
			LOGGER.debug("Inside try block of UniversityUserServiceImpl.updateUniversityUserProfile(-)");
			optional=userRepository.findById(universityUserBean.getUserId());
			if(!optional.isEmpty()) {
				universityUser=optional.get();
				if(universityUser!=null) {
					universityUser.setName(universityUserBean.getName());
					universityUser.setEmail(universityUserBean.getEmail());
					universityUser.setPhoneNo(universityUserBean.getPhoneNo());
					universityUser.setUniversityEmailAddress(universityUserBean.getUniversityEmailAddress());
					universityUser.setImage(universityUserBean.getImage());
					universityUser.setUpdatedOn(new Timestamp(new java.util.Date().getTime()));
					universityUser.setAddress(universityUserBean.getAddress());
					universityUserReturn=userRepository.save(universityUser);
					universityUserBeanReturn=new UserBean();
					BeanUtils.copyProperties(universityUserReturn, universityUserBeanReturn);
					LOGGER.info("Data Successfully updated using UniversityUserServiceImpl.updateUniversityUserProfile(-)");
				}
			}
			return universityUserBeanReturn;
		}
		catch (Exception e) {
			LOGGER.error("Error occured in UniversityUserServiceImpl.updateUniversityUserProfile(-): "+e);
			throw new UniversityUserDefindException(e.getMessage());
		}
	}

//	@Override
//	public UniversityUserBean findUniversityById(Long universityId) {
//		LOGGER.debug("Inside UniversityUserServiceImpl.findUniversityById(-)");
//		UniversityUser universityUser=null;
//		Optional<UniversityUser> optional=null;
//		UniversityUserBean universityUserBean=null;
//		try {
//			LOGGER.debug("Inside try block of UniversityUserServiceImpl.findUniversityById(-)");
//			optional=universityUserRepository.findById(universityId);
//			if(!optional.isEmpty()) {
//				universityUser=optional.get();
//				if(universityUser!=null) {
//					universityUserBean=new UniversityUserBean();
//					BeanUtils.copyProperties(universityUser, universityUserBean);
//					LOGGER.info("Data Successfully fetched using UniversityUserServiceImpl.findUniversityById(-)");
//				}
//			}
//			return universityUserBean;
//		}
//		catch (Exception e) {
//			LOGGER.error("Error occured in UniversityUserServiceImpl.findUniversityById(-): "+e);
//			throw new UniversityUserDefindException(e.getMessage());
//		}
//	}
	
	@Override
	public UserBean findUniversityById(Long universityId) {
		LOGGER.debug("Inside UniversityUserServiceImpl.findUniversityById(-)");
		UserProfile universityUser=null;
		Optional<UserProfile> optional=null;
		UserBean universityUserBean=null;
		try {
			LOGGER.debug("Inside try block of UniversityUserServiceImpl.findUniversityById(-)");
			optional=userRepository.findById(universityId);
			if(!optional.isEmpty()) {
				universityUser=optional.get();
				if(universityUser!=null) {
					universityUserBean=new UserBean();
					BeanUtils.copyProperties(universityUser, universityUserBean);
					LOGGER.info("Data Successfully fetched using UniversityUserServiceImpl.findUniversityById(-)");
				}
			}
			return universityUserBean;
		}
		catch (Exception e) {
			LOGGER.error("Error occured in UniversityUserServiceImpl.findUniversityById(-): "+e);
			throw new UniversityUserDefindException(e.getMessage());
		}
	}

}