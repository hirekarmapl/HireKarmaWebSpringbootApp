package com.hirekarma.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "STUDENT_BRANCH")
@Data
@ToString
public class StudentBranch {

	@Id
	@Column(name = "BRANCH_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "BRANCH_NAME")
	private String branchName;

}
