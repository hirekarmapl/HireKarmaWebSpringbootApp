package com.hirekarma.serviceimpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.apache.http.impl.cookie.DateParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirekarma.beans.EventBean;
import com.hirekarma.model.Corporate;
import com.hirekarma.model.Event;
import com.hirekarma.model.University;
import com.hirekarma.model.UserProfile;
import com.hirekarma.repository.EventRepository;
import com.hirekarma.repository.UserRepository;
import com.hirekarma.service.EventService;
@Service("EventService")
public class EventServiceImpl implements EventService {

	@Autowired
	EventRepository eventRepository;
	
	@Autowired
	private AWSS3Service awss3Service;
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public Map<String, Object> updateEvent(EventBean eventBean, String slug, Corporate corporate, University university)
			throws Exception {
		Map<String,Object> result = new HashMap();
		Optional<Event> eventOptional = this.eventRepository.findById(slug);
		if(!eventOptional.isPresent()) {
			throw new Exception("invalid id");
		}
		Event event = eventOptional.get();
		if(corporate!=null) {
			if(!event.getCorporate().equals(corporate)) {
				throw new Exception("invalid request");
			}
		}
		else if(university!=null) {
			if(!event.getUniversity().equals(university)) {
				throw new Exception("invalid request");
			}
		}
		else {
			if(event.getUniversity()!=null&&event.getCorporate()!=null) {
				throw new Exception("invalid request");
			}
			
		}
		event = this.updateEventForBeanNotNull(event, eventBean);
		event = this.eventRepository.save(event);
		result.put("event", event);
		return result;
	}

	@Override
	public Map<String, Object> getAllEventsForPublic() throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> result = new HashMap();
		result.put("events", this.eventRepository.findAll());
		return result;
	}

	@Override
	public Map<String, Object> getAllEvents(Corporate corporate, University university) throws Exception {
		Map<String,Object> result = new HashMap();
		if(corporate==null) {
			result.put("events",this.eventRepository.findByCorporate(corporate));
		}
		else if(university==null) {
			result.put("events", this.eventRepository.findByUniversity(university));
		}
		else {
			result.put("events",this.eventRepository.findByUniversityIsNullAndCorporateIsNull());
		}
		return result;
	}

	public Event updateEventForBeanNotNull(Event event,EventBean eventBean) throws Exception{
		if(eventBean.getStartTimeString()!=null && eventBean.getEndTimeString()!=null) {
			DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			event.setStartTime(LocalDateTime.parse(eventBean.getStartTimeString(), pattern)); 
			event.setEndTime(LocalDateTime.parse(eventBean.getEndTimeString(), pattern));
			if(event.getStartTime().isAfter(event.getEndTime())) {
				throw new Exception("please check input for startTime and endtime");
			}
		}
		if(eventBean.getSpeaker()!=null && !eventBean.getSpeaker().equals("")) {
			event.setSpeaker(eventBean.getSpeaker());
		}
		if(eventBean.getHost()!=null && !eventBean.getHost().equals("")) {
			event.setHost(eventBean.getHost());
		}
//		if(eventBean.getImg()!=null && !eventBean.getImg().isEmpty()) {
//			event.setCoverImage(awss3Service.uploadFile(eventBean.getImg()));
//		}
		if(eventBean.getKeywords()!=null && !eventBean.getKeywords().equals("")) {
			event.setKeywords(eventBean.getKeywords());
		}
		if(eventBean.getMeetingLink()!=null && !eventBean.getKeywords().equals("")) {
			event.setMeetingLink(eventBean.getMeetingLink());
		}
		if(eventBean.getTitle()!=null && !eventBean.getKeywords().equals("")) {
			event.setTitle(eventBean.getTitle());
		}
		if(eventBean.getCorporate()!=null) {
			event.setCorporate(eventBean.getCorporate());
		}
		if(eventBean.getUniversity()!=null) {
			event.setUniversity(eventBean.getUniversity());
		}
		if(event.getUserProfiles()==null) {
			event.setUserProfiles(new HashSet<UserProfile>());
			event.getUserProfiles().add(eventBean.getUserProfile());
		}
		if(eventBean.getUserIds()!=null && eventBean.getUserIds().size()!=0) {
			
			List<UserProfile> userProfiles = this.userRepository.findAllById(eventBean.getUserIds());
			if(userProfiles.size()!=eventBean.getUserIds().size()) {
				throw new Exception("invalid user ids");
			}
			Set<UserProfile> userProfilesSet = new HashSet<UserProfile>(userProfiles);
			event.getUserProfiles().addAll(userProfilesSet);
			
			
		}
		return event;
		
	}
	@Override
	public Map<String, Object> createEvent(EventBean eventBean) throws Exception {
		Map<String,Object> result = new HashMap();
		Event event = new Event();
		
		event =  this.updateEventForBeanNotNull(event, eventBean);
		event = this.eventRepository.save(event);
		result.put("event", event);
		return result;
	}

	@Override
	public Map<String, Object> addUsersForEvent(String slug, List<Long> userIds, Corporate corporate,
			University university) throws Exception {
		Map<String,Object> result = new HashMap();
		Optional<Event> eventOptional = this.eventRepository.findById(slug);
		if(!eventOptional.isPresent()) {
			throw new Exception("invalid event id");
		}
		Event event = eventOptional.get();
		List<UserProfile> userProfiles = this.userRepository.findAllById(userIds);
		if(userProfiles.size()!=userIds.size()) {
			throw new Exception("invalid user ids");
		}
		Set<UserProfile> userProfilesSet = new HashSet<UserProfile>(userProfiles);
		if(event.getUserProfiles()==null) {
			event.setUserProfiles(new HashSet<UserProfile>());
		}
		event.getUserProfiles().addAll(userProfilesSet);
		result.put("event",this.eventRepository.save(event));
		return result;
	}

	@Override
	public Map<String, Object> deleteById(String slug,Corporate corporate,University university) throws Exception{
		Map<String,Object> result = new HashMap();
		Optional<Event> eventOptional = this.eventRepository.findById(slug);
		if(!eventOptional.isPresent()) {
			throw new Exception("invalid id");
		}
		Event event = eventOptional.get();
		if(corporate!=null) {
			if(!event.getCorporate().equals(corporate)) {
				throw new Exception("invalid request");
			}
			
		}
		else if(university!=null) {
			if(!event.getUniversity().equals(university)) {
				throw new Exception("invalid request");
			}
		}
		else {
			if(event.getUniversity()!=null&&event.getCorporate()!=null) {
				throw new Exception("invalid request");
			}
			
		}
		event.setUserProfiles(null);
		event= this.eventRepository.save(event);
		this.eventRepository.deleteById(slug);
		return result;
	}
	
	
}
