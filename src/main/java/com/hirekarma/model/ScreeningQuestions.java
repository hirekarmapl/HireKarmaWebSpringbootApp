package com.hirekarma.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.sun.istack.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

//@Data
//@NoArgsConstructor
@Entity
@Table(name = "SCREENING_QUESTIONS")
public class ScreeningQuestions implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@NotNull
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "QUESTIONS_ID")
	private Long questionsId;
	
	@Column(name = "SCREENING_ID")
	private Long screeningId;
	
	@Column(name = "QUESTIONS")
	private String questions;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "CREATED_ON")
	@CreationTimestamp
	private Timestamp createdOn;
	
	@Column(name = "UPDATED_ON")
	private Timestamp updatedOn;

	@Override
	public String toString() {
		return "ScreeningQuestions [questionsId=" + questionsId + ", screeningId=" + screeningId + ", questions="
				+ questions + ", status=" + status + ", createdOn=" + createdOn + ", updatedOn=" + updatedOn + "]";
	}

	public ScreeningQuestions() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getQuestionsId() {
		return questionsId;
	}

	public void setQuestionsId(Long questionsId) {
		this.questionsId = questionsId;
	}

	public Long getScreeningId() {
		return screeningId;
	}

	public void setScreeningId(Long screeningId) {
		this.screeningId = screeningId;
	}

	public String getQuestions() {
		return questions;
	}

	public void setQuestions(String questions) {
		this.questions = questions;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public Timestamp getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Timestamp updatedOn) {
		this.updatedOn = updatedOn;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	
}
