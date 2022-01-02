package com.hirekarma.beans;

import java.sql.Timestamp;

public class StudentEducationDetailsBean {
	
	private Long eduDetailsId;
	private Long userId;
	private String examination;
	private String instituteName;
	private String address;
	private String yearOfPassing;
	private Double marks;
	private String additionalInfo;
	private String deleteStatus;
	private Timestamp createdOn;
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
	public String getYearOfPassing() {
		return yearOfPassing;
	}
	public void setYearOfPassing(String yearOfPassing) {
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
		return "StudentEducationDetailsBean [eduDetailsId=" + eduDetailsId + ", userId=" + userId + ", examination="
				+ examination + ", instituteName=" + instituteName + ", address=" + address + ", yearOfPassing="
				+ yearOfPassing + ", marks=" + marks + ", additionalInfo=" + additionalInfo + ", deleteStatus="
				+ deleteStatus + ", createdOn=" + createdOn + ", updatedOn=" + updatedOn + "]";
	}
}
