package com.hirekarma.beans;

import java.util.List;

import com.google.gson.JsonObject;
import com.hirekarma.model.CodingAnswer;
import com.hirekarma.model.Corporate;
import com.hirekarma.model.InputAnswer;
import com.hirekarma.model.LongAnswer;
import com.hirekarma.model.MCQAnswer;
import com.hirekarma.model.OnlineAssessment;
import com.hirekarma.model.QuestionANdanswer;
import com.hirekarma.model.StudentOnlineAssessmentAnswer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
@Data
@ToString
@AllArgsConstructor
public class StudentOnlineAssessmentAnswerRequestBean {

	String questionId;
	JsonObject answer;
	
}
