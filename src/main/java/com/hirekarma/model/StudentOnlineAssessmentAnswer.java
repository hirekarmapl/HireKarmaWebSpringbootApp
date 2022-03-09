package com.hirekarma.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.JsonObject;
@Entity
public class StudentOnlineAssessmentAnswer {
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(columnDefinition = "CHAR(32)")
	@Id
	String slug;
	@ManyToOne
	QuestionANdanswer questionANdanswer;
	
	@ManyToOne
	Student student;
	@JsonIgnore
	@ManyToOne
	OnlineAssessment onlineAssessment;
	
	
	
	@Transient
	JsonObject answer;
	
	String jsonAnswer;
	
	
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public String getJsonAnswer() {
		return jsonAnswer;
	}
	public void setJsonAnswer(String jsonAnswer) {
		this.jsonAnswer = jsonAnswer;
	}
	public String getSlug() {
		return slug;
	}
	public void setSlug(String slug) {
		this.slug = slug;
	}
	public QuestionANdanswer getQuestionANdanswer() {
		return questionANdanswer;
	}
	public void setQuestionANdanswer(QuestionANdanswer questionANdanswer) {
		this.questionANdanswer = questionANdanswer;
	}
	public OnlineAssessment getOnlineAssessment() {
		return onlineAssessment;
	}
	public void setOnlineAssessment(OnlineAssessment onlineAssessment) {
		this.onlineAssessment = onlineAssessment;
	}
	public JsonObject getAnswer() {
		return answer;
	}
	public void setAnswer(JsonObject answer) {
		this.answer = answer;
	}
	
	
}
