package com.hirekarma.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "STUDENT_BATCH")
//@Data
//@ToString
public class StudentBatch {

	@Id
	@Column(name = "BATCH_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "BATCH_NAME")
	private String batchName;
	
	@OneToMany(mappedBy = "studentBatch")
	@JsonIgnore
	private List<UniversityJobShareToStudent> universityJobShareToStudents;
	
	@OneToMany(mappedBy = "studentBatch")
	@JsonIgnore
	private List<Education> educations;

	public List<Education> getEducations() {
		return educations;
	}

	public void setEducations(List<Education> educations) {
		this.educations = educations;
	}

	@Override
	public String toString() {
		return "StudentBatch [id=" + id + ", batchName=" + batchName + "]";
	}

	public List<UniversityJobShareToStudent> getUniversityJobShareToStudents() {
		return universityJobShareToStudents;
	}

	public void setUniversityJobShareToStudents(List<UniversityJobShareToStudent> universityJobShareToStudents) {
		this.universityJobShareToStudents = universityJobShareToStudents;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBatchName() {
		return batchName;
	}

	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}

	
	
}
