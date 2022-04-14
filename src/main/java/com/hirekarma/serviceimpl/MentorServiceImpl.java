package com.hirekarma.serviceimpl;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirekarma.beans.MentorsWeekAvailability;
import com.hirekarma.model.Corporate;
import com.hirekarma.model.Mentor;
import com.hirekarma.model.MentorWeekDayCalendar;
import com.hirekarma.model.Week;
import com.hirekarma.repository.MentorWeekDayCalendarRepository;
import com.hirekarma.service.MentorService;
@Service("MentorService")
public class MentorServiceImpl implements MentorService {
	
	@Autowired
	MentorWeekDayCalendarRepository mentorWeekDayCalendarRepository;

	public List<MentorWeekDayCalendar> updateWeekSchedule(List<MentorWeekDayCalendar> weekDayCalendars,Map<String,String> request){
		
		for(MentorWeekDayCalendar mentorWeekDayCalendar : weekDayCalendars){
			System.out.println(mentorWeekDayCalendar.getWeek().name());
			System.out.println(request.get(mentorWeekDayCalendar.getWeek().name()));
			String startAndEndTimings = request.get(mentorWeekDayCalendar.getWeek().name());
			if(startAndEndTimings!=null) {
				String timings[] =	request.get(mentorWeekDayCalendar.getWeek().name()).split("-");
				
				if(timings.length==2) {
					System.out.println(timings[0]+" "+timings[1]);
					mentorWeekDayCalendar.setStartTime(LocalTime.parse(timings[0].trim()));
					mentorWeekDayCalendar.setEndtime(LocalTime.parse(timings[1].trim()));
					mentorWeekDayCalendar.setAvailabe(true);
					mentorWeekDayCalendar.setUpdatedAt(LocalDateTime.now());
				}
			}
			
		}
		
		return weekDayCalendars;
	}

	@Override
	public Map<String, Object> addWeekAvailablility(MentorsWeekAvailability mentorsWeekAvailability,
			Corporate corporate) {
		Map<String,Object> response = new HashMap<String, Object>();
		List<MentorWeekDayCalendar> weekDayCalendars = corporate.getMentor().getMentorWeeklyCalendar().getMentorWeekDayCalendars();
		weekDayCalendars = updateWeekSchedule(weekDayCalendars,mentorsWeekAvailability.getWeekSchedule());
		weekDayCalendars = this.mentorWeekDayCalendarRepository.saveAll(weekDayCalendars);
		response.put("weekAvailablility", weekDayCalendars);
		return response;
	}

	

}
