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
import com.hirekarma.model.ScreeningResponse;
import com.hirekarma.repository.ScreeninQuestionOptionsRepository;
import com.hirekarma.repository.ScreeningEntityRepository;
import com.hirekarma.repository.ScreeningResponseRepository;
import com.hirekarma.service.ScreeningService;

@Service("screeningServiceImpl")
public class ScreeningServiceImpl implements ScreeningService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ScreeningServiceImpl.class);
	
	@Autowired
	private ScreeningEntityRepository screeningEntityRepository;
	
	@Autowired
	private ScreeninQuestionOptionsRepository screeninQuestionOptionsRepository;
	
	@Autowired
	private ScreeningResponseRepository screeningResponseRepository;

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
	
	@Override
	public Map<String, Object> deleteScreeningQuestion(String slug) {
		LOGGER.debug("Starting of ScreeningServiceImpl.deleteScreeningQuestion(-)");
		Optional<ScreeningEntity> optional = null;
		ScreeningEntity screeningEntity = null;
		Map<String, Object> map = null;
		Long screeningTableId = null;
		try {
			screeningTableId = screeningEntityRepository.findScreeningEntityIdBySlug(slug);
			if(screeningTableId!=null) {
				optional = screeningEntityRepository.findById(screeningTableId);
				if(!optional.isEmpty()) {
					screeningEntity = optional.get();
					screeninQuestionOptionsRepository.deleteScreeningQuestionOptions(screeningTableId);
					screeningEntityRepository.delete(screeningEntity);
					map = new HashMap<String, Object>();
					map.put("status", "Success");
					map.put("responseCode", 200);
					map.put("message", "Screening details are deleted successfully!!!");
					LOGGER.info("Question deleted using ScreeningServiceImpl.deleteScreeningQuestion()");
				}
				else {
					map = new HashMap<String, Object>();
					map.put("status", "Failed");
					map.put("responseCode", 400);
					map.put("message", "Screening details deletion failed!!!");
				}
			}
			else {
				map = new HashMap<String, Object>();
				map.put("status", "Failed");
				map.put("responseCode", 400);
				map.put("message", "Screening details deletion failed!!!");
			}
		}
		catch (Exception e) {
			LOGGER.error("Question deleting failed using ScreeningServiceImpl.deleteScreeningQuestion()");
			map = new HashMap<String, Object>();
			map.put("status", "Failed");
			map.put("responseCode", 400);
			map.put("message", "Deletion of screening details failed!!!");
			e.printStackTrace();
		}
		return map;
	}
	
	@Override
	public Map<String, Object> sendScreeningQuestions(Long jobApplyId, String screeningSlug) {
		LOGGER.debug("Starting of ScreeningServiceImpl.sendScreeningQuestions(-,-)");
		Map<String, Object> map = null;
		Long screeningTableId = null;
		ScreeningResponse screeningResponse = null;
		try {
			screeningTableId = screeningEntityRepository.findScreeningEntityIdBySlug(screeningSlug);
			if(screeningTableId!=null) {
				screeningResponse = new ScreeningResponse();
				screeningResponse.setJobApplyId(jobApplyId);
				screeningResponse.setScreeningId(screeningTableId);
				screeningResponseRepository.save(screeningResponse);
				map = new HashMap<String, Object>();
				map.put("status", "Success");
				map.put("responseCode", 200);
				map.put("message", "Question sent successfully");
				LOGGER.info("Question sent successfully using ScreeningServiceImpl.deleteScreeningQuestion()");
			}
			else {
				map = new HashMap<String, Object>();
				map.put("status", "Failed");
				map.put("responseCode", 400);
				map.put("message", "Question sending failed!!!");
			}
			return map;
		}
		catch (Exception e) {
			LOGGER.error("Question sending failed using ScreeningServiceImpl.sendScreeningQuestions()");
			map = new HashMap<String, Object>();
			map.put("status", "Failed");
			map.put("responseCode", 400);
			map.put("message", "Question sending failed!!!");
			e.printStackTrace();
			return map;
		}
	}
	
	@Override
	public Map<String, Object> getScreeningQuestionsByScreeningTableId(String slug) {
		LOGGER.debug("Starting of ScreeningServiceImpl.getScreeningQuestionsByScreeningTableId(-)");
		Optional<ScreeningEntity> optional = null;
		ScreeningEntity screeningEntity = null;
		Map<String, Object> map = null;
		Long screeningTableId = null;
		try {
			screeningTableId = screeningEntityRepository.findScreeningEntityIdBySlug(slug);
			if(screeningTableId != null) {
				optional = screeningEntityRepository.findById(screeningTableId);
				if(!optional.isEmpty()) {
					screeningEntity = optional.get();
					map = new HashMap<String, Object>();
					map.put("status", "Success");
					map.put("responseCode", 200);
					map.put("data", screeningEntity);
					LOGGER.info("Getting success in ScreeningServiceImpl.getScreeningQuestionsByScreeningTableId(-)");
				}
				else {
					map = new HashMap<String, Object>();
					map.put("status", "Failed");
					map.put("responseCode", 400);
					map.put("message", "No screening details found with this id");
				}
			}
			else {
				map = new HashMap<String, Object>();
				map.put("status", "Failed");
				map.put("responseCode", 400);
				map.put("message", "No screening details found with this id");
			}
			return map;
		}
		catch (Exception e) {
			LOGGER.error("error in ScreeningServiceImpl.getScreeningQuestionsByScreeningTableId(-)");
			map = new HashMap<String, Object>();
			map.put("status", "Failed");
			map.put("responseCode", 400);
			map.put("message", "Bad Request");
			e.printStackTrace();
			return map;
		}
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

	@Override
	public Map<String, Object> responseToScreeningQuestions(Long response) {
		// TODO Auto-generated method stub
		return null;
	}
}
