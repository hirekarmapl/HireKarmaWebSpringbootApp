package com.hirekarma.beans;

import java.util.Arrays;

public class UniversityUserBean {
	
	private String universityName;
	private String emailAddress;
	private String universityEmailAddress;
	private String phoneNo;
	private byte[] universiyImage;
	private String userType;
	
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
	@Override
	public String toString() {
		return "UniversityUserBean [universityName=" + universityName + ", emailAddress=" + emailAddress
				+ ", universityEmailAddress=" + universityEmailAddress + ", phoneNo=" + phoneNo + ", universiyImage="
				+ Arrays.toString(universiyImage) + ", userType=" + userType + "]";
	}
}
