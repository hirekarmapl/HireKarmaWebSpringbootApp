package com.hirekarma.beans;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.hirekarma.model.Stream;
import com.hirekarma.model.StudentBranch;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class JobResponseBean {

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

	private byte[] descriptionFile;
	
	private String eligibilityCriteria;
	
	private String rolesAndResponsibility;
	
	private Double salaryAtProbation;
	
	private Integer serviceAgreement;
	
	private List<Stream> streams= new ArrayList<>();
	private List<StudentBranch> branchs= new ArrayList<>();
	
	private Boolean forcampusDrive;
}
