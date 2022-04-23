package com.hirekarma.beans;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;
import lombok.ToString;
@Data
@ToString
public class WeekAvailabilityResponse {

	LocalDate date;
	Map<String, Boolean> hours = new HashMap<String, Boolean>();
	List<MentorAvailabilityHours> mentorAvailabilityHours;
}
