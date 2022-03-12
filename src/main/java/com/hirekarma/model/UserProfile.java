package com.hirekarma.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hirekarma.beans.AuthenticationProvider;
import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import javax.validation.constraints.Email;

@Entity
@Table(name = "USER_PROFILE")
//@Data
//@ToString
//@AllArgsConstructor
//@NoArgsConstructor
public class UserProfile implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull
	@Column(name = "USER_ID")
	private Long userId;

	@Column(name = "NAME")
	private String name;

	@Column(name = "EMAIL",unique=true)
	@Email(message = "Email is not valid", regexp = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")
	private String email;

	@Column(name = "UNIVERSITY_EMAIL_ADDRESS")
	private String universityEmailAddress;

	@Column(name = "PHONE_NO")
	private String phoneNo;

	private String imageUrl;
	
	

	@Column(name = "USER_TYPE")
	private String userType;

	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "UNIVERSITY_ADDRESS")
	private String address;

	@CreationTimestamp
	@Column(name = "CREATED_ON")
	private Timestamp createdOn;

	@Column(name = "UPDATED_ON")
	private Timestamp updatedOn;

	@Column(name = "STATUS")
	private String status;
	

	public String about;
	
	

	@Override
	public String toString() {
		return "UserProfile [userId=" + userId + ", name=" + name + ", email=" + email + ", universityEmailAddress="
				+ universityEmailAddress + ", phoneNo=" + phoneNo + ", imageUrl=" + imageUrl + ", userType=" + userType
				+ ", password=" + password + ", address=" + address + ", createdOn=" + createdOn + ", updatedOn="
				+ updatedOn + ", status=" + status + ", about=" + about + ", shareJobId=" + shareJobId + ", response="
				+ response + ", authProvider=" + authProvider + ", skills=" + skills + ", projects=" + projects
				+ ", educations=" + educations + ", experiences=" + experiences + ", resetPasswordToken="
				+ resetPasswordToken + "]";
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	@Column(name = "share_job_id")
	private Long shareJobId;

	@Transient
	private String response;

	@Enumerated(EnumType.STRING)
	@Column(name = "AUTH_PROVIDER")
	private AuthenticationProvider authProvider;
	
	@ManyToMany
	private List<Skill> skills = new ArrayList<Skill>();
	
	
	@OneToMany(mappedBy = "userProfile")
	@JsonIgnore
	List<Project> projects = new ArrayList<Project>();
	
	@OneToMany(mappedBy = "userProfile")
	@JsonIgnore
	List<Education> educations;
	
	
	@OneToMany(mappedBy = "userProfile")
	@JsonIgnore
	List<Experience> experiences = new ArrayList<Experience>();
	
	@Column(name = "RESET_PASSWORD_TOKEN")
	String resetPasswordToken;
	public String getResetPasswordToken() {
		return resetPasswordToken;
	}


	public void setResetPasswordToken(String resetPasswordToken) {
		this.resetPasswordToken = resetPasswordToken;
	}

	public List<Experience> getExperiences() {
		return experiences;
	}

	public void setExperiences(List<Experience> experiences) {
		this.experiences = experiences;
	}

	public List<Project> getProjects() {
		return projects;
	}

	public List<Education> getEducations() {
		return educations;
	}

	public void setEducations(List<Education> educations) {
		this.educations = educations;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	public List<Skill> getSkills() {
		return skills;
	}

	public void setSkills(List<Skill> skills) {
		this.skills = skills;
	}



	

	public UserProfile() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUniversityEmailAddress() {
		return universityEmailAddress;
	}

	public void setUniversityEmailAddress(String universityEmailAddress) {
		this.universityEmailAddress = universityEmailAddress;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	
	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getShareJobId() {
		return shareJobId;
	}

	public void setShareJobId(Long shareJobId) {
		this.shareJobId = shareJobId;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public AuthenticationProvider getAuthProvider() {
		return authProvider;
	}

	public void setAuthProvider(AuthenticationProvider authProvider) {
		this.authProvider = authProvider;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	

}