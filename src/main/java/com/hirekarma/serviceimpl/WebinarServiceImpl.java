package com.hirekarma.serviceimpl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.apache.http.auth.AuthScheme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirekarma.beans.WebinarRequest;
import com.hirekarma.controller.WebinarController;
import com.hirekarma.model.Corporate;
import com.hirekarma.model.Webinar;
import com.hirekarma.repository.WebinarRepository;
import com.hirekarma.service.WebinarService;
@Service("WebinarService")
public class WebinarServiceImpl implements WebinarService {
	
	private static final Logger logger = LoggerFactory.getLogger(WebinarServiceImpl.class);

	
	@Autowired
	WebinarRepository webinarRepository;

	@Override
	public Map<String, Object> addForCorporate(WebinarRequest webinarRequest, Corporate corporate) throws Exception {
		logger.debug(" - addForCorporate");
		Map<String,Object> response  = new HashMap();
		Webinar webinar = new Webinar();
		webinar = updateWebinarForBeanNotNull(webinar,webinarRequest);
		webinar.setCorporate(corporate);
		webinar = this.webinarRepository.save(webinar);
		logger.debug(" exiting - addForCorporate");
		return response;
	}

	@Override
	public Map<String, Object> findAllForCorporate(Corporate corporate) throws Exception {
		logger.debug(" - findAllForCorporate()");
		Map<String,Object> response  = new HashMap();
		List<Webinar> webinars = webinarRepository.findByCorporateOrCorporateIsNull(corporate);
		response.put("webinars", webinars);
		logger.debug(" exiting - findAllForCorporate()");
		return response;	
	}

	public Webinar updateWebinarForBeanNotNull(Webinar webinar, WebinarRequest webinarRequest) {
		logger.debug("entering - updateWebinarForBeanNotNull()");
		if(webinarRequest.getIsAccepted()!=null) {
			webinar.setAccepted(webinarRequest.getIsAccepted());
		}
		
		if(webinarRequest.getIsDisable()!=null) {
			webinar.setIsDisable(webinarRequest.getIsDisable());
		}
		
		if(webinarRequest.getDescription()!=null) {
			webinar.setDescription(webinarRequest.getDescription());
		}
		
		if(webinarRequest.getMeetLink()!=null && !webinarRequest.getMeetLink().equals("")) {
			webinar.setMeetLink(webinarRequest.getMeetLink());
		}
		
		if(webinarRequest.getTitle()!=null && !webinarRequest.getTitle().equals("")) {
			webinar.setTitle(webinarRequest.getTitle());
		}
		
		if(webinarRequest.getScheduledDate()!=null) {

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime scheduledDate = LocalDateTime.parse(webinarRequest.getScheduledDate(), formatter);
			webinar.setScheduledAt(scheduledDate);
			
		}
		logger.debug(" exiting - updateWebinarForBeanNotNull ()");
		return webinar;
	}
	
	@Override
	public Map<String, Object> update(String webinarId,WebinarRequest webinarRequest, Corporate corporate) throws Exception {
		logger.debug("entering - update()");
		Map<String, Object> response = new HashMap<String, Object>();
		Optional<Webinar> optionalWebinar = this.webinarRepository.findById(webinarId);
		if(!optionalWebinar.isPresent()) {
			throw new NoSuchElementException("invalid webinar id");
		}
		Webinar webinar = optionalWebinar.get();
		if(webinar.getCorporate()==null || webinar.getCorporate().getCorporateId()!=corporate.getCorporateId()) {
			throw new Exception("unAuthorized");
		}
		webinar = updateWebinarForBeanNotNull(webinar,webinarRequest);
		webinar = this.webinarRepository.save(webinar);
		logger.debug("exiting - update");
		return response;
	}

	@Override
	public Map<String, Object> updateIsAcceptedStatus(boolean status,String webinarId) throws Exception {
		logger.debug(" entering - updateIsAcceptedStatus()");
		Webinar webinar = this.webinarRepository.getById(webinarId);
		webinar.setAccepted(status);
		this.webinarRepository.save(webinar);
		logger.debug("exiting - updateIsAcceptedStatus()");
		return new HashMap<String, Object>();
	}

	@Override
	public Map<String, Object> findAllForAdmin() throws Exception {
		// TODO Auto-generated method stub
		logger.debug("entering - findAllForAdmin");
		Map<String,Object> response = new HashMap<String, Object>();
		response.put("webinars", webinarRepository.findAll());
		logger.debug("exiting - findAllForAdmin");
		return response;
	}

	@Override
	public Map<String, Object> addForAdmin(WebinarRequest webinarRequest) throws Exception {
		logger.debug("entering - addForAdmin");
		Map<String,Object> response  = new HashMap();
		Webinar webinar = new Webinar();
		webinar = updateWebinarForBeanNotNull(webinar,webinarRequest);
		webinar = this.webinarRepository.save(webinar);
		logger.debug("exiting - addForAdmin");
		return response;
	}

	@Override
	public Map<String, Object> findAllForStudent() throws Exception {
		logger.debug("entering - findAllForStudent()");
		Map<String,Object> response  = new HashMap();
		response.put("webinars", this.webinarRepository.findAllWithScheduledAtAfterAndDisabledIsNullOrDisabledIsNotActiveAndIsAcceptedActive(LocalDateTime.now()));
		logger.debug("exiting - findAllForStudent()");
		return response;
	}

	@Override
	public Map<String, Object> updateIsDisableStatus(boolean status, String webinarId, Corporate corporate)
			throws Exception {
		logger.debug("entering - updateIsDisableStatus()");
		Webinar webinar = this.webinarRepository.getById(webinarId);
		if(webinar.getCorporate()==null || webinar.getCorporate().getCorporateId()!=corporate.getCorporateId()) {
			throw new Exception("unAuthorized");
		}
		webinar.setIsDisable(status);
		this.webinarRepository.save(webinar);
		logger.debug("exiting - updateIsDisableStatus()");
		return new HashMap<String, Object>();
	
	}


	
}
