package com.hirekarma.beans;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.hirekarma.model.Corporate;
import com.hirekarma.model.Job;
import com.hirekarma.model.JobApply;
import com.hirekarma.model.Meet;
import com.hirekarma.model.Student;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class JobApplyResponseBean {

	private Long jobApplyId;
	private Long studentId;
	private Long jobId;
	private Long corporateId;
	private String hireReason;
	private String coverLetter;
	private String earliestJoiningDate;
	private Boolean deleteStatus;
	public Boolean applicationStatus;
	private Timestamp createdOn;
	private Timestamp updatedOn;
	private Job job;
	private Corporate corporate;
	private Student student;
	private JobApply jobApply;
	private StudentResponseBean studentResponseBean;
	public Boolean isHire;
	public Meet meet;
	
}
