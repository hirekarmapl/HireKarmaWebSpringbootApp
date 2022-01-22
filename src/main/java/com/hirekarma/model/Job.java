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

@Entity
@Table(name = "JOB")
public class Job implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull
	@Column(name = "JOB_ID")
	private Long jobId;
	
	@Column(name = "USER_ID")
	private Long userId;
	
	@Column(name = "JOB_TITLE")
	private String jobTitle;
	
	@Column(name = "CATEGORY")
	private String category;
	
	@Column(name = "JOBT_YPE")
	private String jobType;
	
	@Column(name = "WFH_CHECK_BOX")
	private Boolean wfhCheckbox;
	
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
	
	@Lob
	@Column(name = "DESCRIPTION_FILE")
	private byte[] descriptionFile;
	
	@Column(name = "STATUS")
	private boolean status;
	
	@Column(name = "DELETE_STATUS")
	private String deleteStatus;
	
	@CreationTimestamp
	@Column(name = "CREATED_ON")
	private Timestamp createdOn;
	
	@Column(name = "UPDATED_ON")
	private Timestamp updatedOn;

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	public Boolean getWfhCheckbox() {
		return wfhCheckbox;
	}

	public void setWfhCheckbox(Boolean wfhCheckbox) {
		this.wfhCheckbox = wfhCheckbox;
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

	public byte[] getDescriptionFile() {
		return descriptionFile;
	}

	public void setDescriptionFile(byte[] descriptionFile) {
		this.descriptionFile = descriptionFile;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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
		return "Job [jobId=" + jobId + ", userId=" + userId + ", jobTitle=" + jobTitle + ", category=" + category
				+ ", jobType=" + jobType + ", wfhCheckbox=" + wfhCheckbox + ", skills=" + skills + ", city=" + city
				+ ", openings=" + openings + ", salary=" + salary + ", about=" + about + ", description=" + description
				+ ", descriptionFile=" + Arrays.toString(descriptionFile) + ", status=" + status + ", deleteStatus="
				+ deleteStatus + ", createdOn=" + createdOn + ", updatedOn=" + updatedOn + "]";
	}
}
