package com.hirekarma.service;

import com.hirekarma.beans.ScreeningBean;

public interface ScreeningService {
	
	public ScreeningBean insertScreeningQuestions(ScreeningBean screeningBean);
	public ScreeningBean getScreeningQuestionsByCorporateId(Long corporateId);

}
