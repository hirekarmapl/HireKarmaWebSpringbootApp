package com.hirekarma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hirekarma.model.MentorWeeklyCalendar;
@Repository
public interface MentorWeeklyCalendarRepository extends JpaRepository<MentorWeeklyCalendar, String> {

}
