package com.hirekarma.model;

import java.io.Serializable;
import java.sql.Timestamp;

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
@Data
@ToString
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
	
	@Column(name = "STUDENT_EMAIL")
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
	
	@Lob
	@Column(name = "STUDENT_IMAGE")
	private byte[] studentImage;
	
	@CreationTimestamp
	@Column(name = "CREATED_ON")
	private Timestamp createdOn;
	
	@Column(name = "UPDATED_ON")
	private Timestamp updatedOn;

	@Column(name = "STATUS")
	private Boolean status;
}
