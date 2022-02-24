package com.hirekarma.beans;

import java.util.ArrayList;
import java.util.List;

import com.hirekarma.model.StudentBranch;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class StreamResponseBean {
	int id;
	String name;
	List<StudentBranch> selectedBranchesForJob = new ArrayList<StudentBranch>();
}
