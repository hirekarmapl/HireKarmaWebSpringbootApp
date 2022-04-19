package com.hirekarma.model;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;

@Entity
@Table(name = "SCREENING_TABLE")
public class ScreeningEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "SCREENING_TABLE_ID")
	@NotNull
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long screeningTableId;
	
	@Column(name = "QUESTIONS")
	private String questions;
	
	@Column(name = "QUESTION_TYPE")
	private Integer questionType;
	
	@Column(name = "CORPORATE_ID")
	private Long corporateId;
	

	private Long universityId;
	
	@Column(name = "SLUG")
	private String slug;
	
	@ManyToMany(mappedBy = "screeningEntities")
	@JsonIgnore
	private Set<ScreeningEntityParent> screeningEntityParents;
	
	@OneToMany
	@JoinColumn(updatable = false, insertable = false, name = "SCREENING_TABLE_ID", referencedColumnName = "SCREENING_TABLE_ID")
	private List<ScreeninQuestionOptions> screeninQuestionOptions;

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

	public List<ScreeninQuestionOptions> getScreeninQuestionOptions() {
		return screeninQuestionOptions;
	}

	public void setScreeninQuestionOptions(List<ScreeninQuestionOptions> screeninQuestionOptions) {
		this.screeninQuestionOptions = screeninQuestionOptions;
	}
	



	public Long getUnivesityId() {
		return universityId;
	}

	public void setUnivesityId(Long univesityId) {
		this.universityId = univesityId;
	}

	public Set<ScreeningEntityParent> getScreeningEntityParents() {
		return screeningEntityParents;
	}

	public void setScreeningEntityParents(Set<ScreeningEntityParent> screeningEntityParents) {
		this.screeningEntityParents = screeningEntityParents;
	}

	@Override
	public String toString() {
		return "ScreeningEntity [screeningTableId=" + screeningTableId + ", questions=" + questions + ", questionType="
				+ questionType + ", corporateId=" + corporateId + ", slug=" + slug + ", screeninQuestionOptions="
				+ screeninQuestionOptions + "]";
	}
}
