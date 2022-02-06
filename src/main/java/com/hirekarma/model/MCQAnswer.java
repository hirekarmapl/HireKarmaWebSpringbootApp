package com.hirekarma.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sun.istack.NotNull;

@Entity
@Table(name = "mcqanswer")
public class MCQAnswer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull
	@Column(name = "id")
	private Long id;
	@Column(name = "mcqanswer")
	private String mcqAnswer;
	@Column(name = "uid")
	private String uid;
	@Column(name="q_uid")
	private String q_uid;
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMcqAnswer() {
		return mcqAnswer;
	}

	public void setMcqAnswer(String mcqAnswer) {
		this.mcqAnswer = mcqAnswer;
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
		return "MCQAnswer [id=" + id + ", mcqAnswer=" + mcqAnswer + ", uid=" + uid + ", q_uid=" + q_uid + "]";
	}

	
}
