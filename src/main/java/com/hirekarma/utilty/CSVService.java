package com.hirekarma.utilty;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.hirekarma.beans.QuestionAndAnswerBean;


public class CSVService {

  public List<QuestionAndAnswerBean> save(MultipartFile file) {
	  List<QuestionAndAnswerBean> tutorials=new ArrayList<>();
    try {
      tutorials = CSVHelper.csvToTutorials(file.getInputStream());
    } catch (IOException e) {
      throw new RuntimeException("fail to store csv data: " + e.getMessage());
    }
    return tutorials;
  }

}