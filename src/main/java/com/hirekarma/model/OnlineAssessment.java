package com.hirekarma.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.ManyToAny;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class OnlineAssessment implements Serializable {


	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(columnDefinition = "CHAR(32)")
	@Id
	String slug;
	
	@ManyToOne
	Corporate corporate;
	String title;
	int totalMarks;
	int codingMarks;
	int qnaMarks;
	int mcqMarks;
	int paragraphMarks;
	int totalTime;
	
	
	LocalDateTime localDateTime;
	
	@ManyToMany
	public List<QuestionANdanswer> questionANdanswers;
	
	@ManyToMany
	public List<Student> students;
	
	
	public List<Student> getStudents() {
		return students;
	}
	
	public void setStudents(List<Student> students) {
		this.students = students;
	}
	public List<QuestionANdanswer> getQuestionANdanswers() {
		return questionANdanswers;
	}
	public void setQuestionANdanswers(List<QuestionANdanswer> questionANdanswers) {
		this.questionANdanswers = questionANdanswers;
	}
	
	
	public Corporate getCorporate() {
		return corporate;
	}
	public void setCorporate(Corporate corporate) {
		this.corporate = corporate;
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
	public int getTotalTime() {
		return totalTime;
	}
	public void setTotalTime(int totalTime) {
		this.totalTime = totalTime;
	}
	public String getSlug() {
		return slug;
	}
	public void setSlug(String slug) {
		this.slug = slug;
	}
	public LocalDateTime getLocalDateTime() {
		return localDateTime;
	}
	public void setLocalDateTime(LocalDateTime localDateTime) {
		this.localDateTime = localDateTime;
	}
	@Override
	public String toString() {
		return "OnlineAssessment [slug=" + slug + ", corporate=" + corporate + ", title=" + title + ", totalMarks="
				+ totalMarks + ", codingMarks=" + codingMarks + ", qnaMarks=" + qnaMarks + ", mcqMarks=" + mcqMarks
				+ ", paragraphMarks=" + paragraphMarks + ", totalTime=" + totalTime + ", localDateTime=" + localDateTime
				+ ", questionANdanswers="  + "]";
	}

	
	
}
