package com.hirekarma.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Arrays;

import javax.persistence.Column;

//@Entity
//@Table(name="UNIVERSITY_USER")
public class UniversityUser  implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@NotNull
//	@Column(name = "UNIVERSITY_ID")
	private Long universityId;
	
//	@Column(name = "UNIVERSITY_NAME")
	private String universityName;
	
//	@Column(name="EMAIL_ADDRESS",)
	@Column(unique=true)
	private String emailAddress;
	
//	@Column(name="UNIVERSITY_EMAIL_ADDRESS")
	@Column(unique=true)
	private String universityEmailAddress;
	
//	@Column(name="PHONE_NO")
	private String phoneNo;
	
//	@Lob
//  @Column(name = "UNIVERSITY_IMAGE")
    private byte[] universityImage;
	
//	@Column(name = "USER_TYPE")
	private String userType;
	
//	@Column(name = "PASSWORD")
	private String password;
	
//	@Column(name = "UNIVERSITY_ADDRESS")
	private String universityAddress;
	
//	@CreationTimestamp
//	@Column(name = "CREATED_ON")
	private Timestamp createdOn;
	
//	@Column(name = "UPDATED_ON")
	private Timestamp updatedOn;

//	@Column(name = "STATUS")
	private String status;

	public Long getUniversityId() {
		return universityId;
	}

	public void setUniversityId(Long universityId) {
		this.universityId = universityId;
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

	public byte[] getUniversityImage() {
		return universityImage;
	}

	public void setUniversityImage(byte[] universityImage) {
		this.universityImage = universityImage;
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

	public String getUniversityAddress() {
		return universityAddress;
	}

	public void setUniversityAddress(String universityAddress) {
		this.universityAddress = universityAddress;
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
		return "UniversityUser [universityId=" + universityId + ", universityName=" + universityName + ", emailAddress="
				+ emailAddress + ", universityEmailAddress=" + universityEmailAddress + ", phoneNo=" + phoneNo
				+ ", universiyImage=" + Arrays.toString(universityImage) + ", userType=" + userType + ", password="
				+ password + ", universityAddress=" + universityAddress + ", createdOn=" + createdOn + ", updatedOn="
				+ updatedOn + ", status=" + status + "]";
	}
}