package com.hirekarma.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;

@Entity
@Table(name="SCREENING_RESPONSE")
public class ScreeningResponse implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "SCREENING_RESPONSE_ID")
	@NotNull
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ScreeningResponseId;
	
	@Column(name = "JOB_APPLY_ID")
	private Long jobApplyId;
	
	@ManyToOne
	private ScreeningEntity screeningEntity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	private Message message;
	
	@Column(name = "USER_RESPONSE")
	private String userResponse;
	
	@Column(nullable = false)
	private LocalDateTime createdOn = LocalDateTime.now();

	public Long getScreeningResponseId() {
		return ScreeningResponseId;
	}

	public void setScreeningResponseId(Long screeningResponseId) {
		ScreeningResponseId = screeningResponseId;
	}

	public Long getJobApplyId() {
		return jobApplyId;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public void setJobApplyId(Long jobApplyId) {
		this.jobApplyId = jobApplyId;
	}



	public String getUserResponse() {
		return userResponse;
	}

	public void setUserResponse(String userResponse) {
		this.userResponse = userResponse;
	}
	


	public ScreeningEntity getScreeningEntity() {
		return screeningEntity;
	}

	public void setScreeningEntity(ScreeningEntity screeningEntity) {
		this.screeningEntity = screeningEntity;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}


}
