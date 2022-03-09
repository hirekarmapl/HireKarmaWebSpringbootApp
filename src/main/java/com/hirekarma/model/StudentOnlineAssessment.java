package com.hirekarma.model;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity
public class StudentOnlineAssessment {

	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(columnDefinition = "CHAR(32)")
	@Id
	String slug;
	
	@ManyToOne(cascade = CascadeType.ALL)

	Student student;
	
	@ManyToOne(cascade = CascadeType.ALL)

	OnlineAssessment onlineAssessment;
	
	LocalDateTime createdOn = LocalDateTime.now();
	
	LocalDateTime startedOn;
	
	Boolean studentResponse = false;

	
	
	
	
	
	
	
	
	
	
	
	
//	------------------------ getters and setters----------------------
	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public OnlineAssessment getOnlineAssessment() {
		return onlineAssessment;
	}

	public void setOnlineAssessment(OnlineAssessment onlineAssessment) {
		this.onlineAssessment = onlineAssessment;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}

	public LocalDateTime getStartedOn() {
		return startedOn;
	}

	public void setStartedOn(LocalDateTime startedOn) {
		this.startedOn = startedOn;
	}

	public Boolean getStudentResponse() {
		return studentResponse;
	}

	public void setStudentResponse(Boolean studentResponse) {
		this.studentResponse = studentResponse;
	}
	
	
}
