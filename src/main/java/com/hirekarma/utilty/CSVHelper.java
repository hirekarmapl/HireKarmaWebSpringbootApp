package com.hirekarma.utilty;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import com.hirekarma.beans.QuestionAndAnswerBean;
public class CSVHelper {
  public static String TYPE = "text/csv";
  static String[] HEADERs = { "question", "type", "mcqanswer", "codingdescription","testcase","corporateid" };
  public static boolean hasCSVFormat(MultipartFile file) {
    if (!TYPE.equals(file.getContentType())) {
      return false;
    }
    return true;
  }
  public static List<QuestionAndAnswerBean> csvToTutorials(InputStream is) {
    try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        CSVParser csvParser = new CSVParser(fileReader,
            CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {
      List<QuestionAndAnswerBean> tutorials = new ArrayList<QuestionAndAnswerBean>();
      Iterable<CSVRecord> csvRecords = csvParser.getRecords();
      for (CSVRecord csvRecord : csvRecords) {
    	  QuestionAndAnswerBean tutorial = new QuestionAndAnswerBean();
    	  String [] question=csvRecord.get("question").split(",");
          tutorial.setQuestion(question);
          tutorial.setType(csvRecord.get("type"));
      	  String [] mcqAnswer=csvRecord.get("mcqanswer").split(",");
          tutorial.setMcqAnswer(mcqAnswer);
          tutorial.setCodingDescription(csvRecord.get("codingdescription"));
      	  String [] testCases=csvRecord.get("testcase").split(",");
          tutorial.setTestCase(testCases);
          tutorial.setCorporateId(csvRecord.get("corporateid"));

        tutorials.add(tutorial);
      }
      return tutorials;
    } catch (IOException e) {
      throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
    }
  }
}