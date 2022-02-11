package com.hirekarma.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
@Entity
public class Experience {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int id;
	String jobTitle;
	String company;
	String employeeType;
	String location;
	int startDateMonth;
	int startDateYear;
	int endDateMonth;
	int endDateYear;
	boolean currentlyWorking;
	String jobDescription;
	@ManyToOne
	UserProfile userProfile;
	
	public UserProfile getUserProfile() {
		return userProfile;
	}
	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getJobTitle() {
		return jobTitle;
	}
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getEmployeeType() {
		return employeeType;
	}
	public void setEmployeeType(String employeeType) {
		this.employeeType = employeeType;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public int getStartDateMonth() {
		return startDateMonth;
	}
	public void setStartDateMonth(int startDateMonth) {
		this.startDateMonth = startDateMonth;
	}
	public int getStartDateYear() {
		return startDateYear;
	}
	public void setStartDateYear(int startDateYear) {
		this.startDateYear = startDateYear;
	}
	public int getEndDateMonth() {
		return endDateMonth;
	}
	public void setEndDateMonth(int endDateMonth) {
		this.endDateMonth = endDateMonth;
	}
	public int getEndDateYear() {
		return endDateYear;
	}
	public void setEndDateYear(int endDateYear) {
		this.endDateYear = endDateYear;
	}
	public boolean isCurrentlyWorking() {
		return currentlyWorking;
	}
	public void setCurrentlyWorking(boolean currentlyWorking) {
		this.currentlyWorking = currentlyWorking;
	}
	public String getJobDescription() {
		return jobDescription;
	}
	public void setJobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
	}
	public Experience(int id, String jobTitle, String company, String employeeType, String location, int startDateMonth,
			int startDateYear, int endDateMonth, int endDateYear, boolean currentlyWorking, String jobDescription) {
		super();
		this.id = id;
		this.jobTitle = jobTitle;
		this.company = company;
		this.employeeType = employeeType;
		this.location = location;
		this.startDateMonth = startDateMonth;
		this.startDateYear = startDateYear;
		this.endDateMonth = endDateMonth;
		this.endDateYear = endDateYear;
		this.currentlyWorking = currentlyWorking;
		this.jobDescription = jobDescription;
	}
	public Experience() {
		super();
		// TODO Auto-generated constructor stub
	}
	public void update(Experience experience,UserProfile userProfile) {
		this.jobTitle = experience.getJobTitle();
		this.company = experience.getCompany();
		this.employeeType = experience.getEmployeeType();
		this.location = experience.getLocation();
		this.startDateMonth = experience.getStartDateMonth();
		this.startDateYear = experience.getStartDateYear();
		this.endDateMonth = experience.getEndDateMonth();
		this.endDateYear = experience.getEndDateYear();
		this.currentlyWorking = experience.isCurrentlyWorking();
		this.jobDescription = experience.getJobDescription();
		this.userProfile = userProfile;
		
	}
	@Override
	public String toString() {
		return "Experience [id=" + id + ", jobTitle=" + jobTitle + ", company=" + company + ", employeeType="
				+ employeeType + ", location=" + location + ", startDateMonth=" + startDateMonth + ", startDateYear="
				+ startDateYear + ", endDateMonth=" + endDateMonth + ", endDateYear=" + endDateYear
				+ ", currentlyWorking=" + currentlyWorking + ", jobDescription=" + jobDescription + "]";
	}
	
}
