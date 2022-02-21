package com.hirekarma.beans;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import com.hirekarma.model.Skill;
import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


public class UserBean implements Serializable {

	private static final long serialVersionUID = 1L;

	
	private Long userId;
	
	private String name;
	
	
	private String email;
	private String universityEmailAddress;
	private String phoneNo;
	private byte[] image;
	private String userType;
	
	private String password;
	private String address;
	private Timestamp createdOn;
	private Timestamp updatedOn;
	private MultipartFile file;
	private Long branch;
	private Double cgpa;
	private Long batch;
	private Long universityId;
	private List<Skill> skills = new ArrayList<Skill>();
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
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	public Long getBranch() {
		return branch;
	}
	public void setBranch(Long branch) {
		this.branch = branch;
	}
	public Double getCgpa() {
		return cgpa;
	}
	public void setCgpa(Double cgpa) {
		this.cgpa = cgpa;
	}
	public Long getBatch() {
		return batch;
	}
	public void setBatch(Long batch) {
		this.batch = batch;
	}
	public Long getUniversityId() {
		return universityId;
	}
	public void setUniversityId(Long universityId) {
		this.universityId = universityId;
	}
	public List<Skill> getSkills() {
		return skills;
	}
	public void setSkills(List<Skill> skills) {
		this.skills = skills;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "UserBean [userId=" + userId + ", name=" + name + ", email=" + email + ", universityEmailAddress="
				+ universityEmailAddress + ", phoneNo=" + phoneNo + ", image=" + Arrays.toString(image) + ", userType="
				+ userType + ", password=" + password + ", address=" + address + ", createdOn=" + createdOn
				+ ", updatedOn=" + updatedOn + ", file=" + file + ", branch=" + branch + ", cgpa=" + cgpa + ", batch="
				+ batch + ", universityId=" + universityId + ", skills=" + skills + "]";
	}
	public UserBean(Long userId, String name, String email, String universityEmailAddress, String phoneNo, byte[] image,
			String userType, String password, String address, Timestamp createdOn, Timestamp updatedOn,
			MultipartFile file, Long branch, Double cgpa, Long batch, Long universityId, List<Skill> skills) {
		super();
		this.userId = userId;
		this.name = name;
		this.email = email;
		this.universityEmailAddress = universityEmailAddress;
		this.phoneNo = phoneNo;
		this.image = image;
		this.userType = userType;
		this.password = password;
		this.address = address;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
		this.file = file;
		this.branch = branch;
		this.cgpa = cgpa;
		this.batch = batch;
		this.universityId = universityId;
		this.skills = skills;
	}
	public UserBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
