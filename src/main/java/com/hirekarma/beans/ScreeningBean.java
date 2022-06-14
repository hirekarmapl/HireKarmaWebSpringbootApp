package com.hirekarma.beans;

import java.io.Serializable;
import java.util.List;

public class ScreeningBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long screeningTableId;
	private String questions;
	private Integer questionType;
	private Long corporateId;
	private Long universityId;
	private String slug;
	private List<String> options;
	
	public Long getUniversityId() {
		return universityId;
	}
	public void setUniversityId(Long universityId) {
		this.universityId = universityId;
	}
	public Long getScreeningTableId() {
		return screeningTableId;
	}
	public void setScreeningTableId(Long screeningTableId) {
		this.screeningTableId = screeningTableId;
	}
	public String getQuestions() {
		return questions;
	}
	public void setQuestions(String questions) {
		this.questions = questions;
	}
	public Integer getQuestionType() {
		return questionType;
	}
	public void setQuestionType(Integer questionType) {
		this.questionType = questionType;
	}
	public Long getCorporateId() {
		return corporateId;
	}
	public void setCorporateId(Long corporateId) {
		this.corporateId = corporateId;
	}
	public String getSlug() {
		return slug;
	}
	public void setSlug(String slug) {
		this.slug = slug;
	}
	public List<String> getOptions() {
		return options;
	}
	public void setOptions(List<String> options) {
		this.options = options;
	}
	
	@Override
	public String toString() {
		return "ScreeningBean [screeningTableId=" + screeningTableId + ", questions=" + questions + ", questionType="
				+ questionType + ", corporateId=" + corporateId + ", slug=" + slug + ", options=" + options + "]";
	}
}
