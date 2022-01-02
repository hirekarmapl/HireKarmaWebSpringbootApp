package com.hirekarma.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirekarma.beans.StudentEducationDetailsBean;
import com.hirekarma.beans.StudentProfessionalDetailsBean;
import com.hirekarma.beans.StudentProfessionalExperienceBean;
import com.hirekarma.model.StudentEducationDetails;
import com.hirekarma.model.StudentProfessionalExperience;
import com.hirekarma.repository.StudentEduDetlsRepository;
import com.hirekarma.repository.StudentProfExpRepository;
import com.hirekarma.service.StudentProfDetailsService;

@Service("studentProfDetailsServiceImpl")
public class StudentProfDetailsServiceImpl implements StudentProfDetailsService {
	
	@Autowired
	private StudentProfExpRepository studentProfExpRepository;
	
	@Autowired
	private StudentEduDetlsRepository studentEduDetlsRepository;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(StudentProfDetailsServiceImpl.class);

	@Override
	public StudentProfessionalDetailsBean addStudentProfessionalDetails(StudentProfessionalDetailsBean professionalDetailsBean) {
		
		LOGGER.debug("Inside StudentProfDetailsServiceImpl.addStudentProfessionalDetails(-)");
		
		StudentProfessionalDetailsBean studentProfessionalDetailsBeanReturn = null;
		List<StudentEducationDetailsBean> educationDetailsBeans = null;
		List<StudentProfessionalExperienceBean> professionalExperienceBeans = null;
		StudentEducationDetails educationDetails=null;
		StudentEducationDetails educationDetailsReturns=null;
		StudentProfessionalExperience professionalExperience=null;
		StudentProfessionalExperience professionalExperienceReturns=null;
		List<StudentEducationDetailsBean> educationDetailsBeansReturn = null;
		List<StudentProfessionalExperienceBean> professionalExperienceBeansReturn = null;
		boolean saveFlagEdu=false;
		boolean saveFlagProfExp=false;
		
		try {
			LOGGER.debug("Inside try block StudentProfDetailsServiceImpl.addStudentProfessionalDetails(-)");
			if(professionalDetailsBean != null) {
				
				educationDetailsBeans=professionalDetailsBean.getEducationDetailsBeans();
				professionalExperienceBeans=professionalDetailsBean.getProfessionalExperienceBeans();
				
				if(educationDetailsBeans != null && educationDetailsBeans.size()>0) {
					Long userId=educationDetailsBeans.get(0).getUserId();
					educationDetailsBeansReturn=new ArrayList<StudentEducationDetailsBean>();
					studentEduDetlsRepository.deleteStudentEduDtlsByUserId(userId);
					for (StudentEducationDetailsBean educationDetailsBean : educationDetailsBeans) {
						educationDetails= new StudentEducationDetails();
						BeanUtils.copyProperties(educationDetailsBean, educationDetails);
						educationDetails.setDeleteStatus("Active");
						educationDetailsReturns=studentEduDetlsRepository.save(educationDetails);
						StudentEducationDetailsBean bean=new StudentEducationDetailsBean();
						BeanUtils.copyProperties(educationDetailsReturns, bean);
						educationDetailsBeansReturn.add(bean);
						saveFlagEdu=true;
						LOGGER.info("StudentEducationDetailsBean details saved successfully using StudentProfDetailsServiceImpl.addStudentProfessionalDetails(-)");
					}
				}
				
				if(professionalExperienceBeans != null && professionalExperienceBeans.size()>0) {
					Long userId=professionalExperienceBeans.get(0).getUserId();
					professionalExperienceBeansReturn=new ArrayList<StudentProfessionalExperienceBean>();
					studentProfExpRepository.deleteStudentProfExpByUserId(userId);
					for (StudentProfessionalExperienceBean professionalExperienceBean : professionalExperienceBeans) {
						professionalExperience= new StudentProfessionalExperience();
						BeanUtils.copyProperties(professionalExperienceBean, professionalExperience);
						professionalExperience.setDeleteStatus("Active");
						professionalExperienceReturns=studentProfExpRepository.save(professionalExperience);
						StudentProfessionalExperienceBean bean=new StudentProfessionalExperienceBean();
						BeanUtils.copyProperties(professionalExperienceReturns, bean);
						professionalExperienceBeansReturn.add(bean);
						saveFlagProfExp=true;
						LOGGER.info("StudentProfessionalExperienceBean details saved successfully using StudentProfDetailsServiceImpl.addStudentProfessionalDetails(-)");
					}
				}
				if(saveFlagEdu || saveFlagProfExp) {
					studentProfessionalDetailsBeanReturn=new StudentProfessionalDetailsBean();
					if(saveFlagEdu) {
						studentProfessionalDetailsBeanReturn.setEducationDetailsBeans(educationDetailsBeansReturn);
					}
					if(saveFlagProfExp) {
						studentProfessionalDetailsBeanReturn.setProfessionalExperienceBeans(professionalExperienceBeansReturn);
					}
				}
			}
			LOGGER.info("Data saved successfully using StudentProfDetailsServiceImpl.addStudentProfessionalDetails(-)");
			return studentProfessionalDetailsBeanReturn;
		}
		catch (Exception e) {
			LOGGER.error("Data saving failed using StudentProfDetailsServiceImpl.addStudentProfessionalDetails(-): "+e.getMessage());
			throw e;
		}
	}

