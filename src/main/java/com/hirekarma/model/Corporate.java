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
	private Long corporatePhoneNumber;

	@Column(name = "CORPORATE_ADDRESS")
	private String corporateAddress;

	@Lob
	@Column(name = "PROFILE_IMAGE")
	private byte[] profileImage;

	private Long userProfile;

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

	public Long getCorporatePhoneNumber() {
		return corporatePhoneNumber;
	}

	public void setCorporatePhoneNumber(Long corporatePhoneNumber) {
		this.corporatePhoneNumber = corporatePhoneNumber;
	}

	public String getCorporateAddress() {
		return corporateAddress;
	}

	public void setCorporateAddress(String corporateAddress) {
		this.corporateAddress = corporateAddress;
	}

	public byte[] getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(byte[] profileImage) {
		this.profileImage = profileImage;
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

	@Override
	public String toString() {
		return "Corporate [corporateId=" + corporateId + ", corporateName=" + corporateName + ", corporateEmail="
				+ corporateEmail + ", corporatePhoneNumber=" + corporatePhoneNumber + ", corporateAddress="
				+ corporateAddress + ", profileImage=" + Arrays.toString(profileImage) + ", createdOn=" + createdOn
				+ ", updatedOn=" + updatedOn + ", status=" + status + ", corporateBadge=" + corporateBadge + "]";
	}

}
