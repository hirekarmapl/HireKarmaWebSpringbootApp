package com.hirekarma.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
@Entity
public class Education {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
int id;
String levelOfEducation;
String field;
String college;
int startDateMonth;
int startDateYear;
int endDateMonth;
int endDateYear;
@ManyToOne
UserProfile userProfile;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getLevelOfEducation() {
	return levelOfEducation;
}
public void setLevelOfEducation(String levelOfEducation) {
	this.levelOfEducation = levelOfEducation;
}
public String getField() {
	return field;
}
public void setField(String field) {
	this.field = field;
}
public String getCollege() {
	return college;
}
public void setCollege(String college) {
	this.college = college;
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
public UserProfile getUserProfile() {
	return userProfile;
}
public void setUserProfile(UserProfile userProfile) {
	this.userProfile = userProfile;
}
public Education(int id, String levelOfEducation, String field, String college, int startDateMonth, int startDateYear,
		int endDateMonth, int endDateYear, UserProfile userProfile) {
	super();
	this.id = id;
	this.levelOfEducation = levelOfEducation;
	this.field = field;
	this.college = college;
	this.startDateMonth = startDateMonth;
	this.startDateYear = startDateYear;
	this.endDateMonth = endDateMonth;
	this.endDateYear = endDateYear;
	this.userProfile = userProfile;
}
public void update(Education education, UserProfile userProfile) {

	this.setLevelOfEducation(education.getLevelOfEducation());
	this.setField(education.getField()); 
	this.setCollege(education.getCollege());
	this.setStartDateMonth(education.getStartDateMonth());
	this.setStartDateYear(education.getStartDateYear());
	this.setEndDateMonth(education.getEndDateMonth());
	this.setEndDateYear(education.getEndDateYear());
	this.setUserProfile(userProfile);
}
public Education() {
	super();
	// TODO Auto-generated constructor stub
}
@Override
public String toString() {
	return "Education [id=" + id + ", levelOfEducation=" + levelOfEducation + ", field=" + field + ", college="
			+ college + ", startDateMonth=" + startDateMonth + ", startDateYear=" + startDateYear + ", endDateMonth="
			+ endDateMonth + ", endDateYear=" + endDateYear + ", userProfile=" + userProfile + "]";
}


}
