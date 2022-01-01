package com.hirekarma.beans;

public class JwtResponse {
	
	private String jwtToken;
	private UserBean data;
	
	public String getJwtToken() {
		return jwtToken;
	}
	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}
	public UserBean getData() {
		return data;
	}
	public void setData(UserBean data) {
		this.data = data;
	}
	
	@Override
	public String toString() {
		return "JwtResponse [jwtToken=" + jwtToken + ", data=" + data + "]";
	}
}
