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
@Table(name="INTERNSHIP_APPLY")
//@Data
//@ToString
public class InternshipApply implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull
	@Column(name = "INTERNSHIP_APPLY_ID")
	private Long internshipApplyId;
	
	@Column(name = "STUDENT_ID")
	private Long studentId;
	
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
	
	@Column(name = "APPLICATION_STATUS")
	private Boolean applicatinStatus;
	
	@CreationTimestamp
	@Column(name = "CREATED_ON")
	private Timestamp createdOn;
	
	@Column(name = "UPDATED_ON")
	private Timestamp updatedOn;
	
	private Long internshipId;
	

	public Long getInternshipId() {
		return internshipId;
	}

	public void setInternshipId(Long internshipId) {
		this.internshipId = internshipId;
	}

	@Override
	public String toString() {
		return "InternshipApply [internshipApplyId=" + internshipApplyId + ", studentId=" + studentId + ", corporateId="
				+ corporateId + ", hireReason=" + hireReason + ", coverLetter=" + coverLetter + ", earliestJoiningDate="
				+ earliestJoiningDate + ", deleteStatus=" + deleteStatus + ", applicatinStatus=" + applicatinStatus
				+ ", createdOn=" + createdOn + ", updatedOn=" + updatedOn + "]";
	}

	public Long getInternshipApplyId() {
		return internshipApplyId;
	}

	public void setInternshipApplyId(Long internshipApplyId) {
		this.internshipApplyId = internshipApplyId;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
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

	public Boolean getApplicatinStatus() {
		return applicatinStatus;
	}

	public void setApplicatinStatus(Boolean applicatinStatus) {
		this.applicatinStatus = applicatinStatus;
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
