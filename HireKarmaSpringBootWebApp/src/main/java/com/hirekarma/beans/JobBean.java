package com.hirekarma.beans;

import java.util.Arrays;

import org.springframework.web.multipart.MultipartFile;

public class JobBean {
	
	private Long jobId;
	private Long corpUserId;
	private String jobTitle;
	private String category;
	private String jobType;
	private Boolean wfhCheckbox;
	private String skills;
	private String city;
	private Integer openings;
	private Double salary;
	private String about;
	private String description;
	private byte[] descriptionFile;
	private MultipartFile file;
	private String status;
	private String deleteStatus;
	
	public Long getJobId() {
		return jobId;
	}
	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}
	public Long getCorpUserId() {
		return corpUserId;
	}
	public void setCorpUserId(Long corpUserId) {
		this.corpUserId = corpUserId;
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
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDeleteStatus() {
		return deleteStatus;
	}
	public void setDeleteStatus(String deleteStatus) {
		this.deleteStatus = deleteStatus;
	}
	
	@Override
	public String toString() {
		return "JobBean [jobId=" + jobId + ", corpUserId=" + corpUserId + ", jobTitle=" + jobTitle + ", category="
				+ category + ", jobType=" + jobType + ", wfhCheckbox=" + wfhCheckbox + ", skills=" + skills + ", city="
				+ city + ", openings=" + openings + ", salary=" + salary + ", about=" + about + ", description="
				+ description + ", descriptionFile=" + Arrays.toString(descriptionFile) + ", file=" + file + ", status="
				+ status + ", deleteStatus=" + deleteStatus + "]";
	}
}
