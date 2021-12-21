package com.hirekarma.model;

import java.io.Serializable;
import java.sql.Timestamp;
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
@Table(name = "COPORATE_USER")
public class CoporateUser implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull
	@Column(name = "CORP_USER_ID")
	private Long corpUserId;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "EMAIL")
	private String email;
	
	@Column(name="PHONE_NO")
	private String phoneNO;
	
	@Lob
    @Column(name = "PROFILE_IMAGE")
    private byte[] profileImage;
	
	@Column(name = "ADDRESS")
	private String address;
	
	@Column(name = "PASSWORD")
	private String password;
	
	@Column(name = "USER_TYPE")
	private String userType;
	
	@CreationTimestamp
	@Column(name = "CREATED_ON")
	private Timestamp createdOn;
	
	@Column(name = "UPDATED_ON")
	private Timestamp updatedOn;
	
	@Column(name = "STATUS")
	private String status;

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
		return "CoporateUser [corpUserId=" + corpUserId + ", name=" + name + ", email=" + email + ", phoneNO=" + phoneNO
				+ ", profileImage=" + Arrays.toString(profileImage) + ", address=" + address + ", password=" + password
				+ ", userType=" + userType + ", createdOn=" + createdOn + ", updatedOn=" + updatedOn + ", status="
				+ status + "]";
	}
}
