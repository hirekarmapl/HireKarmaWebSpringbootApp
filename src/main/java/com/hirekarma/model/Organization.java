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
@Table(name = "ORGANIZATION")
@Data
@ToString
public class Organization implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull
	@Column(name = "ORGANIZATION_ID")
	private Long organizationId;
	
	@Column(name = "CORPORATE_ID")
	private Long corporateId;
	
	@Column(name = "ORG_NAME")
	private String orgName;
	
	@Column(name = "ORG_EMAIL")
	private String orgEmail;
	
	@Column(name = "CIN_GST_NUM")
	private String cinGstNum;
	
	@Lob
	@Column(name = "LOGO")
	private byte[] logo;
	
	@Column(name="DESCRIPTION")
	private String description;
	
	@CreationTimestamp
	@Column(name = "CREATED_ON")
	private Timestamp createdOn;
	
	@Column(name = "UPDATED_ON")
	private Timestamp updatedOn;
	
	@Column(name = "STATUS")
	private Boolean status;

	
}
