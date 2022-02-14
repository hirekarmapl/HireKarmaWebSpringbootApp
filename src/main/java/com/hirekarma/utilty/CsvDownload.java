package com.hirekarma.utilty;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;

import com.hirekarma.beans.QuestionAndAnswerBean;
public class CsvDownload {
  public static ByteArrayInputStream tutorialsToCSV(List<QuestionAndAnswerBean> tutorials) {
    final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);  
    final  String[] CsvHeader = { "question", "type", "mcqanswer", "codingdescription","testcase","corporateid" };
    try (ByteArrayOutputStream out = new ByteArrayOutputStream();
        CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
    	csvPrinter.printRecord(CsvHeader);
      for (QuestionAndAnswerBean tutorial : tutorials) {
    	  String[] question=tutorial.getQuestion();
          StringBuffer s = new StringBuffer();
          for(int k = 0; k < question.length; k++) {
             s.append(question[k]);
             if(k==question.length-1) {
          	   
             }else {
          	   s.append(",");
             }
          }
          
          String stringArray[] = tutorial.getMcqAnswer();
          StringBuffer sb = new StringBuffer();
          for(int i = 0; i < stringArray.length; i++) {
             sb.append(stringArray[i]);
             if(i==stringArray.length-1) {
          	   
             }else {
          	   sb.append(",");
             }
          }
          String testcase[] = tutorial.getTestCase();
          StringBuffer sb1 = new StringBuffer();
          for(int j = 0; j < testcase.length; j++) {
             sb1.append(testcase[j]);
             if(j==testcase.length-1) {
          	   
             }else {
          	   sb1.append(",");
             }
          }
        List<String> data = Arrays.asList(
              String.valueOf(s),
              String.valueOf(tutorial.getType()),
            		  String.valueOf(sb.toString()),
            				  String.valueOf( tutorial.getCodingDescription()),
            						  String.valueOf(sb1.toString()),
            								  String.valueOf(tutorial.getCorporateId())
            );
        csvPrinter.printRecord(data);
      }
      csvPrinter.flush();
      return new ByteArrayInputStream(out.toByteArray());
    } catch (IOException e) {
      throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
    }
  }
//  public static void main(String args[]) {
//	  CsvDownload.tutorialsToCSV(new ArrayList());
//	  
//  }
}
