package com.hirekarma.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hirekarma.model.Mentor;
import com.hirekarma.model.StudentMentorSession;
@Repository
public interface StudentMentorSessionRepository extends JpaRepository<StudentMentorSession, String> {

	@Query("select s from StudentMentorSession s where s.scheduledDate >= :scheduledStartDate and s.scheduledDate<=:scheduledEndDate  and s.mentor=:mentor order by s.scheduledDate,s.startTime")
	List<StudentMentorSession> findBetweenScheduleStartDateAndScheduledEndDate(@Param("scheduledStartDate") LocalDate scheduledStartDate,@Param("scheduledEndDate") LocalDate scheduledEndDate,@Param("mentor") Mentor mentor);
}

