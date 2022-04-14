package com.hirekarma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hirekarma.model.MentorWeekDayCalendar;
@Repository
public interface MentorWeekDayCalendarRepository extends JpaRepository<MentorWeekDayCalendar, String> {

}
