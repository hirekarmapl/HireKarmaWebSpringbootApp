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
@Table(name = "inputanswer")
public class InputAnswer {
	@Id
	@NotNull
	@Column(name = "id")
	private Long id;
	@Column(name = "inputanswer")
	private String inputAnswer;
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

	public String getInputAnswer() {
		return inputAnswer;
	}

	public void setInputAnswer(String inputAnswer) {
		this.inputAnswer = inputAnswer;
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


}
