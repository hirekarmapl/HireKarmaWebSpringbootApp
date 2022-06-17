package com.hirekarma.serviceimpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirekarma.beans.ScreeningBean;
import com.hirekarma.model.Message;
import com.hirekarma.model.ScreeninQuestionOptions;
import com.hirekarma.model.ScreeningEntity;
import com.hirekarma.model.ScreeningEntityParent;
import com.hirekarma.model.ScreeningResponse;
import com.hirekarma.repository.MessageRepository;
import com.hirekarma.repository.ScreeninQuestionOptionsRepository;
import com.hirekarma.repository.ScreeningEntityParentRepository;
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
	
	@Autowired
	private ScreeningEntityParentRepository screeningEntityParentRepository;
	

	@Autowired
	private MessageRepository messageRepository;
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
			if(screeningBean.getCorporateId()!=null) {
				screeningEntity.setCorporateId(screeningBean.getCorporateId());
			}
			if(screeningBean.getUniversityId()!=null) {
				screeningEntity.setUniversityId(screeningBean.getUniversityId());
			}
			screeningEntity.setQuestions(screeningBean.getQuestions());
			screeningEntity.setQuestionType(screeningBean.getQuestionType());
			 slug = String.join("-",screeningBean.getQuestions().replaceAll("[^a-zA-Z0-9]", "-").split(" "));
			 
			slug += (""+generateRandomString());
			screeningEntity.setSlug(slug);
			screeningEntityReturn = screeningEntityRepository.save(screeningEntity);
			if(screeningBean.getQuestionType()==0) {
				options = screeningBean.getOptions();
				for(String option:options) {
					screeninQuestionOptions = new ScreeninQuestionOptions();
					screeninQuestionOptions.setScreeningTableId(screeningEntityReturn.getScreeningTableId());
					screeninQuestionOptions.setOptions(option);
					screeninQuestionOptionsRepository.save(screeninQuestionOptions);
				}
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
	public Map<String, Object> createListScreeningQuestion(List<ScreeningBean> screeningBeans, Long corporateId,Long universityId) {
		LOGGER.debug("starting of ScreeningServiceImpl.createScreeningQuestion()");
		Map<String, Object> map = null;
		try {
			screeningBeans.forEach(screeningBean->{
				ScreeningEntity screeningEntity = new ScreeningEntity();
//				System.out.print("corporate Id"+corporateId);
				if(corporateId!=null) {
					screeningEntity.setCorporateId(corporateId);
				}
				if(universityId!=null) {
					screeningEntity.setUniversityId(universityId);
				}
				screeningEntity.setQuestions(screeningBean.getQuestions());
				screeningEntity.setQuestionType(screeningBean.getQuestionType());
				String slug = String.join("-",screeningBean.getQuestions().replaceAll("[^a-zA-Z0-9]", "-").split(" "));
				 
					slug += (""+generateRandomString());
					screeningEntity.setSlug(slug);
				ScreeningEntity screeningEntityReturn = screeningEntityRepository.save(screeningEntity);
				
				if(screeningBean.getQuestionType()==0) {
					List<String> options = screeningBean.getOptions();
					for(String option:options) {
						ScreeninQuestionOptions screeninQuestionOptions = new ScreeninQuestionOptions();
						screeninQuestionOptions.setScreeningTableId(screeningEntityReturn.getScreeningTableId());
						screeninQuestionOptions.setOptions(option);
						screeninQuestionOptionsRepository.save(screeninQuestionOptions);
					}
				}
			});
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
	public Map<String, Object> updateScreeningQuestion(String slug, ScreeningBean screeningBean,Long corporateId, Long universityId) {
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
					if(corporateId!=null) {
						if(screeningEntity.getCorporateId()==null || screeningEntity.getCorporateId().compareTo(corporateId)!=0) {
							throw new Exception("unauthorized");
						}
					}
					else if(universityId!=null) {
						if(screeningEntity.getUniversityId()==null || screeningEntity.getUniversityId().compareTo(universityId)!=0) {
							throw new Exception("unauthorized");
						}
					}
					else {
						if(screeningEntity.getUniversityId()!=null && screeningEntity.getCorporateId()!=null) {
							throw new Exception("unauthorized");
						}
					}
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
					map = new HashMap<String, Object>();
					map.put("status", "Failed");
					map.put("responseCode", 400);
					map.put("message", "invalid id");;
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
	public Map<String, Object> deleteScreeningQuestion(String slug,Long corporateId,Long universityId) {
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
					if(corporateId!=null) {
						if(screeningEntity.getCorporateId().compareTo(corporateId)!=0) {
							throw new Exception("invalid request");
						}
					}
					else if(universityId!=null) {
						if(screeningEntity.getUniversityId().compareTo(universityId)!=0) {
							throw new Exception("invalid request");
						}
					}
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
				map.put("message", "invalid Id!");
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
	public void sendScreeningQuestionToStudents(List<Long> jobApplyIds,String screeningSlug) throws Exception {
//		Long screeningTableId = screeningEntityRepository.findScreeningEntityIdBySlug(screeningSlug);
//		if(screeningTableId==null) {
//			throw new Exception("invalid screeening slug");
//		}
//		List<ScreeningResponse> screeningResponses = new ArrayList();
//		for(Long jobApplyId:jobApplyIds) {
//			 ScreeningResponse screeningResponse = new ScreeningResponse();
//			screeningResponse.setJobApplyId(jobApplyId);
//			screeningResponse.setScreeningId(screeningTableId);
//			screeningResponses.add(screeningResponse);
//		}
//		screeningResponseRepository.saveAll(screeningResponses);
	}
	@Override
	public void sendScreeningQuestionsToMultipleStudent(List<Long> jobApplyIds,List<String> screeningSlugs) throws Exception{
//		List<ScreeningResponse> screeningResponses = new ArrayList<>();
//		for(String screeningSlug : screeningSlugs) {
//			for(Long jobApplyId :jobApplyIds) {
//				screeningEntityParentOptional = screeningEntityParentRepository.findById(s);
//				if(screeningTableId==null) {
//					throw new Exception("wrong slug id");
//				}
//				ScreeningResponse screeningResponse = new ScreeningResponse();
//				screeningResponse.setJobApplyId(jobApplyId);
//				screeningResponse.setScreeningId(screeningTableId); 
//				screeningResponses.add(screeningResponse);
//			}
//			
//		}
//		this.screeningResponseRepository.saveAll(screeningResponses);
	}
	@Override
	public Map<String, Object> sendScreeningQuestions(Long jobApplyId, String screeningParentSlug,Long chatRoomId) {
		LOGGER.debug("Starting of ScreeningServiceImpl.sendScreeningQuestions(-,-)");
		Map<String, Object> map = null;
		Optional<ScreeningEntityParent> screeningEntityParentOptional = null;
		ScreeningResponse screeningResponse = null;
		try {
			List<ScreeningResponse> screeningResponses = new ArrayList<>();
			screeningEntityParentOptional = screeningEntityParentRepository.findById(screeningParentSlug);
			if(screeningEntityParentOptional.isPresent()) {
				Message message = new Message();
				message.setCreatedOn(LocalDateTime.now());
				message.setChatRoomId(chatRoomId);
				message.setSenderType("corporate");
				messageRepository.save(message);
				for(ScreeningEntity screeningEntity: screeningEntityParentOptional.get().getScreeningEntities()) {
					 screeningResponse = new ScreeningResponse();
					 screeningResponse.setJobApplyId(jobApplyId);
					 screeningResponse.setScreeningEntity(screeningEntity);
					 screeningResponse.setMessage(message);
					 screeningResponses.add(screeningResponse);
				}
				screeningResponses = screeningResponseRepository.saveAll(screeningResponses);
				
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
	
	@Override
	public Map<String, Object> getAllScreeningQuestions() {
		LOGGER.debug("Starting of ScreeningServiceImpl.getAllScreeningQuestions()");
		List<ScreeningEntity> screeningEntities = null;
		Map<String, Object> map = null;
		try {
			screeningEntities = screeningEntityRepository.findAll();
			map = new HashMap<String, Object>();
			map.put("status", "Success");
			map.put("responseCode", 200);
			map.put("data", screeningEntities);
			LOGGER.info("Getting success in ScreeningServiceImpl.getAllScreeningQuestions()");
			return map;
		}
		catch (Exception e) {
			LOGGER.error("error in ScreeningServiceImpl.getAllScreeningQuestions()");
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
		for (int i = 0; i < 10; i++) {
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
