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
@Table(name="USER_PROFILE")
public class UserProfile  implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull
	@Column(name = "USER_ID")
	private Long userId;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name="EMAIL")
	private String email;
	
	@Column(name="UNIVERSITY_EMAIL_ADDRESS")
	private String universityEmailAddress;
	
	@Column(name="PHONE_NO")
	private String phoneNo;
	
	@Lob
    @Column(name = "IMAGE")
    private byte[] image;
	
	@Column(name = "USER_TYPE")
	private String userType;
	
	@Column(name = "PASSWORD")
	private String password;
	
	@Column(name = "UNIVERSITY_ADDRESS")
	private String address;
	
	@CreationTimestamp
	@Column(name = "CREATED_ON")
	private Timestamp createdOn;
	
	@Column(name = "UPDATED_ON")
	private Timestamp updatedOn;

	@Column(name = "STATUS")
	private String status;

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

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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
		return "UserProfile [userId=" + userId + ", name=" + name + ", email=" + email + ", universityEmailAddress="
				+ universityEmailAddress + ", phoneNo=" + phoneNo + ", image=" + Arrays.toString(image) + ", userType="
				+ userType + ", password=" + password + ", address=" + address + ", createdOn=" + createdOn
				+ ", updatedOn=" + updatedOn + ", status=" + status + "]";
	}
}