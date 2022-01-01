package com.hirekarma.beans;

import java.sql.Timestamp;

public class JobApplyBean {
	
	private Long jobApplyId;
	private Long userId;
	private String hireReason;
	private String coverLetter;
	private String earliestJoiningDate;
	private String deleteStatus;
	private Timestamp createdOn;
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
		return "JobApplyBean [jobApplyId=" + jobApplyId + ", userId=" + userId + ", hireReason=" + hireReason
				+ ", coverLetter=" + coverLetter + ", earliestJoiningDate=" + earliestJoiningDate + ", deleteStatus="
				+ deleteStatus + ", createdOn=" + createdOn + ", updatedOn=" + updatedOn + "]";
	}
}
