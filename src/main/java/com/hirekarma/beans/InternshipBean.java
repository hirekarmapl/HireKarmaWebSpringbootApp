package com.hirekarma.beans;

import java.sql.Timestamp;
import java.util.Arrays;

import org.springframework.web.multipart.MultipartFile;

public class InternshipBean {
	
	private Long internshipId;
	private Long userId;
	private String internshipTitle;
	private String internshipType;
	private String skills;
	private String city;
	private Integer openings;
	private Double salary;
	private String about;
	private String description;
	private byte[] descriptionFile;
	private String status;
	private String deleteStatus;
	private Timestamp createdOn;
	private Timestamp updatedOn;
	private MultipartFile file;
	
	public Long getInternshipId() {
		return internshipId;
	}
	public void setInternshipId(Long internshipId) {
		this.internshipId = internshipId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
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
	public byte[] getDescriptionFile() {
		return descriptionFile;
	}
	public void setDescriptionFile(byte[] descriptionFile) {
		this.descriptionFile = descriptionFile;
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
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	
	@Override
	public String toString() {
		return "InternshipBean [internshipId=" + internshipId + ", userId=" + userId + ", internshipTitle="
				+ internshipTitle + ", internshipType=" + internshipType + ", skills=" + skills + ", city=" + city
				+ ", openings=" + openings + ", salary=" + salary + ", about=" + about + ", description=" + description
				+ ", descriptionFile=" + Arrays.toString(descriptionFile) + ", status=" + status + ", deleteStatus="
				+ deleteStatus + ", createdOn=" + createdOn + ", updatedOn=" + updatedOn + ", file=" + file + "]";
	}
}
