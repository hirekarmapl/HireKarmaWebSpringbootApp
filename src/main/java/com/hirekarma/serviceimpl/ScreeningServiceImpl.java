package com.hirekarma.serviceimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirekarma.beans.ScreeningBean;
import com.hirekarma.exception.StudentUserDefindException;
import com.hirekarma.model.ScreeningModel;
import com.hirekarma.model.ScreeningQuestions;
import com.hirekarma.repository.ScreeningQuestionsRepository;
import com.hirekarma.repository.ScreeningRepository;
import com.hirekarma.service.ScreeningService;

@Service("screeningServiceImpl")
public class ScreeningServiceImpl implements ScreeningService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ScreeningServiceImpl.class);
	
	@Autowired
	private ScreeningRepository screeningRepository;
	
	@Autowired
	private ScreeningQuestionsRepository screeningQuestionsRepository;

	@Override
	public ScreeningBean insertScreeningQuestions(ScreeningBean screeningBean) {
		LOGGER.debug("ScreeningServiceImpl.insertScreeningQuestions(-)");
		ScreeningBean screeningBeanReturn = null;
		ScreeningModel screeningModel = null;
		ScreeningModel screeningModelReturn = null;
		ScreeningQuestions screeningQuestions = null;
		String[] questionArray = null;
		try {
			LOGGER.debug("Inside try block of ScreeningServiceImpl.insertScreeningQuestions(-)");
			if(screeningBean!=null) {
				screeningModel = new ScreeningModel();
				screeningModel.setCorporateId(screeningBean.getCorporateId());
				screeningModel.setStatus("Active");
				screeningModelReturn = screeningRepository.save(screeningModel);
				screeningBeanReturn = new ScreeningBean();
				screeningBeanReturn.setCorporateId(screeningModelReturn.getCorporateId());
				screeningBeanReturn.setCreatedOn(screeningModelReturn.getCreatedOn());
				screeningBeanReturn.setUpdatedOn(screeningModelReturn.getUpdatedOn());
				screeningBeanReturn.setScreeningId(screeningModelReturn.getScreeningId());
				screeningBeanReturn.setStatus(screeningModelReturn.getStatus());
				screeningBeanReturn.setUpdatedOn(screeningModelReturn.getUpdatedOn());
				if(screeningBean.getQuestions() != null) {
					questionArray = new String[screeningBean.getQuestions().length];
					for(int i = 0; i<screeningBean.getQuestions().length; i++) {
						screeningQuestions = new ScreeningQuestions();
						screeningQuestions.setScreeningId(screeningModelReturn.getScreeningId());
						screeningQuestions.setQuestions(screeningBean.getQuestions()[i]);
						screeningQuestions.setStatus("Active");
						questionArray[i] = screeningQuestionsRepository.save(screeningQuestions).getQuestions();
					}
					screeningBeanReturn.setQuestions(questionArray);
				}
				LOGGER.info("data saved successfully using ScreeningServiceImpl.insertScreeningQuestions(-)");
			}
			return screeningBeanReturn;
		}
		catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("data saving failed using ScreeningServiceImpl.insertScreeningQuestions(-)");
			throw new StudentUserDefindException(e.getMessage());
		}
	}

	@Override
	public ScreeningBean getScreeningQuestionsByCorporateId(Long corporateId) {
		LOGGER.debug("ScreeningServiceImpl.getScreeningQuestionsByCorporateId()");
		ScreeningBean screeningBean = null;
		ScreeningModel screeningModel = null;
		String[] questionsArray = null;
		try {
			LOGGER.debug("Inside try block of ScreeningServiceImpl.getScreeningQuestionsByCorporateId()");
			screeningModel = screeningRepository.getScreeningQuestionsByCorporateId(corporateId);
			if(screeningModel!=null) {
				screeningBean = new ScreeningBean();
				BeanUtils.copyProperties(screeningModel, screeningBean);
				if(screeningModel.getQuestionsList() != null) {
					questionsArray = new String[screeningModel.getQuestionsList().size()];
					for(int i = 0; i < screeningModel.getQuestionsList().size(); i++) {
						questionsArray[i] = (screeningModel.getQuestionsList().get(i).getQuestions());
					}
					screeningBean.setQuestions(questionsArray);
				}
			}
			LOGGER.info("getting of questions done using ScreeningServiceImpl.getScreeningQuestionsByCorporateId()");
			return screeningBean;
		}
		catch (Exception e) {
			LOGGER.error("getting of questions failed using ScreeningServiceImpl.getScreeningQuestionsByCorporateId()");
			e.printStackTrace();
			throw new StudentUserDefindException(e.getMessage());
		}
	}

}
