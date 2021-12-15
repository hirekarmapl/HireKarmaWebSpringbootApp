package com.hirekarma.serviceimpl;

import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hirekarma.beans.UniversityUserBean;
import com.hirekarma.model.UniversityUser;
import com.hirekarma.repository.UniversityUserRepository;
import com.hirekarma.service.UniversityUserService;

@Service("UniversityUserService")
public class UniversityUserServiceImpl implements UniversityUserService {
	
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(UniversityUserServiceImpl.class);
	
	@Autowired 
	private UniversityUserRepository universityUserRepository;
	

	@Override
	public UniversityUser insert(UniversityUser universityUser) {
		LOGGER.debug("Inside UniversityUserServiceImpl.insert(-)");
		UniversityUser sityUser=null;
		
		try {
			LOGGER.debug("Inside try block of UniversityUserServiceImpl.insert(-)");
			sityUser=universityUserRepository.save(universityUser);
			LOGGER.info("Data successfully saved using UniversityUserServiceImpl.insert(-)");
			return sityUser;	
					
		}catch (Exception e ) {
			LOGGER.error("Data Insertion failed using UniversityUserServiceImpl.insert(-): "+e);
			throw e;
				
		}		
	}

	@Override
	public UniversityUserBean checkLoginCredentials(String email, String password) {
		
		LOGGER.debug("Inside UniversityUserServiceImpl.insert(-)");
		UniversityUserBean sityUserBean=null;
		UniversityUser sityUser=null;
		
	   try {
		   LOGGER.debug("Inside try block of UniversityUserServiceImpl.checkLoginCredentials(-,-)");
			sityUser= universityUserRepository.checkLoginCredentials(email, password);
			if(sityUser!=null) {
				LOGGER.info("user credential match using UniversityUserServiceImpl.checkLoginCredentials(-,-)");
				sityUserBean=new UniversityUserBean();
				BeanUtils.copyProperties(sityUser, sityUserBean);
				return sityUserBean;
			}
			else {
				LOGGER.info("user credential does not match using UniversityUserServiceImpl.checkLoginCredentials(-,-)");
				return null;
			} 
	   }
		catch(Exception e ) {
			
			LOGGER.info("Error occured in UniversityUserServiceImpl.checkLoginCredentials(-,-): "+e);
			throw e;
		}
	
	}

}