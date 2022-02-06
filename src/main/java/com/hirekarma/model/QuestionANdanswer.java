package com.hirekarma.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="hire_karma_qandatable")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionANdanswer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull
	@Column(name="Id")
	private Long id;
	@Column(name="question")
	private String question;
	@Column(name="type")
	private String type;
    @Column(name="codingdescribtion")
    private String codingDescription; 
    @Column(name="corporate_id")
    private String corporateId;
    @Column(name="UID")
    private String uID;
    
    @Column(name="status")
    private String status;
    
	@OneToMany(targetEntity=MCQAnswer.class,cascade=CascadeType.ALL)
	@JoinColumn(name="uID")
	private List<MCQAnswer> mcqAnswer;
	
	@OneToOne(targetEntity=LongAnswer.class,cascade=CascadeType.ALL)
	@JoinColumn(name="id")
	private LongAnswer longAnswer;
	
	@OneToOne(targetEntity=InputAnswer.class,cascade=CascadeType.ALL)
	@JoinColumn(name="id")
	private InputAnswer inputAnswer;
	
	@OneToMany(targetEntity=CodingAnswer.class,cascade=CascadeType.ALL)
	@JoinColumn(name="uID")
	private List<CodingAnswer> codingAnswer;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCodingDescription() {
		return codingDescription;
	}
	public void setCodingDescription(String codingDescription) {
		this.codingDescription = codingDescription;
	}
	public String getCorporateId() {
		return corporateId;
	}
	public void setCorporateId(String corporateId) {
		this.corporateId = corporateId;
	}
	public String getuID() {
		return uID;
	}
	public void setuID(String uID) {
		this.uID = uID;
	}
	public List<MCQAnswer> getMcqAnswer() {
		return mcqAnswer;
	}
	public void setMcqAnswer(List<MCQAnswer> mcqAnswer) {
		this.mcqAnswer = mcqAnswer;
	}
	public LongAnswer getLongAnswer() {
		return longAnswer;
	}
	public void setLongAnswer(LongAnswer longAnswer) {
		this.longAnswer = longAnswer;
	}
	public InputAnswer getInputAnswer() {
		return inputAnswer;
	}
	public void setInputAnswer(InputAnswer inputAnswer) {
		this.inputAnswer = inputAnswer;
	}
	public List<CodingAnswer> getCodingAnswer() {
		return codingAnswer;
	}
	public void setCodingAnswer(List<CodingAnswer> codingAnswer) {
		this.codingAnswer = codingAnswer;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	
  
}
