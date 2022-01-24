package com.hirekarma.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "STUDENT_BATCH")
@Data
@ToString
public class StudentBatch {

	@Id
	@Column(name = "BATCH_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "BATCH_NAME")
	private String batchName;

}
