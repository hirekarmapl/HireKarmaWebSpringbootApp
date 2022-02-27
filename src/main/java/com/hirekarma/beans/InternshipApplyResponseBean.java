package com.hirekarma.beans;

import java.sql.Timestamp;

import com.hirekarma.model.Corporate;
import com.hirekarma.model.Internship;
import com.hirekarma.model.Job;

import lombok.Data;
import lombok.ToString;
@Data
@ToString
public class InternshipApplyResponseBean {

	private Long internshipApplyId;
	private Long studentId;
	private Long corporateId;
	private String hireReason;
	private String coverLetter;
	private String earliestJoiningDate;
	private Boolean applicatinStatus;
	private Timestamp createdOn;
	private Long internshipId;
	private Internship internship;
	private Corporate corporate;
}
