package com.hirekarma.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sun.istack.NotNull;

@Entity
public class Skill {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull
	public int id;

	public String name;

	@ManyToMany(mappedBy = "skills")

	@JsonIgnore
	List<UserProfile> users =  new ArrayList<UserProfile>();

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

	public List<UserProfile> getUsers() {
		return users;
	}

	public void setUsers(List<UserProfile> users) {
		this.users = users;
	}

	public Skill(int id, String name, List<UserProfile> users) {
		super();
		this.id = id;
		this.name = name;
		this.users = users;
	}

	public Skill() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Skill [id=" + id + ", name=" + name ;
	}
	


}
