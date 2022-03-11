package com.hirekarma.beans;

import com.hirekarma.model.StudentBatch;
import com.hirekarma.model.StudentBranch;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class EducationBean {
	int id;
	String levelOfEducation;
	String field;
	String college;
	int startDateMonth;
	int startDateYear;
	int endDateMonth;
	int endDateYear;
	StudentBatch studentBatch;
	StudentBranch studentBranch;
	Long batchId;
	Long branchId;
}
