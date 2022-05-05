package com.hirekarma.beans;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class StudentOnlineAssessmentAnswerBean {

	Long studentId;
	String onlineAssessmentSlug;
	String studentAssesmentSlug;
}
