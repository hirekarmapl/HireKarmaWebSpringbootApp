package com.hirekarma.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.internal.build.AllowSysOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hirekarma.model.StudentBatch;
import com.hirekarma.model.StudentBranch;
import com.hirekarma.repository.StudentBatchRepository;
import com.hirekarma.repository.StudentBranchRepository;

@RestController
@CrossOrigin
@RequestMapping("/hirekarma/")
public class StudentBranchController {
	
	@Autowired
	StudentBranchRepository studentBranchRepository;
	
	@Autowired
	StudentBatchRepository studentBatchRepository;
	
	@PostMapping("/batch")
	public StudentBatch addBatch(@RequestBody Map<String,String> request) {
		StudentBatch studentBatch = new StudentBatch();
		studentBatch.setBatchName(request.get("name"));
		return studentBatchRepository.save(studentBatch);
	}
	
	@GetMapping("/batch")
	public List<StudentBatch> getAllBatch() {
		return studentBatchRepository.findAll();
	}
	@PostMapping("/branch")
	public StudentBranch addBranch(@RequestBody Map<String, String> request) {
		StudentBranch studentBranch = new StudentBranch();
		studentBranch.setBranchName(request.get("name"));
		try {
			return this.studentBranchRepository.save(studentBranch);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@GetMapping("/branchs")
	public List<StudentBranch> findAllBranches(){
		return this.studentBranchRepository.findAll();
	}
	
	@PostMapping("/branch/excel_upload")
	public String excelReader(@RequestParam("file") MultipartFile excel) {
		List<StudentBranch> studentBranchs = new ArrayList<StudentBranch>();
		try {
			XSSFWorkbook workbook = new XSSFWorkbook(excel.getInputStream());
			XSSFSheet sheet = workbook.getSheetAt(0);
			
			for(int i=0; i<sheet.getPhysicalNumberOfRows();i++) {
				XSSFRow row = sheet.getRow(i);
				if(row.getCell(1)!=null) {
					if(row.getCell(1).getCellTypeEnum()==CellType.STRING) {
						System.out.println(row.getCell(1).toString().trim());
						StudentBranch studentBranch = new StudentBranch();
						studentBranch.setBranchName(row.getCell(1).toString().trim());
						studentBranchs.add(studentBranch);
					}
				}
				
			}
			this.studentBranchRepository.saveAll(studentBranchs);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "Success";
	}
	
}
