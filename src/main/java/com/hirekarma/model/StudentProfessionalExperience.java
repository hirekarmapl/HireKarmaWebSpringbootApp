package com.hirekarma.model;

import java.io.Serializable;
import java.sql.Date;
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
@Table(name = "STUDENT_PROF_EXP")
public class StudentProfessionalExperience implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "STUDENT_PROF_EXP_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull
	private Long profExpId;
	
	@Column(name = "USER_ID")
	private Long userId;
	
	@Column(name =  "COMPANY_NAME")
	private String companyName;
	
	@Column(name =  "COMPANY_ADDRESS")
	private String companyAddress;
	
	@Column(name =  "POSITION")
	private String position;
	
	@Column(name =  "START_DATE")
	private Date startDate;
	
	@Column(name = "END_DATE")
	private Date endDate;
	
	@Column(name =  "DESCRIPTION")
	private String description;
	
	@Column(name =  "SKILLS")
	private String skills;
	
	@Column(name = "UPDATED_ON")
	private Timestamp updatedOn;
	
	@Column(name = "CREATED_ON")
	@CreationTimestamp
	private Timestamp createdOn;

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
	

	public String getSkills() {
		return skills;
	}

	public void setSkills(String skills) {
		this.skills = skills;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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

	@Override
	public String toString() {
		return "StudentProfessionalExperience [profExpId=" + profExpId + ", userId=" + userId + ", companyName="
				+ companyName + ", companyAddress=" + companyAddress + ", position=" + position + ", startDate="
				+ startDate + ", endDate=" + endDate + ", description=" + description + ", skills=" + skills
				+ ", updatedOn=" + updatedOn + ", createdOn=" + createdOn + "]";
	}

	
	
	
	
	
	
	
}
