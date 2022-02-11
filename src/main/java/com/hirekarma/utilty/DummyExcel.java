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
public class DummyExcel {
  public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
  
  //static String SHEET = "Tutorials";
  public static ByteArrayInputStream tutorialsToExcel(List<QuestionAndAnswerBean> tutorials, String[] HEADERs,String SHEET) {
	   //String[] HEADERs = {"question", "type", "mcqanswer", "codingdescription","testcase","corporateid" };
    try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
      Sheet sheet = workbook.createSheet(SHEET);
      // Header
      Row headerRow = sheet.createRow(0);
      for (int col = 0; col < HEADERs.length; col++) {
        Cell cell = headerRow.createCell(col);
        cell.setCellValue(HEADERs[col]);
      }
      
      workbook.write(out);
      return new ByteArrayInputStream(out.toByteArray());
    } catch (IOException e) {
      throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
    }
  }
}
