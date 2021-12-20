package com.hirekarma.beans;

import java.util.Arrays;

import org.springframework.web.multipart.MultipartFile;

public class UniversityUserBean {
	
	private Long universityId;
	private String universityName;
	private String emailAddress;
	private String universityEmailAddress;
	private String phoneNo;
	private byte[] universityImage;
	private String userType;
	private String universityAddress;
	private MultipartFile file;
	private String password;
	
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
	public String getUniversityAddress() {
		return universityAddress;
	}
	public void setUniversityAddress(String universityAddress) {
		this.universityAddress = universityAddress;
	}
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {
		return "UniversityUserBean [universityId=" + universityId + ", universityName=" + universityName
				+ ", emailAddress=" + emailAddress + ", universityEmailAddress=" + universityEmailAddress + ", phoneNo="
				+ phoneNo + ", universiyImage=" + Arrays.toString(universityImage) + ", userType=" + userType
				+ ", universityAddress=" + universityAddress + ", file=" + file + ", password=" + password + "]";
	}
}
