package com.hirekarma.beans;

import com.hirekarma.model.Job;
import com.hirekarma.model.StudentBatch;
import com.hirekarma.model.StudentBranch;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UniversityShareJobToStudentResponse {

	private Long shareJobId;
	private StudentBatch studentBatch;
	private StudentBranch studentBranch;
	private Job job;
	
}
