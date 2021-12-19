package com.hirekarma.beans;

import java.util.Arrays;

import org.springframework.web.multipart.MultipartFile;

public class CoporateUserBean {

	private Long corpUserId;
	private String name;
	private String email;
	private String phoneNO;
    private byte[] profileImage;
	private String address;
	private String password;
	private String userType;
	private MultipartFile file;
	
	public Long getCorpUserId() {
		return corpUserId;
	}
	public void setCorpUserId(Long corpUserId) {
		this.corpUserId = corpUserId;
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
	public String getPhoneNO() {
		return phoneNO;
	}
	public void setPhoneNO(String phoneNO) {
		this.phoneNO = phoneNO;
	}
	public byte[] getProfileImage() {
		return profileImage;
	}
	public void setProfileImage(byte[] profileImage) {
		this.profileImage = profileImage;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	@Override
	public String toString() {
		return "CoporateUserBean [corpUserId=" + corpUserId + ", name=" + name + ", email=" + email + ", phoneNO="
				+ phoneNO + ", profileImage=" + Arrays.toString(profileImage) + ", address=" + address + ", password="
				+ password + ", userType=" + userType + ", file=" + file + "]";
	}
	
	
}
