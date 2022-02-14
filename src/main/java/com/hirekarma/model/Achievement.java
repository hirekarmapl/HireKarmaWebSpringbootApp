package com.hirekarma.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Achievement {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int id;
	int students;
	int colleges;
	int companies;
	int hardWorkers;
	public Achievement(int id, int students, int colleges, int companies, int hardWorkers) {
		super();
		this.id = id;
		this.students = students;
		this.colleges = colleges;
		this.companies = companies;
		this.hardWorkers = hardWorkers;
	}
	public Achievement() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getStudents() {
		return students;
	}
	public void setStudents(int students) {
		this.students = students;
	}
	public int getColleges() {
		return colleges;
	}
	public void setColleges(int colleges) {
		this.colleges = colleges;
	}
	public int getCompanies() {
		return companies;
	}
	public void setCompanies(int companies) {
		this.companies = companies;
	}
	public int getHardWorkers() {
		return hardWorkers;
	}
	public void setHardWorkers(int hardWorkers) {
		this.hardWorkers = hardWorkers;
	}
	
}
