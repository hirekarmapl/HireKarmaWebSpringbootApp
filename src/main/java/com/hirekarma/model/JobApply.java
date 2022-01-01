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

@Entity
@Table(name="JOB_APPLY")
public class JobApply implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull
	@Column(name = "JOB_APPLY_ID")
	private Long jobApplyId;
	
	@Column(name = "USER_ID")
	private Long userId;
	
	@Column(name = "HIRE_REASON")
	private String hireReason;
	
	@Column(name = "COVER_LETTER")
	private String coverLetter;
	
	@Column(name = "EARLIEST_JOINING_DATE")
	private String earliestJoiningDate;
	
	@Column(name = "DELETE_STATUS")
	private String deleteStatus;
	
	@CreationTimestamp
	@Column(name = "CREATED_ON")
	private Timestamp createdOn;
	
	@Column(name = "UPDATED_ON")
	private Timestamp updatedOn;

	public Long getJobApplyId() {
		return jobApplyId;
	}

	public void setJobApplyId(Long jobApplyId) {
		this.jobApplyId = jobApplyId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getHireReason() {
		return hireReason;
	}

	public void setHireReason(String hireReason) {
		this.hireReason = hireReason;
	}

	public String getCoverLetter() {
		return coverLetter;
	}

	public void setCoverLetter(String coverLetter) {
		this.coverLetter = coverLetter;
	}

	public String getEarliestJoiningDate() {
		return earliestJoiningDate;
	}

	public void setEarliestJoiningDate(String earliestJoiningDate) {
		this.earliestJoiningDate = earliestJoiningDate;
	}

	public String getDeleteStatus() {
		return deleteStatus;
	}

	public void setDeleteStatus(String deleteStatus) {
		this.deleteStatus = deleteStatus;
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

	@Override
	public String toString() {
		return "JobApply [jobApplyId=" + jobApplyId + ", userId=" + userId + ", hireReason=" + hireReason
				+ ", coverLetter=" + coverLetter + ", earliestJoiningDate=" + earliestJoiningDate + ", deleteStatus="
				+ deleteStatus + ", createdOn=" + createdOn + ", updatedOn=" + updatedOn + "]";
	}
}
