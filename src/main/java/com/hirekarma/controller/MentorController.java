package com.hirekarma.controller;

import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirekarma.beans.MentorsWeekAvailability;
import com.hirekarma.beans.Response;
import com.hirekarma.model.Corporate;
import com.hirekarma.repository.CorporateRepository;
import com.hirekarma.service.MentorService;
import com.hirekarma.utilty.Validation;

@RestController
@CrossOrigin
@RequestMapping("/hirekarma/")
public class MentorController {

	@Autowired
	CorporateRepository corporateRepository;

	@Autowired
	MentorService mentorService;

	@PostMapping("/mentor/weekSchedule")
	public ResponseEntity<Response> addWeekSchedule(@RequestBody MentorsWeekAvailability mentorsWeekAvailability,
			@RequestHeader("Authorization") String token) {
		try {

			Corporate corporate = this.corporateRepository.findByEmail(Validation.validateToken(token));

			return new ResponseEntity(
					new Response("success", 200, "",
							this.mentorService.addWeekAvailablility(mentorsWeekAvailability, corporate), null),
					HttpStatus.OK);
		} catch (DateTimeParseException dtpe) {
			return new ResponseEntity(new Response("error", 422, "invalid input", null, null),
					HttpStatus.UNPROCESSABLE_ENTITY);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(new Response("error", 500, e.getMessage(), null, null), HttpStatus.BAD_REQUEST);
		}

	}
	
	@GetMapping("/mentor/weekSchedule")
	public ResponseEntity<Response> getWeekScheduleForCorporate(@RequestHeader("Authorization") String token) {
		try {

			Corporate corporate = this.corporateRepository.findByEmail(Validation.validateToken(token));
			Map<String,Object> response = new HashMap<String, Object>();
			response.put("weeekSchedule", corporate.getMentor().getMentorWeeklyCalendar());
			return new ResponseEntity(
					new Response("success", 200, "",response, null),
					HttpStatus.OK);
		} catch (DateTimeParseException dtpe) {
			return new ResponseEntity(new Response("error", 422, "invalid input", null, null),
					HttpStatus.UNPROCESSABLE_ENTITY);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(new Response("error", 500, e.getMessage(), null, null), HttpStatus.BAD_REQUEST);
		}
	}
	
}
