package com.hirekarma.beans;

import java.sql.Timestamp;
import java.util.List;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UniversityJobShareBean {

	private Long tableId;

	private String createdBy;

	private Timestamp createdOn;

	private Long jobId;

	private List<Long> studentId;

	private Long universityId;

	private String updatedBy;

	private Timestamp updatedOn;

	private String jobStatus;

	private String feedBack;

	private String studentResponseStatus;
	
	private String response;

}
