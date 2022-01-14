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
@Table(name = "SHARE_JOB_TO_UNIVERSITY")
@Data
@ToString
public class ShareJob {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull
	@Column(name = "SHARE_JOB_ID")
	private Long shareJobId;
	
	@Column(name = "JOB_ID")
	private Long jobId;
	
	@Column(name = "UNIVERSITY_ID")
	private Long universityId;
	
	@CreationTimestamp
	@Column(name = "CREATED_ON")
	private Timestamp createdOn;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "UPDATED_ON")
	private Timestamp updatedOn;
	
	@Column(name = "UPDATED_BY")
	private String updatedBy;
	
	

}