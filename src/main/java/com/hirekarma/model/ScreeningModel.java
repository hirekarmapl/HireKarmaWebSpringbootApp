package com.hirekarma.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.sun.istack.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "SCREENING")
@Entity
@Data
@NoArgsConstructor
public class ScreeningModel implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "SCREENING_ID")
	@NotNull
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long screeningId;
	
	@Column(name = "CORPORATE_ID")
	private Long corporateId;
	
	@OneToMany
	@JoinColumn(updatable = false,insertable = false,name ="SCREENING_ID", referencedColumnName = "SCREENING_ID")
	private List<ScreeningQuestions> questionsList;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "CREATED_ON")
	@CreationTimestamp
	private Timestamp createdOn;
	
	@Column(name = "UPDATED_ON")
	private Timestamp updatedOn;

}
