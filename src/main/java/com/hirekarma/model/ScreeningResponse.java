package com.hirekarma.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
	
	@Column(name = "SCREENING_ID")
	private Long screeningId;
	
	@Column(name = "USER_RESPONSE")
	private String userResponse;

	public Long getScreeningResponseId() {
		return ScreeningResponseId;
	}

	public void setScreeningResponseId(Long screeningResponseId) {
		ScreeningResponseId = screeningResponseId;
	}

	public Long getJobApplyId() {
		return jobApplyId;
	}

	public void setJobApplyId(Long jobApplyId) {
		this.jobApplyId = jobApplyId;
	}

	public Long getScreeningId() {
		return screeningId;
	}

	public void setScreeningId(Long screeningId) {
		this.screeningId = screeningId;
	}

	public String getUserResponse() {
		return userResponse;
	}

	public void setUserResponse(String userResponse) {
		this.userResponse = userResponse;
	}

	@Override
	public String toString() {
		return "ScreeningResponse [ScreeningResponseId=" + ScreeningResponseId + ", jobApplyId=" + jobApplyId
				+ ", screeningId=" + screeningId + ", userResponse=" + userResponse + "]";
	}

}
