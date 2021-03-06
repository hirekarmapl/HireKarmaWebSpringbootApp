package com.hirekarma.beans;

import java.sql.Timestamp;
import java.util.List;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class JobApplyBean {
	
	private Long jobApplyId;
	private Long studentId;
	private String hireReason;
	private String coverLetter;
	private String earliestJoiningDate;
	private Boolean deleteStatus;
	private Timestamp createdOn;
	private Timestamp updatedOn;
	private Long corporateId;
	private Long jobId;
	private List<Long> jobApplyIds;
	
}
