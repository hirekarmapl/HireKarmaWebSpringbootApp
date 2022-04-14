package com.hirekarma.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
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
	
	@Column(name = "CORPORATE_ID")
	private Long corporateId;
	
	@Column(name = "JOB_TITLE")
	private String jobTitle;
	
	@Column(name = "CATEGORY")
	private String category;
	
	@Column(name = "JOB_TYPE")
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
	
	private String eligibilityCriteria;
	
	private String rolesAndResponsibility;
	
	private Double salaryAtProbation;
	
	private Integer serviceAgreement;
	
	private Boolean forcampusDrive;
	
	private LocalDateTime tentativeDate;
	

	@ManyToMany
	private List<Stream> streams = new ArrayList<Stream>();
	
	@ManyToMany
	private List<StudentBranch> branchs = new ArrayList<StudentBranch>();
	
	public Double getSalaryAtProbation() {
		return salaryAtProbation;
	}

	public LocalDateTime getTentativeDate() {
		return tentativeDate;
	}

	public void setTentativeDate(LocalDateTime tentativeDate) {
		this.tentativeDate = tentativeDate;
	}

	public void setSalaryAtProbation(Double salaryAtProbation) {
		this.salaryAtProbation = salaryAtProbation;
	}

	

	public Boolean getForcampusDrive() {
		return forcampusDrive;
	}

	public void setForcampusDrive(Boolean forcampusDrive) {
		this.forcampusDrive = forcampusDrive;
	}



	
	
	public List<Stream> getStreams() {
		return streams;
	}

	public void setStreams(List<Stream> streams) {
		this.streams = streams;
	}

	public String getRolesAndResponsibility() {
		return rolesAndResponsibility;
	}

	public void setRolesAndResponsibility(String rolesAndResponsibility) {
		this.rolesAndResponsibility = rolesAndResponsibility;
	}

	public String getEligibilityCriteria() {
		return eligibilityCriteria;
	}

	public void setEligibilityCriteria(String eligibilityCriteria) {
		this.eligibilityCriteria = eligibilityCriteria;
	}

	public Double getSalaryAfterProbation() {
		return salaryAtProbation;
	}

	public void setSalaryAfterProbation(Double salaryAfterProbation) {
		this.salaryAtProbation = salaryAfterProbation;
	}

	public Integer getServiceAgreement() {
		return serviceAgreement;
	}

	public void setServiceAgreement(Integer serviceAgreement) {
		this.serviceAgreement = serviceAgreement;
	}

	
	

	

	public List<StudentBranch> getBranchs() {
		return branchs;
	}

	public void setBranchs(List<StudentBranch> branchs) {
		this.branchs = branchs;
	}



	public String getDescriptionFileUrl() {
		return descriptionFileUrl;
	}

	public void setDescriptionFileUrl(String descriptionFileUrl) {
		this.descriptionFileUrl = descriptionFileUrl;
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

	@Override
	public String toString() {
		return "Job [jobId=" + jobId + ", corporateId=" + corporateId + ", jobTitle=" + jobTitle + ", category="
				+ category + ", jobType=" + jobType + ", wfhCheckbox=" + wfhCheckbox + ", skills=" + skills + ", city="
				+ city + ", openings=" + openings + ", salary=" + salary + ", about=" + about + ", description="
				+ description + ", descriptionFileUrl=" + descriptionFileUrl + ", status=" + status + ", deleteStatus="
				+ deleteStatus + ", createdOn=" + createdOn + ", updatedOn=" + updatedOn + ", eligibilityCriteria="
				+ eligibilityCriteria + ", rolesAndResponsibility=" + rolesAndResponsibility + ", salaryAtProbation="
				+ salaryAtProbation + ", serviceAgreement=" + serviceAgreement + ", forcampusDrive=" + forcampusDrive
				+ ", tentativeDate=" + tentativeDate + ", streams=" + streams + ", branchs=" + branchs + "]";
	}
	
	

}
