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
@Table(name = "INTERNSHIP")
@Data
@ToString
public class Internship implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull
	@Column(name = "INTERNSHIP_ID")
	private Long internshipId;
	
	@Column(name = "CORPORATE_ID")
	private Long corporateId;
	
	@Column(name = "INTERNSHIP_TITLE")
	private String internshipTitle;
	
	@Column(name = "INTERNSHIP_TYPE")
	private String internshipType;
	
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
	private Boolean status;
	
	@Column(name = "DELETE_STATUS")
	private Boolean deleteStatus;
	
	@CreationTimestamp
	@Column(name = "CREATED_ON")
	private Timestamp createdOn;
	
	@Column(name = "UPDATED_ON")
	private Timestamp updatedOn;

	
}
