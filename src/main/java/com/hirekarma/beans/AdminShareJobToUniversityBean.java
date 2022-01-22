package com.hirekarma.beans;

import java.sql.Timestamp;
import java.util.List;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class AdminShareJobToUniversityBean {
	
	private Long shareJobId;
	
	private Long jobId;
	
	private List<Long> universityId;
	
	private String jobStatus;
	
	private Boolean universityResponseStatus;
	
	private String rejectionFeedback;
	
	private Timestamp createdOn;
	
	private String createdBy;
	
	private Timestamp updatedOn;
	
	private String updatedBy;
	
	private String response;
	
	private Long toatlSharedJob;


}
