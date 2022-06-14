package com.hirekarma.model;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sun.istack.NotNull;

@Entity
@Table(name = "TBL_CORPORATE")
public class Corporate {

	private String about;

	@OneToMany(mappedBy = "corporate",fetch= FetchType.LAZY)
	@JsonIgnore
	private List<Blog> blogs;

	@Column(name = "CORPORATE_ADDRESS")
	private String corporateAddress;

	@Column(name = "BADGE")
	private Long corporateBadge;

	@Column(name = "CORPORATE_EMAIL", unique = true)
	private String corporateEmail;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@NotNull
	@Column(name = "CORPORATE_ID")
	private Long corporateId;

	@Column(name = "CORPORATE_NAME")
	private String corporateName;
	
	@Column(name = "CORPORATE_PHONE_NUMBER")
	private String corporatePhoneNumber;

	@CreationTimestamp
	@Column(name = "CREATED_ON")
	private Timestamp createdOn;
	
	private String imageUrl;
	
	@JsonIgnore
	@OneToMany(mappedBy = "corporate",orphanRemoval = true)
	private Set<Event> events;
	
	@OneToMany(mappedBy = "corporate")
	@JsonIgnore
	private List<OnlineAssessment> onlineAssessments;

	private double percentageOfProfileCompletion;

	private Boolean profileUpdationStatus =  false;

	@OneToMany(mappedBy = "corporate")
	@JsonIgnore
	private List<QuestionANdanswer> questionANdanswers;

	@Column(name = "STATUS")
	private Boolean status;

	@Column(name = "UPDATED_ON")
	private Timestamp updatedOn;

	private Long userProfile;
	

	private String websiteUrl;
	
	
	boolean is_mentor;
	
	@OneToOne(mappedBy = "corporate")
	@JsonIgnore
	Mentor mentor;
	
	
	
//	@Column(nullable = false)
//	private Boolean emailVerfication;

	public Mentor getMentor() {
		return mentor;
	}
	public void setMentor(Mentor mentor) {
		this.mentor = mentor;
	}
	@OneToMany(mappedBy = "corporate")
	@JsonIgnore
	List<Webinar> webinars;
	
	
	public Boolean getIs_mentor() {
		return is_mentor;
	}
	public void setIs_mentor(Boolean is_mentor) {
		this.is_mentor = is_mentor;
	}
	public List<Webinar> getWebinars() {
		return webinars;
	}
	public void setWebinars(List<Webinar> webinars) {
		this.webinars = webinars;
	}
	public String getAbout() {
		return about;
	}
	public Boolean is_mentor() {
		return is_mentor;
	}
	public void set_mentor(Boolean is_mentor) {
		this.is_mentor = is_mentor;
	}
	public List<Blog> getBlogs() {
		return blogs;
	}

	public String getCorporateAddress() {
		return corporateAddress;
	}

	public Long getCorporateBadge() {
		return corporateBadge;
	}

	public String getCorporateEmail() {
		return corporateEmail;
	}

	public Long getCorporateId() {
		return corporateId;
	}

	public String getCorporateName() {
		return corporateName;
	}
	
	public String getCorporatePhoneNumber() {
		return corporatePhoneNumber;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	
	public String getImageUrl() {
		return imageUrl;
	}

	public List<OnlineAssessment> getOnlineAssessments() {
		return onlineAssessments;
	}

	public double getPercentageOfProfileCompletion() {
		return percentageOfProfileCompletion;
	}

	public Boolean getProfileUpdationStatus() {
		return profileUpdationStatus;
	}

	public List<QuestionANdanswer> getQuestionANdanswers() {
		return questionANdanswers;
	}

	public Boolean getStatus() {
		return status;
	}

	public Timestamp getUpdatedOn() {
		return updatedOn;
	}

	public Long getUserProfile() {
		return userProfile;
	}

	public String getWebsiteUrl() {
		return websiteUrl;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public void setBlogs(List<Blog> blogs) {
		this.blogs = blogs;
	}

	public void setCorporateAddress(String corporateAddress) {
		this.corporateAddress = corporateAddress;
	}

	public void setCorporateBadge(Long corporateBadge) {
		this.corporateBadge = corporateBadge;
	}

	public void setCorporateEmail(String corporateEmail) {
		this.corporateEmail = corporateEmail;
	}

	public void setCorporateId(Long corporateId) {
		this.corporateId = corporateId;
	}

	

	public void setCorporateName(String corporateName) {
		this.corporateName = corporateName;
	}

	public void setCorporatePhoneNumber(String corporatePhoneNumber) {
		this.corporatePhoneNumber = corporatePhoneNumber;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	
	public void setOnlineAssessments(List<OnlineAssessment> onlineAssessments) {
		this.onlineAssessments = onlineAssessments;
	}

	public void setPercentageOfProfileCompletion(double percentageOfProfileCompletion) {
		this.percentageOfProfileCompletion = percentageOfProfileCompletion;
	}

	public void setProfileUpdationStatus(Boolean profileUpdationStatus) {
		this.profileUpdationStatus = profileUpdationStatus;
	}

	public void setQuestionANdanswers(List<QuestionANdanswer> questionANdanswers) {
		this.questionANdanswers = questionANdanswers;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public void setUpdatedOn(Timestamp updatedOn) {
		this.updatedOn = updatedOn;
	}

	public void setUserProfile(Long userProfile) {
		this.userProfile = userProfile;
	}

	public void setWebsiteUrl(String websiteUrl) {
		this.websiteUrl = websiteUrl;
	}
	@Override
	public String toString() {
		return "Corporate [about=" + about + ", blogs=" + blogs + ", corporateAddress=" + corporateAddress
				+ ", corporateBadge=" + corporateBadge + ", corporateEmail=" + corporateEmail + ", corporateId="
				+ corporateId + ", corporateName=" + corporateName + ", corporatePhoneNumber=" + corporatePhoneNumber
				+ ", createdOn=" + createdOn + ", imageUrl=" + imageUrl + ", onlineAssessments=" + onlineAssessments
				+ ", percentageOfProfileCompletion=" + percentageOfProfileCompletion + ", profileUpdationStatus="
				+ profileUpdationStatus + ", questionANdanswers=" + questionANdanswers + ", status=" + status
				+ ", updatedOn=" + updatedOn + ", userProfile=" + userProfile + ", websiteUrl=" + websiteUrl + "]";
	}

	

}
