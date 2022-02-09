package com.hirekarma.serviceimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirekarma.beans.ScreeningBean;
import com.hirekarma.model.ScreeninQuestionOptions;
import com.hirekarma.model.ScreeningEntity;
import com.hirekarma.repository.ScreeninQuestionOptionsRepository;
import com.hirekarma.repository.ScreeningEntityRepository;
import com.hirekarma.service.ScreeningService;

@Service("screeningServiceImpl")
public class ScreeningServiceImpl implements ScreeningService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ScreeningServiceImpl.class);
	
	@Autowired
	private ScreeningEntityRepository screeningEntityRepository;
	
	@Autowired
	private ScreeninQuestionOptionsRepository screeninQuestionOptionsRepository;

	@Override
	public Map<String, Object> createScreeningQuestion(ScreeningBean screeningBean) {
		LOGGER.debug("starting of ScreeningServiceImpl.createScreeningQuestion()");
		ScreeningEntity screeningEntity = null, screeningEntityReturn = null;
		ScreeninQuestionOptions screeninQuestionOptions = null;
		String slug =null;
		List<String> options = null;
		Map<String, Object> map = null;
		try {
			screeningEntity = new ScreeningEntity();
			screeningEntity.setCorporateId(screeningBean.getCorporateId());
			screeningEntity.setQuestions(screeningBean.getQuestions());
			screeningEntity.setQuestionType(screeningBean.getQuestionType());
			slug = screeningBean.getQuestions().substring(0, 6)+generateRandomString();
			screeningEntity.setSlug(slug);
			screeningEntityReturn = screeningEntityRepository.save(screeningEntity);
			options = screeningBean.getOptions();
			for(String option:options) {
				screeninQuestionOptions = new ScreeninQuestionOptions();
				screeninQuestionOptions.setScreeningTableId(screeningEntityReturn.getScreeningTableId());
				screeninQuestionOptions.setOptions(option);
				screeninQuestionOptionsRepository.save(screeninQuestionOptions);
			}
			map = new HashMap<String, Object>();
			map.put("status", "Success");
			map.put("responseCode", 200);
			map.put("message", "Screening details are saved successfully");
			LOGGER.info("Question saved using ScreeningServiceImpl.createScreeningQuestion()");
		}
		catch (Exception e) {
			LOGGER.error("Question saving failed using ScreeningServiceImpl.createScreeningQuestion()");
			map = new HashMap<String, Object>();
			map.put("status", "Failed");
			map.put("responseCode", 400);
			map.put("message", "Screening details saving failed!!!");
			e.printStackTrace();
		}
		return map;
	}
	
	@Override
	public Map<String, Object> updateScreeningQuestion(String slug, ScreeningBean screeningBean) {
		LOGGER.debug("Starting of ScreeningServiceImpl.updateScreeningQuestion(-)");
		Optional<ScreeningEntity> optional = null;
		ScreeningEntity screeningEntity = null, screeningEntityReturn = null;
		ScreeninQuestionOptions screeninQuestionOptions = null;
		List<String> options = null;
		Map<String, Object> map = null;
		Long screeningTableId = null;
		try {
			screeningTableId = screeningEntityRepository.findScreeningEntityIdBySlug(slug);
			if(screeningTableId!=null) {
				optional = screeningEntityRepository.findById(screeningTableId);
				if(!optional.isEmpty()) {
					screeningEntity = optional.get();
					screeningEntity.setQuestions(screeningBean.getQuestions());
					screeninQuestionOptionsRepository.deleteScreeningQuestionOptions(screeningTableId);
					screeningEntityReturn = screeningEntityRepository.save(screeningEntity);
					options = screeningBean.getOptions();
					for(String option:options) {
						screeninQuestionOptions = new ScreeninQuestionOptions();
						screeninQuestionOptions.setScreeningTableId(screeningEntityReturn.getScreeningTableId());
						screeninQuestionOptions.setOptions(option);
						screeninQuestionOptionsRepository.save(screeninQuestionOptions);
					}
					map = new HashMap<String, Object>();
					map.put("status", "Success");
					map.put("responseCode", 200);
					map.put("message", "Screening details are updated successfully!!!");
					LOGGER.info("Question updated using ScreeningServiceImpl.createScreeningQuestion()");
				}
				else {
					map = new HashMap<String, Object>();
					map.put("status", "Failed");
					map.put("responseCode", 400);
					map.put("message", "Screening details updation failed!!!");
				}
			}
			else {
				map = new HashMap<String, Object>();
				map.put("status", "Failed");
				map.put("responseCode", 400);
				map.put("message", "Screening details updation failed!!!");
			}
		}
		catch (Exception e) {
			LOGGER.error("Question saving failed using ScreeningServiceImpl.createScreeningQuestion()");
			map = new HashMap<String, Object>();
			map.put("status", "Failed");
			map.put("responseCode", 400);
			map.put("message", "Screening details saving failed!!!");
			e.printStackTrace();
		}
		return map;
	}
	
	private static String generateRandomString() {
		String alphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";
		StringBuilder sb = new StringBuilder(7);
		for (int i = 0; i < 7; i++) {
            int index = (int)(alphaNumericString.length()* Math.random());
            sb.append(alphaNumericString.charAt(index));
        }
		return sb.toString();
	}
}
