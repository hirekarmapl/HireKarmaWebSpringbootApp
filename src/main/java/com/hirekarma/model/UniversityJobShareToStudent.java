package com.hirekarma.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.sun.istack.NotNull;

import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "UNIVERSITY_JOB_SHARE_TO_STUDENT")
@Data
@ToString
public class UniversityJobShareToStudent {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull
	@Column(name = "TABLE_ID")
	private Long iD;

	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "CREATED_ON")
	@CreationTimestamp
	private Timestamp createdOn;

	@Column(name = "JOB_ID")
	private Long jobId;

	@Column(name = "STUDENT_ID")
	private Long studentId;

	@Column(name = "UNIVERSITY_ID")
	private Long universityId;

	@Column(name = "UPDATED_BY")
	private String updatedBy;

	@Column(name = "UPDATED_ON")
	private Timestamp updatedOn;

	@Column(name = "JOB_STATUS")
	private Boolean jobStatus;

	@Column(name = "RESPONSE_FEEDBACK")
	private String feedBack;

	@Column(name = "STUDENT_RESPONSE_STATUS")
	private Boolean studentResponseStatus;

}
