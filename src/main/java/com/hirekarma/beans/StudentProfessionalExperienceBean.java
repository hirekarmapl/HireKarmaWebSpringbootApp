package com.hirekarma.beans;

import java.sql.Date;
import java.sql.Timestamp;

public class StudentProfessionalExperienceBean {

	
    private Long profExpId;
	
	private Long userId;
	
	private String companyName;
	
	private String companyAddress;
	
	private String position;
	
	private Date startDate;
	
	private Date endDate;
	
	private String description;
	
	private Timestamp updatedOn;
	
	private Timestamp createdOn;
	
	private String Response;

	public Long getProfExpId() {
		return profExpId;
	}

	public void setProfExpId(Long profExpId) {
		this.profExpId = profExpId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Timestamp getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Timestamp updatedOn) {
		this.updatedOn = updatedOn;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public String getResponse() {
		return Response;
	}

	public void setResponse(String response) {
		Response = response;
	}

	@Override
	public String toString() {
		return "StudentProfessionalExperienceBean [profExpId=" + profExpId + ", userId=" + userId + ", companyName="
				+ companyName + ", companyAddress=" + companyAddress + ", position=" + position + ", startDate="
				+ startDate + ", endDate=" + endDate + ", description=" + description + ", updatedOn=" + updatedOn
				+ ", createdOn=" + createdOn + ", Response=" + Response + "]";
	}
	
	
	
}
