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
@Table(name = "STUDENT_EDU_DETAILS")
public class StudentEducationDetails implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull
	@Column(name = "STUDENT_EDU_DET_ID")
	private Long eduDetailsId;
	
	@Column(name = "USER_ID")
	private Long userId;
	
	@Column(name = "EXAMINATION")
	private String examination;
	
	@Column(name = "INSTITUTE_NAME")
	private String instituteName;
	
	@Column(name = "ADDRESS")
	private String address;
	
	@Column(name = "YEAR_OF_PASSING")
	private Integer yearOfPassing;
	
	@Column(name = "MARKS")
	private Double marks;
	
	@Column(name = "ADDITIONAL_INFO")
	private String additionalInfo;
	
	@Column(name = "DELETE_STATUS")
	private String deleteStatus;
	
	@CreationTimestamp
	@Column(name = "CREATED_ON")
	private Timestamp createdOn;
	
	@Column(name = "UPDATED_ON")
	private Timestamp updatedOn;

	public Long getEduDetailsId() {
		return eduDetailsId;
	}

	public void setEduDetailsId(Long eduDetailsId) {
		this.eduDetailsId = eduDetailsId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getExamination() {
		return examination;
	}

	public void setExamination(String examination) {
		this.examination = examination;
	}

	public String getInstituteName() {
		return instituteName;
	}

	public void setInstituteName(String instituteName) {
		this.instituteName = instituteName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getYearOfPassing() {
		return yearOfPassing;
	}

	public void setYearOfPassing(Integer yearOfPassing) {
		this.yearOfPassing = yearOfPassing;
	}

	public Double getMarks() {
		return marks;
	}

	public void setMarks(Double marks) {
		this.marks = marks;
	}

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
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
		return "StudentEducationDetails [eduDetailsId=" + eduDetailsId + ", userId=" + userId + ", examination="
				+ examination + ", instituteName=" + instituteName + ", address=" + address + ", yearOfPassing="
				+ yearOfPassing + ", marks=" + marks + ", additionalInfo=" + additionalInfo + ", deleteStatus="
				+ deleteStatus + ", createdOn=" + createdOn + ", updatedOn=" + updatedOn + "]";
	}
}
