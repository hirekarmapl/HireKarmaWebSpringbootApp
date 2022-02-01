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
@Table(name = "JOB")
@Data
@ToString
public class Job implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull
	@Column(name = "JOB_ID")
	private Long jobId;
	
	@Column(name = "USER_ID")
	private Long userId;
	
	@Column(name = "JOB_TITLE")
	private String jobTitle;
	
	@Column(name = "CATEGORY")
	private String category;
	
	@Column(name = "JOBT_YPE")
	private String jobType;
	
	@Column(name = "WFH_CHECK_BOX")
	private Boolean wfhCheckbox;
	
	@Column(name = "SKILLS")
	private String skills;
	
	@Column(name = "CITY")
	private String city;
	
	@Column(name = "OPENINGS")
	private Integer openings;
	
	@Column(name = "SALARY")
	private Double salary;
	
	@Column(name = "ABOUT")
	private String about;
	
	@Column(name = "DESCRIPTION")
	private String description;
	
	@Lob
	@Column(name = "DESCRIPTION_FILE")
	private byte[] descriptionFile;
	
	@Column(name = "STATUS")
	private boolean status;
	
	@Column(name = "DELETE_STATUS")
	private String deleteStatus;
	
	@CreationTimestamp
	@Column(name = "CREATED_ON")
	private Timestamp createdOn;
	
	@Column(name = "UPDATED_ON")
	private Timestamp updatedOn;

}
