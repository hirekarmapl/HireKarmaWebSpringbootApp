package com.hirekarma.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sun.istack.NotNull;

@Entity
@Table(name = "SCREENING_QUESTION_OPTIONS")
public class ScreeninQuestionOptions implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "SCREENING_OPTIONS_ID")
	@NotNull
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long screeningOptionsId;
	
	@Column(name = "SCREENING_TABLE_ID")
	private Long screeningTableId;
	
	@Column(name = "OPTIONS")
	private String options;

	public Long getScreeningOptionsId() {
		return screeningOptionsId;
	}

	public void setScreeningOptionsId(Long screeningOptionsId) {
		this.screeningOptionsId = screeningOptionsId;
	}

	public Long getScreeningTableId() {
		return screeningTableId;
	}

	public void setScreeningTableId(Long screeningTableId) {
		this.screeningTableId = screeningTableId;
	}

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	@Override
	public String toString() {
		return "ScreeninQuestionOptions [screeningOptionsId=" + screeningOptionsId + ", screeningTableId="
				+ screeningTableId + ", options=" + options + "]";
	}
}
