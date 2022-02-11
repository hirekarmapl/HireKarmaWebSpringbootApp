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
import lombok.ToString;

@Entity
@Table(name = "ADMIN_SHARE_JOB_TO_UNIVERSITY")
public class AdminShareJobToUniversity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull
	@Column(name = "SHARE_JOB_ID")
	private Long shareJobId;
	
	@Column(name = "JOB_ID")
	private Long jobId;
	
	@Column(name = "UNIVERSITY_ID")
	private Long universityId;
	
	@Column(name = "JOB_STATUS")
	private String jobStatus;
	
	@Column(name = "UNIVERSITY_RESPONSE_STATUS")
	private Boolean universityResponseStatus;
	
	@Column(name = "REJECTION_FEEDBACK")
	private String rejectionFeedback;
	
	@CreationTimestamp
	@Column(name = "CREATED_ON")
	private Timestamp createdOn;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "UPDATED_ON")
	private Timestamp updatedOn;
	
	@Column(name = "UPDATED_BY")
	private String updatedBy;

	@Override
	public String toString() {
		return "AdminShareJobToUniversity [shareJobId=" + shareJobId + ", jobId=" + jobId + ", universityId="
				+ universityId + ", jobStatus=" + jobStatus + ", universityResponseStatus=" + universityResponseStatus
				+ ", rejectionFeedback=" + rejectionFeedback + ", createdOn=" + createdOn + ", createdBy=" + createdBy
				+ ", updatedOn=" + updatedOn + ", updatedBy=" + updatedBy + "]";
	}

	public Long getShareJobId() {
		return shareJobId;
	}

	public void setShareJobId(Long shareJobId) {
		this.shareJobId = shareJobId;
	}

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	public Long getUniversityId() {
		return universityId;
	}

	public void setUniversityId(Long universityId) {
		this.universityId = universityId;
	}

	public String getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}

	public Boolean getUniversityResponseStatus() {
		return universityResponseStatus;
	}

	public void setUniversityResponseStatus(Boolean universityResponseStatus) {
		this.universityResponseStatus = universityResponseStatus;
	}

	public String getRejectionFeedback() {
		return rejectionFeedback;
	}

	public void setRejectionFeedback(String rejectionFeedback) {
		this.rejectionFeedback = rejectionFeedback;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Timestamp updatedOn) {
		this.updatedOn = updatedOn;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

	
}
