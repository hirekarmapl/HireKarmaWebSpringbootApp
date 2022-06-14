package com.hirekarma.controller;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.hirekarma.beans.EventBean;
import com.hirekarma.beans.Response;
import com.hirekarma.beans.StudentMentorBooking;
import com.hirekarma.model.Corporate;
import com.hirekarma.model.Event;
import com.hirekarma.model.Student;
import com.hirekarma.model.University;
import com.hirekarma.model.UserProfile;
import com.hirekarma.repository.UserRepository;
import com.hirekarma.service.EventService;
import com.hirekarma.utilty.Validation;
@RestController
public class EventController {

	@Autowired
	EventService eventService;
	
	@Autowired
	UserRepository userRepository;
	@GetMapping("/bunnyEvent")
	public EventBean buny(){
		return new EventBean();
	}
	@PostMapping("/hirekarma/event")
	public ResponseEntity<Response> createEvent(@RequestBody EventBean eventBean,@RequestHeader("Authorization") String token){
	Map<String,Object> map = new HashMap();
		try {
			String email = Validation.validateToken(token);
			List<Object[]> userData = this.userRepository.findUserAndAssociatedEntity(email);
			UserProfile userProfile = (UserProfile) userData.get(0)[0];
			Corporate corporate  = (Corporate) userData.get(0)[1];
			University university  = (University) userData.get(0)[2];
			Student student = (Student) userData.get(0)[3];
			if(userProfile.getUserType().equals("university")) {
				eventBean.setUniversity(university);
				
			}
			else if(userProfile.getUserType().equals("corporate")){
				eventBean.setCorporate(corporate);
			}
			eventBean.setUserProfile(userProfile);
			map = eventService.createEvent(eventBean);
			
			return new ResponseEntity(
					new Response("success", 201, "",map, null),HttpStatus.CREATED);
		} catch (DateTimeParseException dtpe) {
			return new ResponseEntity(new Response("error", 422, "invalid input", null, null),
					HttpStatus.UNPROCESSABLE_ENTITY);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(new Response("error", 400, e.getMessage(), null, null), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/hirekarma/event/{slug}")
	public ResponseEntity<Response> updateEvent(@RequestBody EventBean eventBean,@RequestHeader("Authorization") String token,@PathVariable("slug")String slug){
	Map<String,Object> map = new HashMap();
		try {
			String email = Validation.validateToken(token);
			List<Object[]> userData = this.userRepository.findUserAndAssociatedEntity(email);
			UserProfile userProfile = (UserProfile) userData.get(0)[0];
			Corporate corporate  = (Corporate) userData.get(0)[1];
			University university  = (University) userData.get(0)[2];
			Student student = (Student) userData.get(0)[3];
			if(userProfile.getUserType().equals("university")) {
				eventBean.setUniversity(university);
				
			}
			else if(userProfile.getUserType().equals("corporate")){
				eventBean.setCorporate(corporate);
			}
			eventBean.setUserProfile(userProfile);
			map = eventService.updateEvent(eventBean, slug, corporate, university);
			
			return new ResponseEntity(
					new Response("success", 200, "updated successfully",map, null),HttpStatus.OK);
		} catch (DateTimeParseException dtpe) {
			return new ResponseEntity(new Response("error", 422, "invalid input", null, null),
					HttpStatus.UNPROCESSABLE_ENTITY);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(new Response("error", 400, e.getMessage(), null, null), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/hirekarma/events")
	public ResponseEntity<Response> getEvents(@RequestHeader("Authorization") String token){
	Map<String,Object> map = new HashMap();
		try {
			String email = Validation.validateToken(token);
			List<Object[]> userData = this.userRepository.findUserAndAssociatedEntity(email);
			UserProfile userProfile = (UserProfile) userData.get(0)[0];
			Corporate corporate  = (Corporate) userData.get(0)[1];
			University university  = (University) userData.get(0)[2];
			Student student = (Student) userData.get(0)[3];
			
			
			return new ResponseEntity(
					new Response("success", 201, "",this.eventService.getAllEvents(corporate, university), null),HttpStatus.OK);
		} catch (DateTimeParseException dtpe) {
			return new ResponseEntity(new Response("error", 422, "invalid input", null, null),
					HttpStatus.UNPROCESSABLE_ENTITY);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(new Response("error", 400, e.getMessage(), null, null), HttpStatus.BAD_REQUEST);
		}
	}
	@DeleteMapping("/hirekarma/event/{slug}")
	public ResponseEntity<Response> updateEvent(@RequestHeader("Authorization") String token,@PathVariable("slug")String slug){
		Map<String,Object> map = new HashMap();
			try {
				String email = Validation.validateToken(token);
				List<Object[]> userData = this.userRepository.findUserAndAssociatedEntity(email);
				UserProfile userProfile = (UserProfile) userData.get(0)[0];
				Corporate corporate  = (Corporate) userData.get(0)[1];
				University university  = (University) userData.get(0)[2];
				Student student = (Student) userData.get(0)[3];
				
				map = eventService.deleteById(slug, corporate, university);
				
				return new ResponseEntity(
						new Response("success", 200, "deleted successfully",map, null),HttpStatus.OK);
			} catch (DateTimeParseException dtpe) {
				return new ResponseEntity(new Response("error", 422, "invalid input", null, null),
						HttpStatus.UNPROCESSABLE_ENTITY);
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity(new Response("error", 400, e.getMessage(), null, null), HttpStatus.BAD_REQUEST);
			}
		}
	
	@GetMapping("/hirekarma/public/events")
	public ResponseEntity<Response> getPublicEvents(){
	Map<String,Object> map = new HashMap();
		try {
			
			
			
			return new ResponseEntity(
					new Response("success", 201, "",this.eventService.getAllEventsForPublic(), null),HttpStatus.OK);
		} catch (DateTimeParseException dtpe) {
			return new ResponseEntity(new Response("error", 422, "invalid input", null, null),
					HttpStatus.UNPROCESSABLE_ENTITY);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(new Response("error", 400, e.getMessage(), null, null), HttpStatus.BAD_REQUEST);
		}
	}
//	@PostMapping("/hirekarma/createEvent")
//	public ResponseEntity<Response> studentBookASlot(@RequestBody EventBean eventBean,@RequestHeader("Authorization") String token){
//	Map<String,Object> map = new HashMap();
//		try {
//			String email = Validation.validateToken(token);
//			List<Object[]> userData = this.userRepository.findUserAndAssociatedEntity(email);
//			UserProfile userProfile = (UserProfile) userData.get(0)[0];
//			Corporate corporate  = (Corporate) userData.get(0)[1];
//			University university  = (University) userData.get(0)[2];
//			Student student = (Student) userData.get(0)[3];
//			if(userProfile.getUserType().equals("university")) {
//				screeningBean.setUniversityId(university.getUniversityId());
//				map = screeningService.createScreeningQuestion(screeningBean);
//			}
//			else if(userProfile.getUserType().equals("admin")) {
//				
//				map = screeningService.createScreeningQuestion(screeningBean);
//			}
//			else if(userProfile.getUserType().equals("corporate")){
//				screeningBean.setCorporateId(corporate.getCorporateId());
//				map = screeningService.createScreeningQuestion(screeningBean);
//			}
//		
//			
//			return new ResponseEntity(
//					new Response("success", 201, "",map, null),HttpStatus.CREATED),HttpStatus.OK);
//		} catch (DateTimeParseException dtpe) {
//			return new ResponseEntity(new Response("error", 422, "invalid input", null, null),
//					HttpStatus.UNPROCESSABLE_ENTITY);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return new ResponseEntity(new Response("error", 400, e.getMessage(), null, null), HttpStatus.BAD_REQUEST);
//		}
//	}
}
