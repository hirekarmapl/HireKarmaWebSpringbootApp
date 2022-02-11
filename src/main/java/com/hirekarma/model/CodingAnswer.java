package com.hirekarma.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.sun.istack.NotNull;

@Entity
@Table(name = "codinganswer")
public class CodingAnswer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull
	@Column(name = "id")
	private Long id;
	@Column(name = "codinganswer")
	private String testCases;
	@Column(name = "uid")
	private String uid;
	@Column(name = "q_uid")
	private String q_uid;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTestCases() {
		return testCases;
	}

	public void setTestCases(String testCases) {
		this.testCases = testCases;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getQ_uid() {
		return q_uid;
	}

	public void setQ_uid(String q_uid) {
		this.q_uid = q_uid;
	}

	@Override
	public String toString() {
		return "CodingAnswer [id=" + id + ", testCases=" + testCases + ", uid=" + uid + ", q_uid=" + q_uid + "]";
	}


}
