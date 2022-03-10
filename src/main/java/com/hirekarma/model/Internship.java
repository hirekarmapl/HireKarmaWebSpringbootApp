package com.hirekarma.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.sun.istack.NotNull;

import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "INTERNSHIP")
//@Data
//@ToString
public class Internship implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull
	@Column(name = "INTERNSHIP_ID")
	private Long internshipId;
	
	@Column(name = "CORPORATE_ID")
	private Long corporateId;
	
	@Column(name = "INTERNSHIP_TITLE")
	private String internshipTitle;
	
	@Column(name = "INTERNSHIP_TYPE")
	private String internshipType;
	
	@Column(name = "SKILLS")
	private String skills;
	
	@Column(name = "CITY")
	private String city;
	
	@Column(name = "OPENINGS")
	private Integer openings;
	
	@Column(name = "SALARY")
	private Double salary;
	
	@Column(name = "ABOUT")
	private String about;
	
	@Column(name = "DESCRIPTION")
	private String description;
	
	private String descriptionFileUrl;
	
	@Column(name = "STATUS")
	private Boolean status;
	
	@Column(name = "DELETE_STATUS")
	private Boolean deleteStatus;
	
	@CreationTimestamp
	@Column(name = "CREATED_ON")
	private Timestamp createdOn;
	
	@Column(name = "UPDATED_ON")
	private Timestamp updatedOn;



	@Override
	public String toString() {
		return "Internship [internshipId=" + internshipId + ", corporateId=" + corporateId + ", internshipTitle="
				+ internshipTitle + ", internshipType=" + internshipType + ", skills=" + skills + ", city=" + city
				+ ", openings=" + openings + ", salary=" + salary + ", about=" + about + ", description=" + description
				+ ", descriptionFileUrl=" + descriptionFileUrl + ", status=" + status + ", deleteStatus=" + deleteStatus
				+ ", createdOn=" + createdOn + ", updatedOn=" + updatedOn + "]";
	}

	public String getDescriptionFileUrl() {
		return descriptionFileUrl;
	}

	public void setDescriptionFileUrl(String descriptionFileUrl) {
		this.descriptionFileUrl = descriptionFileUrl;
	}

	public Long getInternshipId() {
		return internshipId;
	}

	public void setInternshipId(Long internshipId) {
		this.internshipId = internshipId;
	}

	public Long getCorporateId() {
		return corporateId;
	}

	public void setCorporateId(Long corporateId) {
		this.corporateId = corporateId;
	}

	public String getInternshipTitle() {
		return internshipTitle;
	}

	public void setInternshipTitle(String internshipTitle) {
		this.internshipTitle = internshipTitle;
	}

	public String getInternshipType() {
		return internshipType;
	}

	public void setInternshipType(String internshipType) {
		this.internshipType = internshipType;
	}

	public String getSkills() {
		return skills;
	}

	public void setSkills(String skills) {
		this.skills = skills;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Integer getOpenings() {
		return openings;
	}

	public void setOpenings(Integer openings) {
		this.openings = openings;
	}

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	
}
