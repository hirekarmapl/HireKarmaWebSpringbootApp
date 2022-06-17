package com.hirekarma.utilty;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;
import com.hirekarma.beans.QuestionAndAnswerBean;
import com.hirekarma.model.Corporate;
import com.hirekarma.model.University;
public class ExcelHelper {
  public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
  static String[] HEADERs = { "question", "type", "mcqanswer", "codingdescription","testcase","corporateid" };
  static String SHEET = "Tutorials";
  public static boolean hasExcelFormat(MultipartFile file) {
	  System.out.println(file.getContentType());
    if (!TYPE.equals(file.getContentType())) {
      return false;
    }
    return true;
  }
  public static List<QuestionAndAnswerBean> excelToTutorials(InputStream is,Corporate corporate,University university) {
    try {
    	System.out.println(is.toString());
      Workbook workbook = new XSSFWorkbook(is);
      Sheet sheet = workbook.getSheetAt(0);
      String sheetName=sheet.getSheetName();
      System.out.println(sheet.getSheetName());
      Iterator<Row> rows = sheet.iterator();
      List<QuestionAndAnswerBean> tutorials = new ArrayList<QuestionAndAnswerBean>();
      int rowNumber = 0;
      while (rows.hasNext()) {
        Row currentRow = rows.next();
        // skip header
        if (rowNumber == 0) {
          rowNumber++;
          continue;
        }
        Iterator<Cell> cellsInRow = currentRow.iterator();
        QuestionAndAnswerBean tutorial = new QuestionAndAnswerBean();
        int cellIdx = 0;
				while (cellsInRow.hasNext()) {
					Cell currentCell = cellsInRow.next();
					if (sheetName.equalsIgnoreCase("QNA")) {

						switch (cellIdx) {
						case 0:
							String[] question = currentCell.getStringCellValue().split(",");
							tutorial.setQuestion(question);
							break;

						default:
							break;
						}
						if(corporate!=null)
						{
							tutorial.setCorporate(corporate);
						}
						else if(university!=null) {
							tutorial.setUniversity(university);
						}
						tutorial.setType("QNA");
					} else if (sheetName.equalsIgnoreCase("Input")) {

						switch (cellIdx) {
						case 0:
							String[] question = currentCell.getStringCellValue().split(",");
							tutorial.setQuestion(question);
							break;
						default:
							break;
						}
						if(corporate!=null)
						{
							tutorial.setCorporate(corporate);
						}
						else if(university!=null) {
							tutorial.setUniversity(university);
						}
						tutorial.setType("Input");
					} else if (sheetName.equalsIgnoreCase("MCQ")) {

						switch (cellIdx) {
						case 0:
							String[] question = currentCell.getStringCellValue().split(",");
							tutorial.setQuestion(question);
							break;
						case 1:
							String[] mcqAnswer = currentCell.getStringCellValue().split(",");
							tutorial.setMcqAnswer(mcqAnswer);
							break;
						default:
							break;
						}
						if(corporate!=null)
						{
							tutorial.setCorporate(corporate);
						}
						else if(university!=null) {
							tutorial.setUniversity(university);
						}
						tutorial.setType("MCQ");
					} else if (sheetName.equalsIgnoreCase("Coding")) {

						switch (cellIdx) {
						case 0:
							String[] question = currentCell.getStringCellValue().split(",");
							tutorial.setQuestion(question);
							break;
						case 1:
							tutorial.setCodingDescription(currentCell.getStringCellValue());
							break;
						case 2:
							String[] testCases = currentCell.getStringCellValue().split(",");
							tutorial.setTestCase(testCases);
							break;
						default:
							break;
						}

						if(corporate!=null)
						{
							tutorial.setCorporate(corporate);
						}
						else if(university!=null) {
							tutorial.setUniversity(university);
						}
						tutorial.setType("Coding");
					} else {
						switch (cellIdx) {
						case 0:
							String[] question = currentCell.getStringCellValue().split(",");
							tutorial.setQuestion(question);
							break;
						case 1:
							tutorial.setType(currentCell.getStringCellValue());
							break;
						case 2:
							String[] mcqAnswer = currentCell.getStringCellValue().split(",");
							tutorial.setMcqAnswer(mcqAnswer);
							break;
						case 3:
							tutorial.setCodingDescription(currentCell.getStringCellValue());
							break;
						case 4:
							String[] testCases = currentCell.getStringCellValue().split(",");
							tutorial.setTestCase(testCases);
							break;
						default:
							break;
						}
						if(corporate!=null)
						{
							tutorial.setCorporate(corporate);
						}
						else if(university!=null) {
							tutorial.setUniversity(university);
						}
					}
					cellIdx++;
				}
        tutorials.add(tutorial);
      }
      workbook.close();
      return tutorials;
    } catch (IOException e) {
      throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
    }
  }

}