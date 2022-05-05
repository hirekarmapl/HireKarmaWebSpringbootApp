package com.hirekarma.beans;

import java.util.ArrayList;
import java.util.List;

import com.hirekarma.model.Stream;
import com.hirekarma.model.StudentBranch;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class AdminSharedJobList {
	
	private String universityResponseStatus;
	
	private String shareJobId;
	
	private String jobTitle;
	
	private String category;
	
	private String jobType;
	
	private boolean wfhCheckbox;
	
	private String skills;
	
	private String city;
	
	private String openings;
	
	private String salary;
	
	private String about;
	
	private String description;
	
	private String jobId;
	
	private String corporateId;
	
	private String descriptionFileUrl;
private String eligibilityCriteria;
	
	private String rolesAndResponsibility;
	private List<Stream> streams= new ArrayList<>();
	private List<StudentBranch> branchs= new ArrayList<>();
	

}
