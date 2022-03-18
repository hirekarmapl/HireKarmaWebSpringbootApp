package com.hirekarma.beans;

import java.util.List;

import com.hirekarma.model.Education;
import com.hirekarma.model.Skill;
import com.hirekarma.model.Stream;
import com.hirekarma.model.StudentBatch;
import com.hirekarma.model.StudentBranch;

import lombok.Data;
import lombok.ToString;
@Data
@ToString
public class StudentResponseBean {
	private Long studentId;
	private Long userId;
	private String studentName;
	private Long studentPhoneNumber;
	private String studentEmail;
	private Long universityId;
	private String studentAddress;
	private Long branch;
	private Boolean status;
	private Boolean profileUpdationStatus =  false;
	private String imageUrl;
	private Double cgpa;
	private Long batch;
	private Long streamId;
	private StudentBatch studentBatch;
	private StudentBranch studentBranch;
	private Stream stream;
	
	private List<Skill> skills;
	private List<Education> educations;
}
