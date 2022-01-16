package com.hirekarma.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.sun.istack.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "SCREENING_QUESTIONS")
public class ScreeningQuestions implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@NotNull
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "QUESTIONS_ID")
	private Long questionsId;
	
	@Column(name = "SCREENING_ID")
	private Long screeningId;
	
	@Column(name = "QUESTIONS")
	private String questions;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "CREATED_ON")
	@CreationTimestamp
	private Timestamp createdOn;
	
	@Column(name = "UPDATED_ON")
	private Timestamp updatedOn;

}
