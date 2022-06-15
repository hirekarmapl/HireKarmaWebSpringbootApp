package com.hirekarma.serviceimpl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirekarma.beans.ScreeningBean;
import com.hirekarma.beans.ScreeningEntityParentBean;
import com.hirekarma.controller.ScreeningEntityParentController;
import com.hirekarma.model.Corporate;
import com.hirekarma.model.ScreeningEntity;
import com.hirekarma.model.ScreeningEntityParent;
import com.hirekarma.model.University;
import com.hirekarma.repository.ScreeningEntityParentRepository;
import com.hirekarma.repository.ScreeningEntityRepository;
import com.hirekarma.service.ScreeningEntityParentService;

@Service("ScreeningEntityParentService")
public class ScreeningEntityParentServiceImpl implements ScreeningEntityParentService {

	private static final Logger logger = LoggerFactory.getLogger(ScreeningEntityParentServiceImpl.class);

	@Autowired
	private ScreeningEntityRepository screeningEntityRepository;

	@Autowired
	private ScreeningEntityParentRepository screeningEntityParentRepository;

	@Override
	public Map<String, Object> addQuestions(ScreeningEntityParentBean screeningEntityParentBean) throws Exception {
		Map<String, Object> response = new HashMap<>();
		Optional<ScreeningEntityParent> screeningEntityParentOptional = this.screeningEntityParentRepository
				.findById(screeningEntityParentBean.getScreeningEntityParentSlug());
		if (!screeningEntityParentOptional.isPresent()) {
			throw new Exception("invalid slug");
		}

		List<ScreeningEntity> screeningEntities = screeningEntityRepository
				.findAllById(screeningEntityParentBean.getScreeningEntityIds());
		if (screeningEntities.size() != screeningEntityParentBean.getScreeningEntityIds().size()) {
			throw new Exception("please check your input for ids");
		}

		ScreeningEntityParent screeningEntityParent = screeningEntityParentOptional.get();
		screeningEntityParent.getScreeningEntities().addAll(screeningEntities);
		response.put("screeningEntityParent", this.screeningEntityParentRepository.save(screeningEntityParent));
		return response;
	}

	@Override
	public Map<String, Object> addQuestionsByCorporate(ScreeningEntityParentBean screeningEntityParentBean,
			Corporate corporate) throws Exception {
		Map<String, Object> response = new HashMap<>();

		Optional<ScreeningEntityParent> screeningEntityParentOptional = this.screeningEntityParentRepository
				.findById(screeningEntityParentBean.getScreeningEntityParentSlug());
		if (!screeningEntityParentOptional.isPresent()) {
			throw new Exception("invalid slug");
		}

		ScreeningEntityParent screeningEntityParent = screeningEntityParentOptional.get();
		logger.info("corporate Id {} screeningEntityParent.corporateId {}", corporate.getCorporateId(),
				screeningEntityParent.getCoporateId());
		if (screeningEntityParent.getCoporateId().compareTo(corporate.getCorporateId()) != 0) {
			throw new Exception("unauthorized");
		}
		List<ScreeningEntity> screeningEntities = screeningEntityRepository
				.findAllById(screeningEntityParentBean.getScreeningEntityIds());
		if (screeningEntities.size() != screeningEntityParentBean.getScreeningEntityIds().size()) {
			throw new Exception("please check your input for ids");
		}

		screeningEntityParent.getScreeningEntities().addAll(screeningEntities);
		response.put("screeningEntityParent", this.screeningEntityParentRepository.save(screeningEntityParent));
		return response;
	}

	@Override
	public Map<String, Object> addQuestionsByUniversity(ScreeningEntityParentBean screeningEntityParentBean,
			University university) throws Exception {
		Map<String, Object> response = new HashMap<>();
		logger.debug("addQuestionsByUniversity()");
		Optional<ScreeningEntityParent> screeningEntityParentOptional = this.screeningEntityParentRepository
				.findById(screeningEntityParentBean.getScreeningEntityParentSlug());
		if (!screeningEntityParentOptional.isPresent()) {
			throw new Exception("invalid slug");
		}

		ScreeningEntityParent screeningEntityParent = screeningEntityParentOptional.get();
		logger.info("univeristy Id {} screeningEntityParent.corporateId {}", university.getUniversityId(),
				screeningEntityParent.getUniversityId());
		if (screeningEntityParent.getUniversityId().compareTo(university.getUniversityId()) != 0) {
			throw new Exception("unauthorized");
		}
		List<ScreeningEntity> screeningEntities = screeningEntityRepository
				.findAllById(screeningEntityParentBean.getScreeningEntityIds());
		if (screeningEntities.size() != screeningEntityParentBean.getScreeningEntityIds().size()) {
			throw new Exception("please check your input for ids");
		}

		screeningEntityParent.getScreeningEntities().addAll(screeningEntities);
		response.put("screeningEntityParent", this.screeningEntityParentRepository.save(screeningEntityParent));
		return response;
	}
	
