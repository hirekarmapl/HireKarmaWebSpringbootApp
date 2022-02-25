package com.hirekarma.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "STUDENT_BRANCH")
//@Data
//@ToString
public class StudentBranch {

	@Id
	@Column(name = "BRANCH_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "BRANCH_NAME")
	private String branchName;

	
	@ManyToMany(mappedBy = "branchs")
	@JsonIgnore
	List<Job> jobs = new ArrayList<Job>();


	public Long getId() {
		return id;
	}

	

	public void setId(Long id) {
		this.id = id;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}





	public List<Job> getJobs() {
		return jobs;
	}



	public void setJobs(List<Job> jobs) {
		this.jobs = jobs;
	}
	


	

}
