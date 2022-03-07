package com.hirekarma.beans;

import java.time.LocalDateTime;
import java.util.List;


public class OnlineAssessmentBean {

	
	List<Integer> questions;

//	same attributes
	String title;
	int totalMarks;
	int codingMarks;
	int qnaMarks;
	int mcqMarks;
	int paragraphMarks;
	int totalTime;
	String scheduledAt;
	LocalDateTime localDateTime;
//	end of same attributes
	
//	extra variable
	String onlineAssessmentSlug;
	
	
	
	public String getOnlineAssessmentSlug() {
		return onlineAssessmentSlug;
	}
	public void setOnlineAssessmentSlug(String onlineAssessmentSlug) {
		this.onlineAssessmentSlug = onlineAssessmentSlug;
	}
	public int getTotalTime() {
		return totalTime;
	}
	public void setTotalTime(int totalTime) {
		this.totalTime = totalTime;
	}
	public LocalDateTime getLocalDateTime() {
		return localDateTime;
	}
	public void setLocalDateTime(LocalDateTime localDateTime) {
		this.localDateTime = localDateTime;
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
	
	public String getScheduledAt() {
		return scheduledAt;
	}
	public void setScheduledAt(String scheduledAt) {
		this.scheduledAt = scheduledAt;
	}
	
	

}
