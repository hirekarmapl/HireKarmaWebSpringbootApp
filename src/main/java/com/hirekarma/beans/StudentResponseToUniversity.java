package com.hirekarma.beans;

import com.hirekarma.model.Job;
import com.hirekarma.model.Student;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class StudentResponseToUniversity {
	
	public Job job;
	
	public Student student;
	
	public String studentResponse;
	
	public String studentFeedBack;

}
