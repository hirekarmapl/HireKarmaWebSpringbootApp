package com.hirekarma.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.hirekarma.beans.QuestionAndAnswerBean;
import com.hirekarma.beans.QuestionAndAnswerResponseBean;
import com.hirekarma.model.Corporate;
import com.hirekarma.model.QuestionANdanswer;

public interface QuestionAndANswerService {

	QuestionAndAnswerResponseBean CreateQuestionAndAnswer(List<QuestionAndAnswerBean> qAndA,Corporate corporate);

	QuestionAndAnswerResponseBean updateQuestionAndAnswer(List<QuestionAndAnswerBean> qAndA,Corporate corporate);

	QuestionANdanswer getQNADetail(String qNA_id);

	QuestionAndAnswerResponseBean deleteQNADetail(String qNA_id);

	ResponseEntity<QuestionAndAnswerResponseBean> uploadFile(MultipartFile file,Corporate corporate);

	ResponseEntity<Resource> downloadFile(String type);

	ResponseEntity<Resource> downloadDummyFile(String type);
	
	List<QuestionANdanswer> getQNAByType(String type);

}
