package com.hirekarma.beans;

import java.util.List;


public class OnlineAssessmentBean {

	
	List<Integer> questions;
	String title;
	int totalMarks;
	int codingMarks;
	int qnaMarks;
	
	int mcqMarks;
	int paragraphMarks;
	int onlineAssessmentId;
	
	public int getOnlineAssessmentId() {
		return onlineAssessmentId;
	}
	public void setOnlineAssessmentId(int onlineAssessmentId) {
		this.onlineAssessmentId = onlineAssessmentId;
	}
	public List<Integer> getQuestions() {
		return questions;
	}
	public void setQuestions(List<Integer> questions) {
		this.questions = questions;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getTotalMarks() {
		return totalMarks;
	}
	public void setTotalMarks(int totalMarks) {
		this.totalMarks = totalMarks;
	}
	public int getCodingMarks() {
		return codingMarks;
	}
	public void setCodingMarks(int codingMarks) {
		this.codingMarks = codingMarks;
	}
	public int getQnaMarks() {
		return qnaMarks;
	}
	public void setQnaMarks(int qnaMarks) {
		this.qnaMarks = qnaMarks;
	}
	public int getMcqMarks() {
		return mcqMarks;
	}
	public void setMcqMarks(int mcqMarks) {
		this.mcqMarks = mcqMarks;
	}
	public int getParagraphMarks() {
		return paragraphMarks;
	}
	public void setParagraphMarks(int paragraphMarks) {
		this.paragraphMarks = paragraphMarks;
	}
	@Override
	public String toString() {
		return "OnlineAssessmentBean [questions=" + questions + ", title=" + title + ", totalMarks=" + totalMarks
				+ ", codingMarks=" + codingMarks + ", qnaMarks=" + qnaMarks + ", mcqMarks=" + mcqMarks
				+ ", paragraphMarks=" + paragraphMarks + ", onlineAssessmentId=" + onlineAssessmentId + "]";
	}
	

}
