package com.hirekarma.utilty;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import com.hirekarma.beans.UserBean;

public class StudentDataExcelGenerator extends AbstractXlsView{

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		HSSFWorkbook hssfWorkbook = (HSSFWorkbook)workbook;
		String[] columns= {"Serial No.","Name","Email","Phone"};
		@SuppressWarnings("unchecked")
		List<UserBean> studentBeans = (List<UserBean>)model.get("Data");
		HSSFSheet realSheet=hssfWorkbook.createSheet("Students Report");
		//CreationHelper helper=hssfWorkbook.getCreationHelper();
		
		HSSFFont mainFont = hssfWorkbook.createFont();
		mainFont.setBold(true);
		mainFont.setUnderline(HSSFFont.U_DOUBLE);
		mainFont.setFontHeightInPoints((short)16);
		mainFont.setFontName("Calibri");
		
		HSSFCellStyle mainStyle = hssfWorkbook.createCellStyle();
		mainStyle.setFont(mainFont);
		mainStyle.setBorderBottom(BorderStyle.THIN);
		mainStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		mainStyle.setBorderLeft(BorderStyle.THIN);
		mainStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		mainStyle.setBorderRight(BorderStyle.THIN);
		mainStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
		mainStyle.setBorderTop(BorderStyle.THIN);
		mainStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		
		HSSFRow row=realSheet.createRow(0);
		HSSFCell cell=row.createCell(0);
		cell.setCellValue("Hire Karma");
		cell.setCellStyle(mainStyle);
		
		
		HSSFFont midFont = hssfWorkbook.createFont();
		midFont.setBold(true);
		midFont.setUnderline(Font.U_DOUBLE);
		midFont.setFontHeightInPoints((short)12);
		HSSFCellStyle midStyle = hssfWorkbook.createCellStyle();
		midStyle.setFont(midFont);
		midStyle.setBorderBottom(BorderStyle.THIN);
		midStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		midStyle.setBorderLeft(BorderStyle.THIN);
		midStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		midStyle.setBorderRight(BorderStyle.THIN);
		midStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
		midStyle.setBorderTop(BorderStyle.THIN);
		midStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		
		row=realSheet.createRow(1);
		cell=row.createCell(0);
		cell.setCellValue("Students Report");
		cell.setCellStyle(midStyle);
		
		row=realSheet.createRow(3);
		cell=row.createCell(0);
		cell.setCellValue("Date : "+new SimpleDateFormat("dd/MMM/yyyy").format(new Date()));
		
		HSSFFont headerFont = hssfWorkbook.createFont();
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short)11);
		
		HSSFCellStyle headerStyle = hssfWorkbook.createCellStyle();
		headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);  
		headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        
		headerStyle.setFont(headerFont);
		headerStyle.setBorderBottom(BorderStyle.THIN);
		headerStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		headerStyle.setBorderLeft(BorderStyle.THIN);
		headerStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		headerStyle.setBorderRight(BorderStyle.THIN);
		headerStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
		headerStyle.setBorderTop(BorderStyle.THIN);
		headerStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		
		HSSFRow  headerRow = realSheet.createRow(5);
		int i=0;
		for(String column : columns)
		{
			HSSFCell cell1 = headerRow.createCell(i);
			cell1.setCellValue(column);
			cell1.setCellStyle(headerStyle);
			i++;
		}
		HSSFCellStyle dataStyle=hssfWorkbook.createCellStyle();
		dataStyle.setWrapText(true);
		dataStyle.setBorderBottom(BorderStyle.THIN);
		dataStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		dataStyle.setBorderLeft(BorderStyle.THIN);
		dataStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		dataStyle.setBorderRight(BorderStyle.THIN);
		dataStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
		dataStyle.setBorderTop(BorderStyle.THIN);
		dataStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		
		HSSFFont dataFont=hssfWorkbook.createFont();
		dataFont.setFontName("Cambria");
		dataFont.setFontHeightInPoints((short)11);
		dataStyle.setFont(dataFont);
		realSheet.setAutoFilter(new CellRangeAddress(5,5,0,3));
		
		for(int j=0;j<studentBeans.size();j++) {
			UserBean studentBean = studentBeans.get(j);
			row = realSheet.createRow(6+j);
			cell = row.createCell(0);
			cell.setCellValue(String.valueOf(j+1));
			cell.setCellStyle(dataStyle);
			
			cell = row.createCell(1);
			cell.setCellValue(studentBean.getName());
			cell.setCellStyle(dataStyle);
			
			cell = row.createCell(2);
			cell.setCellValue(studentBean.getEmail());
			cell.setCellStyle(dataStyle);
			
			cell = row.createCell(3);
			cell.setCellValue(studentBean.getPhoneNo());
			cell.setCellStyle(dataStyle);
		}
		
		realSheet.addMergedRegion(new CellRangeAddress(0,0,0,2));
		realSheet.addMergedRegion(new CellRangeAddress(1,1,0,1));
		realSheet.addMergedRegion(new CellRangeAddress(2,2,0,2));
		
		for(i=0;i<realSheet.getPhysicalNumberOfRows();i++) {
			try{
				HSSFRow rowObj = realSheet.getRow(i);
				if(i == 0 || i == 1)
					rowObj.setHeightInPoints((short)19);
				for(int j=0;j<rowObj.getPhysicalNumberOfCells();j++) {
					try {
						HSSFCell cellObj = rowObj.getCell(j);
						cellObj.getCellStyle().setWrapText(true);
						realSheet.autoSizeColumn(j);
					}
					catch(Exception e) {
						continue;
					}
				}
			}
			catch(Exception e){
				continue;
			}
		}
	}
}
