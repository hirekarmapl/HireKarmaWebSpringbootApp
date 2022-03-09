package com.hirekarma.beans;

import java.util.List;

import com.hirekarma.model.CodingAnswer;
import com.hirekarma.model.Corporate;
import com.hirekarma.model.InputAnswer;
import com.hirekarma.model.LongAnswer;
import com.hirekarma.model.MCQAnswer;
import com.hirekarma.model.OnlineAssessment;
import com.hirekarma.model.QuestionANdanswer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class QuestionAndAnswerStudentResponseBean {
	private Long id;
	private String question;
	private String type;
	private String codingDescription; 
	private List<MCQAnswer> mcqAnswer;
	private List<CodingAnswer> codingAnswer;
	private String uID;
}
