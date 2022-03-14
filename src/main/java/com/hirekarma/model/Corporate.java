package com.hirekarma.model;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;

@Entity
@Table(name = "TBL_CORPORATE")
public class Corporate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull
	@Column(name = "CORPORATE_ID")
	private Long corporateId;

	@Column(name = "CORPORATE_NAME")
	private String corporateName;

	@Column(name = "CORPORATE_EMAIL", unique = true)
	private String corporateEmail;

	@Column(name = "CORPORATE_PHONE_NUMBER")
	private String corporatePhoneNumber;

	@Column(name = "CORPORATE_ADDRESS")
	private String corporateAddress;

	private String imageUrl;

	private Long userProfile;
	
	private Boolean profileUpdationStatus =  false;

	private String websiteUrl;
	
	private String about;
	
	
	
	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public String getWebsiteUrl() {
		return websiteUrl;
	}

	public void setWebsiteUrl(String websiteUrl) {
		this.websiteUrl = websiteUrl;
	}

	public Long getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(Long userProfile) {
		this.userProfile = userProfile;
	}

	@CreationTimestamp
	@Column(name = "CREATED_ON")
	private Timestamp createdOn;

	@Column(name = "UPDATED_ON")
	private Timestamp updatedOn;

	@Column(name = "STATUS")
	private Boolean status;

	@Column(name = "BADGE")
	private Long corporateBadge;

	@OneToMany(mappedBy = "corporate")
	@JsonIgnore
	private List<Blog> blogs;

	@OneToMany(mappedBy = "corporate")
	@JsonIgnore
	private List<OnlineAssessment> onlineAssessments;
	
	@OneToMany(mappedBy = "corporate")
	@JsonIgnore
	private List<QuestionANdanswer> questionANdanswers;

	public List<OnlineAssessment> getOnlineAssessments() {
		return onlineAssessments;
	}

	
	public Boolean getProfileUpdationStatus() {
		return profileUpdationStatus;
	}

	public void setProfileUpdationStatus(Boolean profileUpdationStatus) {
		this.profileUpdationStatus = profileUpdationStatus;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public List<QuestionANdanswer> getQuestionANdanswers() {
		return questionANdanswers;
	}

	public void setQuestionANdanswers(List<QuestionANdanswer> questionANdanswers) {
		this.questionANdanswers = questionANdanswers;
	}

	public void setOnlineAssessments(List<OnlineAssessment> onlineAssessments) {
		this.onlineAssessments = onlineAssessments;
	}

	public List<Blog> getBlogs() {
		return blogs;
	}

	public void setBlogs(List<Blog> blogs) {
		this.blogs = blogs;
	}

	public Long getCorporateId() {
		return corporateId;
	}

	public void setCorporateId(Long corporateId) {
		this.corporateId = corporateId;
	}

	public String getCorporateName() {
		return corporateName;
	}

	public void setCorporateName(String corporateName) {
		this.corporateName = corporateName;
	}

	public String getCorporateEmail() {
		return corporateEmail;
	}

	public void setCorporateEmail(String corporateEmail) {
		this.corporateEmail = corporateEmail;
	}

	

	public String getCorporatePhoneNumber() {
		return corporatePhoneNumber;
	}

	public void setCorporatePhoneNumber(String corporatePhoneNumber) {
		this.corporatePhoneNumber = corporatePhoneNumber;
	}

	public String getCorporateAddress() {
		return corporateAddress;
	}

	public void setCorporateAddress(String corporateAddress) {
		this.corporateAddress = corporateAddress;
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

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Long getCorporateBadge() {
		return corporateBadge;
	}

	public void setCorporateBadge(Long corporateBadge) {
		this.corporateBadge = corporateBadge;
	}

	

}
