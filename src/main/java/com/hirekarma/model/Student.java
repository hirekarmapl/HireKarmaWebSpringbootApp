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

import lombok.Data;
import lombok.ToString;

@Entity
@Table(name="TBL_STUDENT")
//@Data
//@ToString
public class Student implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@NotNull
	@Column(name = "STUDENT_ID")
	private Long studentId;
	
	@Column(name = "USER_ID")
	private Long userId;
	
	@Column(name = "STUDENT_NAME")
	private String studentName;
	
	@Column(name = "STUDENT_PHONE_NUMBER")
	private Long studentPhoneNumber;
	
	@Column(name = "STUDENT_EMAIL",unique=true)
	private String studentEmail;
	
	@Column(name = "UNIVERSITY_ID")
	private Long universityId;
	
	@Column(name = "STUDENT_ADDRESS")
	private String studentAddress;
	
	@Column(name = "BRANCH")
	private Long branch;
	
	@Column(name = "BATCH")
	private Long batch;
	
	@Column(name = "CGPA")
	private Double cgpa;
	

	
	@CreationTimestamp
	@Column(name = "CREATED_ON")
	private Timestamp createdOn;
	
	@Column(name = "UPDATED_ON")
	private Timestamp updatedOn;

	@Column(name = "STATUS")
	private Boolean status;
	
	private String imageUrl;
	

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	@Override
	public String toString() {
		return "Student [studentId=" + studentId + ", userId=" + userId + ", studentName=" + studentName
				+ ", studentPhoneNumber=" + studentPhoneNumber + ", studentEmail=" + studentEmail + ", universityId="
				+ universityId + ", studentAddress=" + studentAddress + ", branch=" + branch + ", batch=" + batch
				+ ", cgpa=" + cgpa + ", createdOn=" + createdOn
				+ ", updatedOn=" + updatedOn + ", status=" + status + "]";
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public Long getStudentPhoneNumber() {
		return studentPhoneNumber;
	}

	public void setStudentPhoneNumber(Long studentPhoneNumber) {
		this.studentPhoneNumber = studentPhoneNumber;
	}

	public String getStudentEmail() {
		return studentEmail;
	}

	public void setStudentEmail(String studentEmail) {
		this.studentEmail = studentEmail;
	}

	public Long getUniversityId() {
		return universityId;
	}

	public void setUniversityId(Long universityId) {
		this.universityId = universityId;
	}

	public String getStudentAddress() {
		return studentAddress;
	}

	public void setStudentAddress(String studentAddress) {
		this.studentAddress = studentAddress;
	}

	public Long getBranch() {
		return branch;
	}

	public void setBranch(Long branch) {
		this.branch = branch;
	}

	public Long getBatch() {
		return batch;
	}

	public void setBatch(Long batch) {
		this.batch = batch;
	}

	public Double getCgpa() {
		return cgpa;
	}

	public void setCgpa(Double cgpa) {
		this.cgpa = cgpa;
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

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	
	
}
