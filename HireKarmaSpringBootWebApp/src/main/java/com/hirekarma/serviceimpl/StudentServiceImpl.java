package com.hirekarma.serviceimpl;

import java.sql.Timestamp;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirekarma.beans.StudentBean;
import com.hirekarma.exception.StudentUserDefindException;
import com.hirekarma.model.Student;
import com.hirekarma.repository.StudentRepository;
import com.hirekarma.service.StudentService;
import com.hirekarma.utilty.HirekarmaPasswordVerifier;

@Service("studentServiceImpl")
public class StudentServiceImpl implements StudentService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(StudentServiceImpl.class);
	
	@Autowired
	private StudentRepository studentRepository;

	@Override
	public Student insert(Student student) {
		LOGGER.debug("Inside StudentServiceImpl.insert(-)");
		Student studentReturn=null;
		HirekarmaPasswordVerifier verifier= null;
		String encryptedPassword=null;
		try {
			LOGGER.debug("Inside try block of StudentServiceImpl.insert(-)");
			verifier= new HirekarmaPasswordVerifier();
			student.setUserType("Student User");
			student.setStatus("Active");
			encryptedPassword=verifier.getEncriptedString(student.getPassword());
			student.setPassword(encryptedPassword);
			studentReturn=studentRepository.save(student);
			LOGGER.info("Data successfully saved using StudentServiceImpl.insert(-)");
			return studentReturn;
		}
		catch (Exception e) {
			LOGGER.error("Data Insertion failed using StudentServiceImpl.insert(-): "+e);
			throw new StudentUserDefindException(e.getMessage());
		}
	}

	@Override
	public StudentBean checkLoginCredentials(String email, String password) {
		LOGGER.debug("Inside StudentServiceImpl.checkLoginCredentials(-,-)");
		StudentBean studentBean=null;
		Student student=null;
		HirekarmaPasswordVerifier verifier= null;
		String encryptedPassword=null;
		try {
			LOGGER.debug("Inside try block of StudentServiceImpl.checkLoginCredentials(-,-)");
			verifier= new HirekarmaPasswordVerifier();
			encryptedPassword=verifier.getEncriptedString(password);
			student=studentRepository.checkLoginCredentials(email, encryptedPassword);
			if(student!=null) {
				LOGGER.info("student credential match using StudentServiceImpl.checkLoginCredentials(-,-)");
				studentBean=new StudentBean();
				BeanUtils.copyProperties(student, studentBean);
				return studentBean;
			}
			else {
				LOGGER.info("student credential does not match using StudentServiceImpl.checkLoginCredentials(-,-)");
				return null;
			}
		}
		catch (Exception e) {
			LOGGER.info("Error occured in StudentServiceImpl.checkLoginCredentials(-,-): "+e);
			throw new StudentUserDefindException(e.getMessage());
		}
	}

	@Override
	public StudentBean updateStudentProfile(StudentBean bean) {
		LOGGER.debug("Inside StudentServiceImpl.updateStudentProfile(-)");
		Student student=null;
		Student studentReturn=null;
		Optional<Student> optional=null;
		StudentBean studentBean=null;
		try {
			LOGGER.debug("Inside try block of StudentServiceImpl.updateStudentProfile(-)");
			optional=studentRepository.findById(bean.getStudentId());
			student=optional.get();
			if(!optional.isEmpty()) {
				if(student!=null) {
					student.setName(bean.getName());
					student.setEmail(bean.getEmail());
					student.setPhoneNO(bean.getPhoneNO());
					student.setProfileImage(bean.getProfileImage());
					student.setAddress(bean.getAddress());
					student.setUpdatedOn(new Timestamp(new java.util.Date().getTime()));
					studentReturn=studentRepository.save(student);
					studentBean=new StudentBean();
					BeanUtils.copyProperties(studentReturn, studentBean);
					LOGGER.info("Data Successfully updated using StudentServiceImpl.updateStudentProfile(-)");
				}
			}
			return studentBean;
		}
		catch (Exception e) {
			LOGGER.error("Error occured in StudentServiceImpl.updateStudentProfile(-): "+e);
			throw new StudentUserDefindException(e.getMessage());
		}
	}

	@Override
	public StudentBean findStudentById(Long studentId) {
		LOGGER.debug("Inside StudentServiceImpl.findStudentById(-)");
		Student student=null;
		Optional<Student> optional=null;
		StudentBean studentBean=null;
		try {
			LOGGER.debug("Inside try block of StudentServiceImpl.findStudentById(-)");
			optional=studentRepository.findById(studentId);
			if(!optional.isEmpty()) {
				student=optional.get();
				if(student!=null) {
					studentBean=new StudentBean();
					BeanUtils.copyProperties(student, studentBean);
					LOGGER.info("Data Successfully fetched using StudentServiceImpl.findStudentById(-)");
				}
			}
			return studentBean;
		}
		catch (Exception e) {
			LOGGER.error("Error occured in StudentServiceImpl.findStudentById(-): "+e);
			throw new StudentUserDefindException(e.getMessage());
		}
	}
}
