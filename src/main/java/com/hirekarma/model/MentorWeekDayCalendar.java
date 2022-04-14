package com.hirekarma.model;

import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.ToString;
@Data
@ToString
@Entity
public class MentorWeekDayCalendar {

	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(columnDefinition = "CHAR(32)")
	@Id
	String id;
	
	@ManyToOne
	@JsonIgnore
	MentorWeeklyCalendar mentorWeeklyCalendar;
	
	@Enumerated(EnumType.STRING)
	Week week;
	
	LocalTime startTime;
	
	LocalTime endtime;
	
	boolean availabe;
	
	LocalDateTime createdAt;
	
	LocalDateTime updatedAt;
	
	
}
