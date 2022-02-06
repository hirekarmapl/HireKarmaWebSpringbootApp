package com.hirekarma.serviceimpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hirekarma.beans.QuestionAndAnswerBean;
import com.hirekarma.service.QuestionAndANswerService;
import com.hirekarma.utilty.ExcelHelper;

public class ExcelService {
  public List<QuestionAndAnswerBean> save(MultipartFile file) {
	  List<QuestionAndAnswerBean> tutorials=new ArrayList();
    try {
    	 
       tutorials = ExcelHelper.excelToTutorials(file.getInputStream());
     // service.CreateQuestionAndAnswer(tutorials);
    } catch (IOException e) {
      throw new RuntimeException("fail to store excel data: " + e.getMessage());
    }
    return tutorials;
  }
}
