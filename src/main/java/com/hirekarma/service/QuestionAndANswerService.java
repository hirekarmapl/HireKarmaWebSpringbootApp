package com.hirekarma.service;

import java.util.List;

import com.hirekarma.beans.QuestionAndAnswerBean;
import com.hirekarma.beans.QuestionAndAnswerResponseBean;
import com.hirekarma.model.QuestionANdanswer;

public interface QuestionAndANswerService {

	QuestionAndAnswerResponseBean CreateQuestionAndAnswer(List<QuestionAndAnswerBean> qAndA);

	QuestionAndAnswerResponseBean updateQuestionAndAnswer(List<QuestionAndAnswerBean> qAndA);

	QuestionANdanswer getQNADetail(String qNA_id);

	QuestionAndAnswerResponseBean deleteQNADetail(String qNA_id);

}
