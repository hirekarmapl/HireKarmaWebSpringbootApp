package com.hirekarma.beans;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UniversitySharedJobList {
	
	private Long jobId;
	
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
	
	private Long sharedJobId;
	
	private Boolean studentResponse;
}
