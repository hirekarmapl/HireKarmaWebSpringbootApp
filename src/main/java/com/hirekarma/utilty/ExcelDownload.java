package com.hirekarma.utilty;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.hirekarma.beans.QuestionAndAnswerBean;
public class ExcelDownload {
  public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
  static String[] HEADERs = {"question", "type", "mcqanswer", "codingdescription","testcase","corporateid" };
  static String SHEET = "Tutorials";
  public static ByteArrayInputStream tutorialsToExcel(List<QuestionAndAnswerBean> tutorials) {
    try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
      Sheet sheet = workbook.createSheet(SHEET);
      // Header
      Row headerRow = sheet.createRow(0);
      for (int col = 0; col < HEADERs.length; col++) {
        Cell cell = headerRow.createCell(col);
        cell.setCellValue(HEADERs[col]);
      }
      int rowIdx = 1;
      for (QuestionAndAnswerBean tutorial : tutorials) {
        Row row = sheet.createRow(rowIdx++);
        String[] question=tutorial.getQuestion();
        StringBuffer s = new StringBuffer();
        for(int k = 0; k < question.length; k++) {
           s.append(question[k]);
           if(k==question.length-1) {
        	   
           }else {
        	   s.append(",");
           }
        }
        row.createCell(0).setCellValue(s.toString());
        row.createCell(1).setCellValue(tutorial.getType());
        String stringArray[] = tutorial.getMcqAnswer();
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < stringArray.length; i++) {
           sb.append(stringArray[i]);
           if(i==stringArray.length-1) {
        	   
           }else {
        	   sb.append(",");
           }
        }
        row.createCell(2).setCellValue(sb.toString());
        row.createCell(3).setCellValue(tutorial.getCodingDescription());
        String testcase[] = tutorial.getTestCase();
        StringBuffer sb1 = new StringBuffer();
        for(int j = 0; j < testcase.length; j++) {
           sb1.append(testcase[j]);
           if(j==testcase.length-1) {
        	   
           }else {
        	   sb1.append(",");
           }
        }
        row.createCell(4).setCellValue(sb1.toString());
       
        row.createCell(5).setCellValue(tutorial.getCorporate()!=null?tutorial.getCorporate().getCorporateName():"");
      }
      workbook.write(out);
      return new ByteArrayInputStream(out.toByteArray());
    } catch (IOException e) {
      throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
    }
  }
}
