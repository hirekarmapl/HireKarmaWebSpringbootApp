package com.hirekarma.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.sun.istack.NotNull;

import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "UNIVERSITY_JOB_SHARE_TO_STUDENT")
//@Data
//@ToString
public class UniversityJobShareToStudent {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull
	@Column(name = "TABLE_ID")
	private Long iD;

	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "CREATED_ON")
	@CreationTimestamp
	private Timestamp createdOn;

	@Column(name = "JOB_ID")
	private Long jobId;

	@Column(name = "STUDENT_ID")
	private Long studentId;

	@Column(name = "UNIVERSITY_ID")
	private Long universityId;

	@Column(name = "UPDATED_BY")
	private String updatedBy;

	@Column(name = "UPDATED_ON")
	private Timestamp updatedOn;

	@Column(name = "JOB_STATUS")
	private Boolean jobStatus;

	@Column(name = "RESPONSE_FEEDBACK")
	private String feedBack;

	@Column(name = "STUDENT_RESPONSE_STATUS")
	private Boolean studentResponseStatus;
	
	@ManyToOne
	private StudentBatch studentBatch;
	
	@ManyToOne
	private StudentBranch studentBranch;
	
	private Boolean seen = false;
	

	

	public Boolean getSeen() {
		return seen;
	}


	public void setSeen(Boolean seen) {
		this.seen = seen;
	}


	@Override
	public String toString() {
		return "UniversityJobShareToStudent [iD=" + iD + ", createdBy=" + createdBy + ", createdOn=" + createdOn
				+ ", jobId=" + jobId + ", studentId=" + studentId + ", universityId=" + universityId + ", updatedBy="
				+ updatedBy + ", updatedOn=" + updatedOn + ", jobStatus=" + jobStatus + ", feedBack=" + feedBack
				+ ", studentResponseStatus=" + studentResponseStatus + "]";
	}

	
	public StudentBatch getStudentBatch() {
		return studentBatch;
	}


	public void setStudentBatch(StudentBatch studentBatch) {
		this.studentBatch = studentBatch;
	}


	public StudentBranch getStudentBranch() {
		return studentBranch;
	}


	public void setStudentBranch(StudentBranch studentBranch) {
		this.studentBranch = studentBranch;
	}


	public Long getiD() {
		return iD;
	}

	public void setiD(Long iD) {
		this.iD = iD;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public Long getUniversityId() {
		return universityId;
	}

	public void setUniversityId(Long universityId) {
		this.universityId = universityId;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Timestamp getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Timestamp updatedOn) {
		this.updatedOn = updatedOn;
	}

	public Boolean getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(Boolean jobStatus) {
		this.jobStatus = jobStatus;
	}

	public String getFeedBack() {
		return feedBack;
	}

	public void setFeedBack(String feedBack) {
		this.feedBack = feedBack;
	}

	public Boolean getStudentResponseStatus() {
		return studentResponseStatus;
	}

	public void setStudentResponseStatus(Boolean studentResponseStatus) {
		this.studentResponseStatus = studentResponseStatus;
	}

	

}
