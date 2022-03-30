package com.hirekarma.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.ToString;
@Entity
@Data
@ToString
public class Meet {

	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(columnDefinition = "CHAR(32)")
	@Id
	String id;
	
	public String meetLink;
	
	LocalDateTime startTime;
	
	LocalDateTime endTime;
	
	String title;
	
	@OneToOne
	@JsonIgnore
	JobApply jobApply;
	
	@OneToOne
	@JsonIgnore
	UniversityJobShareToStudent universityJobShareToStudent;
	
}
