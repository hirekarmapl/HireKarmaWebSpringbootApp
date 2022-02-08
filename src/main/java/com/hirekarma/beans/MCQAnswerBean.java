package com.hirekarma.beans;

import java.util.List;

public class MCQAnswerBean {
	private Long id;
	private List<String> mcqAnswer;
	private String uid;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public List<String> getMcqAnswer() {
		return mcqAnswer;
	}
	public void setMcqAnswer(List<String> mcqAnswer) {
		this.mcqAnswer = mcqAnswer;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}

}
