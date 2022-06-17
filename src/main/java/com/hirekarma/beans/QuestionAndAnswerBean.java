package com.hirekarma.beans;

import java.util.List;

import com.hirekarma.model.Corporate;
import com.hirekarma.model.MCQAnswer;
import com.hirekarma.model.University;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
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
    private Corporate corporate;
    private String universityId;
    private String answer;
    private String adminId;
    private String uId;
    private University university;
	
}
