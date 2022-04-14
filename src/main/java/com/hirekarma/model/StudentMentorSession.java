package com.hirekarma.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import lombok.ToString;

@Entity
@ToString
@Data
public class StudentMentorSession {
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(columnDefinition = "CHAR(32)")
	@Id
	String id;
	
	LocalDate scheduledDate;
	
	@ManyToOne
	Student student;
	@ManyToOne
	Mentor mentor;
	
	@Enumerated(EnumType.STRING)
	Week scheduledDay;
	@Enumerated(EnumType.STRING)
	MeetUpReason reason;
	LocalTime startTime;
	LocalTime endTime;
	LocalDateTime createdAt  = LocalDateTime.now();
	
	
	
}
