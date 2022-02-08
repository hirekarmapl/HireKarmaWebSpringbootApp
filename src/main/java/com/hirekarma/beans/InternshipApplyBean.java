package com.hirekarma.beans;

import java.sql.Timestamp;

import lombok.Data;

import lombok.ToString;

@Data
@ToString
public class InternshipApplyBean {
	
	private Long internshipApplyId;
	private Long studentId;
	private Long corporateId;
	private String hireReason;
	private String coverLetter;
	private String earliestJoiningDate;
	private Boolean deleteStatus;
	private Boolean applicatinStatus;
	private Timestamp createdOn;
	private Timestamp updatedOn;
	
}
