package com.hirekarma.beans;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;

import org.hibernate.annotations.CreationTimestamp;

import com.hirekarma.model.Corporate;
import com.hirekarma.model.Stream;
import com.hirekarma.model.StudentBranch;

import lombok.Data;
import lombok.ToString;
@Data
@ToString
public class JobModelResponseBean {
private Long jobId;

	private Long corporateId;
	

	private String jobTitle;

	private String category;
	
	private String jobType;
	
	private Boolean wfhCheckbox;
	
	private String skills;
	
	private String city;
	
	private Integer openings;
	
	private Double salary;
	
	private String about;
	
	private String description;
	
	private String descriptionFileUrl;
	
	private Boolean status;
	
	private Boolean deleteStatus;
	
	private Timestamp createdOn;
	
	private Timestamp updatedOn;
	
	private String eligibilityCriteria;
	
	private String rolesAndResponsibility;
	
	private Double salaryAtProbation;
	
	private Integer serviceAgreement;
	
	private Boolean forcampusDrive;
	
	private LocalDateTime tentativeDate;
	
	private List<Stream> streams = new ArrayList<Stream>();
	private List<StudentBranch> branchs = new ArrayList<StudentBranch>();
	
	private Corporate corporate;
}
