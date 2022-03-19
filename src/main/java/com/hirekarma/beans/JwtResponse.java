package com.hirekarma.beans;

public class JwtResponse {
	
	private String jwtToken;
	private UserBeanResponse data;
	
	public String getJwtToken() {
		return jwtToken;
	}
	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}
	
	public UserBeanResponse getData() {
		return data;
	}
	public void setData(UserBeanResponse data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "JwtResponse [jwtToken=" + jwtToken + ", data=" + data + "]";
	}
}