	@Override
	public StudentProfessionalDetailsBean getStudentProffesionalDetailsByUserId(Long userId) {
		LOGGER.debug("Inside StudentProfDetailsServiceImpl.getStudentProffesionalDetailsByUserId(-)");
		
		StudentProfessionalDetailsBean studentProfessionalDetailsBean=null;
		StudentEducationDetailsBean studentEducationDetailsBean=null;
		StudentProfessionalExperienceBean studentProfessionalExperienceBean=null;
		List<StudentEducationDetailsBean> studentEducationDetailsBeans=null;
		List<StudentProfessionalExperienceBean> studentProfessionalExperienceBeans=null;
		List<StudentEducationDetails> studentEducationDetails=null;
		List<StudentProfessionalExperience> studentProfessionalExperiences=null;
		
		try {
			LOGGER.debug("Inside try block of StudentProfDetailsServiceImpl.getStudentProffesionalDetailsByUserId(-)");
			studentEducationDetails=studentEduDetlsRepository.getStudentEducationDetailsByUserId(userId);
			studentProfessionalExperiences=studentProfExpRepository.getStudentProfessionalExperienceByUserId(userId);
			if(studentEducationDetails!=null && studentEducationDetails.size()>0) {
				studentEducationDetailsBeans=new ArrayList<StudentEducationDetailsBean>();
				for (StudentEducationDetails studentEducationDetail : studentEducationDetails) {
					studentEducationDetailsBean =new StudentEducationDetailsBean();
					BeanUtils.copyProperties(studentEducationDetail, studentEducationDetailsBean);
					studentEducationDetailsBeans.add(studentEducationDetailsBean);
				}
			}
			if(studentProfessionalExperiences!=null && studentProfessionalExperiences.size()>0) {
				studentProfessionalExperienceBeans=new ArrayList<StudentProfessionalExperienceBean>();
				for (StudentProfessionalExperience studentProfessionalExperience : studentProfessionalExperiences) {
					studentProfessionalExperienceBean =new StudentProfessionalExperienceBean();
					BeanUtils.copyProperties(studentProfessionalExperience, studentProfessionalExperienceBean);
					studentProfessionalExperienceBeans.add(studentProfessionalExperienceBean);
				}
			}
			studentProfessionalDetailsBean = new StudentProfessionalDetailsBean();
			studentProfessionalDetailsBean.setEducationDetailsBeans(studentEducationDetailsBeans);
			studentProfessionalDetailsBean.setProfessionalExperienceBeans(studentProfessionalExperienceBeans);
			LOGGER.info("data retrived using StudentProfDetailsServiceImpl.getStudentProffesionalDetailsByUserId(-)");
			return studentProfessionalDetailsBean;
		}
		catch (Exception e) {
			LOGGER.debug("data failed to retrive using StudentProfDetailsServiceImpl.getStudentProffesionalDetailsByUserId(-): "+e);
			throw e;
		}
	}
}
