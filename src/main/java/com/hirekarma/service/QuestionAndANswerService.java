package com.hirekarma.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.hirekarma.beans.QuestionAndAnswerBean;
import com.hirekarma.beans.QuestionAndAnswerResponseBean;
import com.hirekarma.model.QuestionANdanswer;

public interface QuestionAndANswerService {

	QuestionAndAnswerResponseBean CreateQuestionAndAnswer(List<QuestionAndAnswerBean> qAndA);

	QuestionAndAnswerResponseBean updateQuestionAndAnswer(List<QuestionAndAnswerBean> qAndA);

	QuestionANdanswer getQNADetail(String qNA_id);

	QuestionAndAnswerResponseBean deleteQNADetail(String qNA_id);

	ResponseEntity<QuestionAndAnswerResponseBean> uploadFile(MultipartFile file);

}