	@Override
	public Map<String, Object> deleteQuestionsByCorporate(ScreeningEntityParentBean screeningEntityParentBean,
			Corporate corporate) throws Exception {
		Map<String, Object> response = new HashMap<>();

		Optional<ScreeningEntityParent> screeningEntityParentOptional = this.screeningEntityParentRepository
				.findById(screeningEntityParentBean.getScreeningEntityParentSlug());
		if (!screeningEntityParentOptional.isPresent()) {
			throw new Exception("invalid slug");
		}

		ScreeningEntityParent screeningEntityParent = screeningEntityParentOptional.get();
		logger.info("corporate Id {} screeningEntityParent.corporateId {}", corporate.getCorporateId(),
				screeningEntityParent.getCoporateId());
		if (screeningEntityParent.getCoporateId().compareTo(corporate.getCorporateId()) != 0) {
			throw new Exception("unauthorized");
		}
		
		List<ScreeningEntity> screeningEntities = screeningEntityRepository
				.findAllById(screeningEntityParentBean.getScreeningEntityIds());
		if (screeningEntities.size() != screeningEntityParentBean.getScreeningEntityIds().size()) {
			throw new Exception("please check your input for ids");
		}
		
		screeningEntityParent.getScreeningEntities().removeAll(screeningEntities);
		this.screeningEntityParentRepository.save(screeningEntityParent);
	
		return response;
	}
	
	@Override
	public Map<String, Object> deleteQuestionsByUniversity(ScreeningEntityParentBean screeningEntityParentBean,
			University university) throws Exception {
		Map<String, Object> response = new HashMap<>();

		Optional<ScreeningEntityParent> screeningEntityParentOptional = this.screeningEntityParentRepository
				.findById(screeningEntityParentBean.getScreeningEntityParentSlug());
		if (!screeningEntityParentOptional.isPresent()) {
			throw new Exception("invalid slug");
		}

		ScreeningEntityParent screeningEntityParent = screeningEntityParentOptional.get();
		logger.info("corporate Id {} screeningEntityParent.corporateId {}", university.getUniversityId(),
				screeningEntityParent.getUniversityId());
		if (screeningEntityParent.getUniversityId().compareTo(university.getUniversityId()) != 0) {
			throw new Exception("unauthorized");
		}
		
		List<ScreeningEntity> screeningEntities = screeningEntityRepository
				.findAllById(screeningEntityParentBean.getScreeningEntityIds());
		if (screeningEntities.size() != screeningEntityParentBean.getScreeningEntityIds().size()) {
			throw new Exception("please check your input for ids");
		}
		
		screeningEntityParent.getScreeningEntities().removeAll(screeningEntities);
		this.screeningEntityParentRepository.save(screeningEntityParent);
	
		return response;
	}

	
	@Override
	public Map<String, Object> deleteQuestions(ScreeningEntityParentBean screeningEntityParentBean) throws Exception {
		Map<String, Object> response = new HashMap<>();

		Optional<ScreeningEntityParent> screeningEntityParentOptional = this.screeningEntityParentRepository
				.findById(screeningEntityParentBean.getScreeningEntityParentSlug());
		if (!screeningEntityParentOptional.isPresent()) {
			throw new Exception("invalid slug");
		}
		ScreeningEntityParent screeningEntityParent = screeningEntityParentOptional.get();
		if(screeningEntityParent.getCoporateId()==null && screeningEntityParent.getUniversityId()==null)
		{
			throw new Exception("unauthorized");
		}
		List<ScreeningEntity> screeningEntities = screeningEntityRepository
				.findAllById(screeningEntityParentBean.getScreeningEntityIds());
		if (screeningEntities.size() != screeningEntityParentBean.getScreeningEntityIds().size()) {
			throw new Exception("please check your input for ids");
		}
		
		screeningEntityParent.getScreeningEntities().removeAll(screeningEntities);
		this.screeningEntityParentRepository.save(screeningEntityParent);
	
		return response;
	}

	@Override
	public Map<String, Object> createByCorporate(String title, Corporate corporate) {
		Map<String, Object> response = new HashMap<>();
		ScreeningEntityParent screeningEntityParent = new ScreeningEntityParent();
		screeningEntityParent.setTitle(title);
		screeningEntityParent.setCoporateId(corporate.getCorporateId());
		screeningEntityParent.setCreatedOn(LocalDateTime.now());
		screeningEntityParent.setUpdatedOn(LocalDateTime.now());
		screeningEntityParent.setDeleted(false);
		response.put("screeningEntityParent", this.screeningEntityParentRepository.save(screeningEntityParent));
		return response;
	}
	
@Override
	public Map<String, Object> createByUniversity(String title, University university) {
		Map<String, Object> response = new HashMap<>();
		ScreeningEntityParent screeningEntityParent = new ScreeningEntityParent();
		screeningEntityParent.setTitle(title);
		screeningEntityParent.setUniversityId(university.getUniversityId());
		screeningEntityParent.setCreatedOn(LocalDateTime.now());
		screeningEntityParent.setUpdatedOn(LocalDateTime.now());
		screeningEntityParent.setDeleted(false);
		response.put("screeningEntityParent", this.screeningEntityParentRepository.save(screeningEntityParent));
		return response;
	}

