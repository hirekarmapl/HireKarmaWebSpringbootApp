package com.hirekarma.beans;

public class HireKarmaUserBean {
	
	private Long userId;
	private String name;
	private String email;
	private String password;
	private String userType;
	
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
	
	@Override
	public String toString() {
		return "HireKarmaUserBean [userId=" + userId + ", name=" + name + ", email=" + email + ", password=" + password
				+ ", userType=" + userType + "]";
	}
}

