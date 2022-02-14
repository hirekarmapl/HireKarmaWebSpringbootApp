package com.hirekarma.model;

import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class Client {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	int id;
	
	public int getId() {
		return id;
	}

	public Client(int id, String name, byte[] logo) {
		super();
		this.id = id;
		this.name = name;
		this.logo = logo;
	}

	@Override
	public String toString() {
		return "Client [id=" + id + ", name=" + name + ", logo=" + Arrays.toString(logo) + "]";
	}

	public Client() {
		super();
		// TODO Auto-generated constructor stub
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

	public byte[] getLogo() {
		return logo;
	}

	public void setLogo(byte[] logo) {
		this.logo = logo;
	}

	@Column(unique = true)
	String name;
	
	@Lob
	byte[] logo;
	
	
}
