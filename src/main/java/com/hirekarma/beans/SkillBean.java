package com.hirekarma.beans;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.GeneratorType;

import com.hirekarma.model.UserProfile;


public class SkillBean {

	public int id;

	public String name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public SkillBean(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public SkillBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	


	

	

	
	
}
