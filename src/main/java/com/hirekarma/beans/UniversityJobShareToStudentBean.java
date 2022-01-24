package com.hirekarma.beans;

import java.sql.Timestamp;
import java.util.List;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UniversityJobShareToStudentBean {

	private Long id;

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
	
	private Long cgpaId;

}