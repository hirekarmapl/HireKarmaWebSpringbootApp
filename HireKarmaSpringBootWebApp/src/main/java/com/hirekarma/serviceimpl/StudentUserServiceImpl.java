package com.hirekarma.serviceimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirekarma.beans.StudentUserBean;
import com.hirekarma.model.StudentUser;
import com.hirekarma.repository.StudentUserRepository;
import com.hirekarma.service.StudentUserService;

@Service("studentUserServiceImpl")
public class StudentUserServiceImpl implements StudentUserService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(StudentUserServiceImpl.class);
	
	@Autowired
	private StudentUserRepository studentUserRepository;

	@Override
	public StudentUser insert(StudentUser studentUser) {
		LOGGER.debug("Inside StudentUserServiceImpl.insert(-)");
		StudentUser user=null;
		try {
			LOGGER.debug("Inside try block of StudentUserServiceImpl.insert(-)");
			user=studentUserRepository.save(studentUser);
			LOGGER.info("Data successfully saved using StudentUserServiceImpl.insert(-)");
			return user;
		}
		catch (Exception e) {
			LOGGER.error("Data Insertion failed using StudentUserServiceImpl.insert(-): "+e);
			throw e;
		}
	}

	@Override
	public StudentUserBean checkLoginCredentials(String email, String password) {
		LOGGER.debug("Inside StudentUserServiceImpl.checkLoginCredentials(-,-)");
		StudentUserBean studentUserBean=null;
		StudentUser studentUser=null;
		try {
			LOGGER.debug("Inside try block of StudentUserServiceImpl.checkLoginCredentials(-,-)");
			studentUser=studentUserRepository.checkLoginCredentials(email, password);
			if(studentUser!=null) {
				LOGGER.info("user credential match using StudentUserServiceImpl.checkLoginCredentials(-,-)");
				studentUserBean=new StudentUserBean();
				BeanUtils.copyProperties(studentUser, studentUserBean);
				return studentUserBean;
			}
			else {
				LOGGER.info("user credential does not match using StudentUserServiceImpl.checkLoginCredentials(-,-)");
				return null;
			}
		}
		catch (Exception e) {
			LOGGER.info("Error occured in HireKarmaUserServiceImpl.checkLoginCredentials(-,-): "+e);
			throw e;
		}
	}
}
