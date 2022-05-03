package com.hirekarma.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

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
	
	@ManyToOne
	University university;
	
	String title;
	int totalMarks;
	int codingMarks;
	int qnaMarks;
	int mcqMarks;
	int paragraphMarks;
	int totalTime;
	boolean publicly_available;
	
	@Column(nullable = false)
	Boolean deleteStatus = false;
	
	LocalDateTime localDateTime;

	@ManyToMany
	public List<QuestionANdanswer> questionANdanswers;
	
//	for sending online assesment to student
	@OneToMany(mappedBy = "onlineAssessment")
	@JsonIgnore
	Set<StudentOnlineAssessment> studentOnlineAssessments;
	
//	for storing the result
	@JsonIgnore
	@OneToMany(mappedBy = "onlineAssessment")
	private List<StudentOnlineAssessmentAnswer> studentOnlineAssessmentAnswers;
	
	
public List<StudentOnlineAssessmentAnswer> getStudentOnlineAssessmentAnswers() {
		return studentOnlineAssessmentAnswers;
	}
	public void setStudentOnlineAssessmentAnswers(List<StudentOnlineAssessmentAnswer> studentOnlineAssessmentAnswers) {
		this.studentOnlineAssessmentAnswers = studentOnlineAssessmentAnswers;
	}
	//	for sending online assesment to student	
	public Set<StudentOnlineAssessment> getStudentOnlineAssessments() {
		return studentOnlineAssessments;
	}
	public void setStudentOnlineAssessments(Set<StudentOnlineAssessment> studentOnlineAssessments) {
		this.studentOnlineAssessments = studentOnlineAssessments;
	}
	public List<QuestionANdanswer> getQuestionANdanswers() {
		return questionANdanswers;
	}
	public void setQuestionANdanswers(List<QuestionANdanswer> questionANdanswers) {
		this.questionANdanswers = questionANdanswers;
	}
	 
	public Boolean getDeleteStatus() {
		return deleteStatus;
	}
	public void setDeleteStatus(Boolean deleteStatus) {
		this.deleteStatus = deleteStatus;
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
	public University getUniversity() {
		return university;
	}
	public void setUniversity(University university) {
		this.university = university;
	}
	public boolean isPublicly_available() {
		return publicly_available;
	}
	public void setPublicly_available(boolean publicly_available) {
		this.publicly_available = publicly_available;
	}


	
	
}
