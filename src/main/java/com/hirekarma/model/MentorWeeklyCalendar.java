package com.hirekarma.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString
public class MentorWeeklyCalendar {
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(columnDefinition = "CHAR(32)")
	@Id
	String id;
	
	@OneToOne
	@JsonIgnore
	Mentor mentor;
	
	
	@OneToMany(mappedBy = "mentorWeeklyCalendar")	
	List<MentorWeekDayCalendar> mentorWeekDayCalendars;
	
}