	@Override
	public Map<String, Object> create(String title) {
		Map<String, Object> response = new HashMap<>();
		ScreeningEntityParent screeningEntityParent = new ScreeningEntityParent();
		screeningEntityParent.setTitle(title);
		screeningEntityParent.setCreatedOn(LocalDateTime.now());
		screeningEntityParent.setUpdatedOn(LocalDateTime.now());
		screeningEntityParent.setDeleted(false);
		response.put("screeningEntityParent", this.screeningEntityParentRepository.save(screeningEntityParent));
		return response;
	}


	@Override
	public Map<String, Object> sendToStudents(ScreeningEntityParentBean screeningEntityParentBean) throws Exception {
		
		return null;
	}

	@Override
	public Map<String, Object> findAllByCorporate(Corporate corporate) {
		Map<String,Object> response = new HashMap<String, Object>();
		response.put("screeningEntityParent", this.screeningEntityParentRepository.findByCoporateIdAndDeletedFalse(corporate.getCorporateId()));		
		return response;
	}

	@Override
	public Map<String, Object> findAllByUniversity(University university) {
		Map<String,Object> response = new HashMap<String, Object>();
		response.put("screeningEntityParent", this.screeningEntityParentRepository.findByUniversityIdAndDeletedFalse(university.getUniversityId()));				
		return response;
	}

	@Override
	public Map<String, Object> findAll() {
		Map<String,Object> response = new HashMap<String, Object>();
		response.put("screeningEntityParent", this.screeningEntityParentRepository.findByAdmin());			
		return response;
	}

	@Override
	public Map<String, Object> delete(ScreeningEntityParentBean screeningEntityParentBean) throws Exception{
		Map<String, Object> response = new HashMap<>();

		Optional<ScreeningEntityParent> screeningEntityParentOptional = this.screeningEntityParentRepository
				.findById(screeningEntityParentBean.getScreeningEntityParentSlug());
		if (!screeningEntityParentOptional.isPresent()) {
			throw new Exception("invalid slug");
		}
		ScreeningEntityParent screeningEntityParent = screeningEntityParentOptional.get();
		if(screeningEntityParent.getCoporateId()!=null || screeningEntityParent.getUniversityId()!=null)
		{
			throw new Exception("unauthorized");
		}
		screeningEntityParent.setDeleted(true);
		this.screeningEntityParentRepository.save(screeningEntityParent);
		return response;
	}

	@Override
	public Map<String, Object> deleteByCorporate(ScreeningEntityParentBean screeningEntityParentBean,
			Corporate corporate) throws Exception {
		Map<String, Object> response = new HashMap<>();

		Optional<ScreeningEntityParent> screeningEntityParentOptional = this.screeningEntityParentRepository
				.findById(screeningEntityParentBean.getScreeningEntityParentSlug());
		if (!screeningEntityParentOptional.isPresent()) {
			throw new Exception("invalid slug");
		}

		ScreeningEntityParent screeningEntityParent = screeningEntityParentOptional.get();
		
		if (screeningEntityParent.getCoporateId().compareTo(corporate.getCorporateId()) != 0) {
			throw new Exception("unauthorized");
		}
		
		screeningEntityParent.setDeleted(true);
		this.screeningEntityParentRepository.save(screeningEntityParent);
		return response;
	}
	

	@Override
	public Map<String, Object> deleteByUniversity(ScreeningEntityParentBean screeningEntityParentBean,
			University university) throws Exception {
		Map<String, Object> response = new HashMap<>();

		Optional<ScreeningEntityParent> screeningEntityParentOptional = this.screeningEntityParentRepository
				.findById(screeningEntityParentBean.getScreeningEntityParentSlug());
		if (!screeningEntityParentOptional.isPresent()) {
			throw new Exception("invalid slug");
		}

		ScreeningEntityParent screeningEntityParent = screeningEntityParentOptional.get();
		
		if (screeningEntityParent.getUniversityId().compareTo(university.getUniversityId()) != 0) {
			throw new Exception("unauthorized");
		}
		screeningEntityParent.setDeleted(true);
		this.screeningEntityParentRepository.save(screeningEntityParent);
		return response;
	}

}
