package com.hirekarma.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

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
	
	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)

	OnlineAssessment onlineAssessment;
	
	LocalDateTime createdOn = LocalDateTime.now();
	
	LocalDateTime startedOn;
	
	Boolean studentResponse = false;
	
	Integer totalMarksObtained;

	@OneToMany(mappedBy = "studentOnlineAssessment",fetch = FetchType.LAZY)
	List<StudentOnlineAssessmentAnswer> studentOnlineAssessmentAnswers;
	
	
	
	
	
	
	
	
	
	
	
//	------------------------ getters and setters----------------------
	
	

	public String getSlug() {
		return slug;
	}

	public List<StudentOnlineAssessmentAnswer> getStudentOnlineAssessmentAnswers() {
		return studentOnlineAssessmentAnswers;
	}

	public void setStudentOnlineAssessmentAnswers(List<StudentOnlineAssessmentAnswer> studentOnlineAssessmentAnswers) {
		this.studentOnlineAssessmentAnswers = studentOnlineAssessmentAnswers;
	}

	public Integer getTotalMarksObtained() {
		return totalMarksObtained;
	}

	public void setTotalMarksObtained(Integer totalMarksObtained) {
		this.totalMarksObtained = totalMarksObtained;
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
