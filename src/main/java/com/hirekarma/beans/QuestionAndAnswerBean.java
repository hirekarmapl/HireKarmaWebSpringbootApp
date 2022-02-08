package com.hirekarma.beans;

import java.util.List;

import com.hirekarma.model.MCQAnswer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionAndAnswerBean {
	private Long id;
	private String[] question;
	private String type;
	private String longAnswer;
	private String[] mcqAnswer;
	private List<MCQAnswer> mcqAns;
	private String inputAnswer;
	private String codingAnswer;
    private String codingDescription; 
    private String[] testCase;
    private String corporateId;
    private String universityId;
    private String adminId;
    private String uId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String[] getQuestion() {
		return question;
	}
	public void setQuestion(String[] question) {
		this.question = question;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getLongAnswer() {
		return longAnswer;
	}
	public void setLongAnswer(String longAnswer) {
		this.longAnswer = longAnswer;
	}	

	public String getInputAnswer() {
		return inputAnswer;
	}
	public void setInputAnswer(String inputAnswer) {
		this.inputAnswer = inputAnswer;
	}
	public String[] getMcqAnswer() {
		return mcqAnswer;
	}
	public void setMcqAnswer(String[] mcqAnswer) {
		this.mcqAnswer = mcqAnswer;
	}
	public String getCodingDescription() {
		return codingDescription;
	}
	public void setCodingDescription(String codingDescription) {
		this.codingDescription = codingDescription;
	}	
	public String[] getTestCase() {
		return testCase;
	}
	public void setTestCase(String[] testCase) {
		this.testCase = testCase;
	}
	public String getCorporateId() {
		return corporateId;
	}
	public void setCorporateId(String corporateId) {
		this.corporateId = corporateId;
	}
	public String getUniversityId() {
		return universityId;
	}
	public void setUniversityId(String universityId) {
		this.universityId = universityId;
	}
	public String getAdminId() {
		return adminId;
	}
	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}
	public String getuId() {
		return uId;
	}
	public void setuId(String uId) {
		this.uId = uId;
	}
	public String getCodingAnswer() {
		return codingAnswer;
	}
	public void setCodingAnswer(String codingAnswer) {
		this.codingAnswer = codingAnswer;
	}
	public List<MCQAnswer> getMcqAns() {
		return mcqAns;
	}
	public void setMcqAns(List<MCQAnswer> mcqAns) {
		this.mcqAns = mcqAns;
	}
	
}
