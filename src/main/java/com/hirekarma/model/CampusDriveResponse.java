package com.hirekarma.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.sun.istack.NotNull;

@Entity
@Table(name = "CAMPUS_DRIVE_RESPONSE")
public class CampusDriveResponse {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CAMPUS_DRIVE_ID")
	@NotNull
	private Long campusDriveId;

	@Column(name = "JOB_ID")
	private Long jobId;

	@Column(name = "CORPORATE_ID")
	private Long corporateId;

	@Column(name = "UNIVERSITY_ID")
	private Long universityId;

	@Column(name = "UNIVERSITY_ASK_FOR_CAMPUS")
	private Boolean universityAsk;

	@Column(name = "CORPORATE_RESPONSE")
	private Boolean corporateResponse;

	@Column(name = "UNIVERSITY_ASKED_ON")
	@CreationTimestamp
	private Timestamp universityAskedOn;

	@Column(name = "CORPORATE_RESPONSE_ON")
	private Timestamp corporateResponseOn;

	@Override
	public String toString() {
		return "CampusDriveResponse [campusDriveId=" + campusDriveId + ", jobId=" + jobId + ", corporateId="
				+ corporateId + ", universityId=" + universityId + ", universityAsk=" + universityAsk
				+ ", corporateResponse=" + corporateResponse + ", universityAskedOn=" + universityAskedOn
				+ ", corporateResponseOn=" + corporateResponseOn + "]";
	}

	public Long getCampusDriveId() {
		return campusDriveId;
	}

	public void setCampusDriveId(Long campusDriveId) {
		this.campusDriveId = campusDriveId;
	}

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	public Long getCorporateId() {
		return corporateId;
	}

	public void setCorporateId(Long corporateId) {
		this.corporateId = corporateId;
	}

	public Long getUniversityId() {
		return universityId;
	}

	public void setUniversityId(Long universityId) {
		this.universityId = universityId;
	}

	public Boolean getUniversityAsk() {
		return universityAsk;
	}

	public void setUniversityAsk(Boolean universityAsk) {
		this.universityAsk = universityAsk;
	}

	public Boolean getCorporateResponse() {
		return corporateResponse;
	}

	public void setCorporateResponse(Boolean corporateResponse) {
		this.corporateResponse = corporateResponse;
	}

	public Timestamp getUniversityAskedOn() {
		return universityAskedOn;
	}

	public void setUniversityAskedOn(Timestamp universityAskedOn) {
		this.universityAskedOn = universityAskedOn;
	}

	public Timestamp getCorporateResponseOn() {
		return corporateResponseOn;
	}

	public void setCorporateResponseOn(Timestamp corporateResponseOn) {
		this.corporateResponseOn = corporateResponseOn;
	}
	
	
	

}
