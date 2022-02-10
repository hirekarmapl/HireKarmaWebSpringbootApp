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
@Table(name = "JOB_APPLY")
//@Data
//@ToString
public class JobApply implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull
	@Column(name = "JOB_APPLY_ID")
	private Long jobApplyId;

	@Column(name = "STUDENT_ID")
	private Long studentId;
	
	@Column(name = "JOB_ID")
	private Long jobId;

	@Column(name = "CORPORATE_ID")
	private Long corporateId;
	
	@Column(name = "HIRE_REASON")
	private String hireReason;

	@Column(name = "COVER_LETTER")
	private String coverLetter;

	@Column(name = "EARLIEST_JOINING_DATE")
	private String earliestJoiningDate;

	@Column(name = "DELETE_STATUS")
	private Boolean deleteStatus;

	@CreationTimestamp
	@Column(name = "CREATED_ON")
	private Timestamp createdOn;

	@Column(name = "UPDATED_ON")
	private Timestamp updatedOn;

	@Column(name = "JOB_APPLICATION_STATUS")
	public Boolean applicationStatus;

	@Override
	public String toString() {
		return "JobApply [jobApplyId=" + jobApplyId + ", studentId=" + studentId + ", jobId=" + jobId + ", corporateId="
				+ corporateId + ", hireReason=" + hireReason + ", coverLetter=" + coverLetter + ", earliestJoiningDate="
				+ earliestJoiningDate + ", deleteStatus=" + deleteStatus + ", createdOn=" + createdOn + ", updatedOn="
				+ updatedOn + ", applicationStatus=" + applicationStatus + "]";
	}

	public Long getJobApplyId() {
		return jobApplyId;
	}

	public void setJobApplyId(Long jobApplyId) {
		this.jobApplyId = jobApplyId;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
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

	public Boolean getDeleteStatus() {
		return deleteStatus;
	}

	public void setDeleteStatus(Boolean deleteStatus) {
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

	public Boolean getApplicationStatus() {
		return applicationStatus;
	}

	public void setApplicationStatus(Boolean applicationStatus) {
		this.applicationStatus = applicationStatus;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
