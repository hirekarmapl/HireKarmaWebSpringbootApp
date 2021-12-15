package com.hirekarma.model;

import java.io.Serializable;
import java.security.Timestamp;
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
@Table(name="UNIVERSITY_USER")
public class UniversityUser  implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull
	@Column(name = "UNIVERSITY_ID")
	private Long universityId;
	
	@Column(name = "UNIVERSITY_NAME")
	private String universityName;
	
	@Column(name="EMAIL_ADDRESS")
	private String emailAddress;
	
	@Column(name="UNIVERSITY_EMAIL_ADDRESS")
	private String universityEmailAddress;
	
	@Column(name="PHONE_NO")
	private String phoneNo;
	
	@Lob
    @Column(name = "UNIVERSITY_IMAGE")
    private byte[] universiyImage;
	
	@Column(name = "USER_TYPE")
	private String userType;
	
	@CreationTimestamp
	@Column(name = "CREATED_ON")
	private Timestamp createdOn;
	
	@CreationTimestamp
	@Column(name = "UPDATED_ON")
	private Timestamp updatedOn;

	@Column(name = "STATUS")
	private String status;

	
	public Long getUserId() {
		return universityId;
	}

	public void setUserId(Long userId) {
		this.universityId = userId;
	}

	public String getUniversityName() {
		return universityName;
	}

	public void setUniversityName(String universityName) {
		this.universityName = universityName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
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

	public byte[] getUniversiyImage() {
		return universiyImage;
	}

	public void setUniversiyImage(byte[] universiyImage) {
		this.universiyImage = universiyImage;
	}

	public String getUserType() {
		return userType;
	}


	public void setUserType(String userType) {
		this.userType = userType;
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

	@Override
	public String toString() {
		return "UniversityUser [userId=" + universityId + ", universityName=" + universityName + ", emailAddress="
				+ emailAddress + ", universityEmailAddress=" + universityEmailAddress + ", phoneNo=" + phoneNo
				+ ", universiyImage=" + Arrays.toString(universiyImage) + ", userType=" + userType + ", createdOn="
				+ createdOn + ", updatedOn=" + updatedOn + ", status=" + status + "]";
	}
}