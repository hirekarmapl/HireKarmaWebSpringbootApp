package com.hirekarma.beans;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UniversityJobShareToStudentBean {

	private Long iD;

	private String createdBy;

	private Timestamp createdOn;

	private Long jobId;

	private List<Long> studentId;

	private Long universityId;

	private String updatedBy;

	private Timestamp updatedOn;

	private Boolean jobStatus;

	private String feedBack;

	private Boolean studentResponseStatus;
	
	private String response;
	
	private String token;
	
	private Long batchId;
	
	private Long branchId;
	
	private Double cgpaId;
	
	private Long shareJobId;
	
	private Boolean seen = false;
	
	private List<Integer> streamIds = new ArrayList<>();
	
	



}
