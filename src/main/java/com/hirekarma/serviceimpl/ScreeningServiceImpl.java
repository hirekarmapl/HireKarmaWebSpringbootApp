package com.hirekarma.serviceimpl;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
		}
		catch (Exception e) {
			map = new HashMap<String, Object>();
			map.put("status", "Failed");
			map.put("responseCode", 400);
			map.put("message", "Screening details saving failed!!!");
		}
		return map;
	}
	
	private static String generateRandomString() {
		byte[] array = new byte[7];
		new Random().nextBytes(array);
		String generatedString = new String(array,Charset.forName("UTF-8"));
		return generatedString;
	}

}
