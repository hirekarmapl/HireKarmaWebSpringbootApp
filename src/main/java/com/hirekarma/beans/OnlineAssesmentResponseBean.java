package com.hirekarma.beans;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class OnlineAssesmentResponseBean {
	String title;
	int totalMarks;
	int codingMarks;
	int qnaMarks;
	int mcqMarks;
	int paragraphMarks;
	int totalTime;
	String slug;
	List<QuestionAndAnswerStudentResponseBean> questionAndAnswerStudentResponseBeans;
	LocalDateTime localDateTime;
	
	
}
