package com.hirekarma.serviceimpl;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hirekarma.beans.QuestionAndAnswerBean;
import com.hirekarma.beans.QuestionAndAnswerResponseBean;
import com.hirekarma.model.CodingAnswer;
import com.hirekarma.model.Corporate;
import com.hirekarma.model.InputAnswer;
import com.hirekarma.model.LongAnswer;
import com.hirekarma.model.MCQAnswer;
import com.hirekarma.model.QuestionANdanswer;
import com.hirekarma.model.University;
import com.hirekarma.repository.MCQRepository;
import com.hirekarma.repository.QuestionAndAnswerRepository;
import com.hirekarma.service.QuestionAndANswerService;
import com.hirekarma.utilty.CSVHelper;
import com.hirekarma.utilty.CSVService;
import com.hirekarma.utilty.CsvDownload;
import com.hirekarma.utilty.DummyExcel;
import com.hirekarma.utilty.ExcelDownload;
import com.hirekarma.utilty.ExcelHelper;
@Service("QuestionAndAnswerServiceImpl")
public class QuestionAndAnswerServiceImpl implements QuestionAndANswerService {
	@Autowired
	QuestionAndAnswerRepository QARepo;
	@Autowired
	MCQRepository mcqRepo;


	@Override
	public QuestionAndAnswerResponseBean CreateQuestionAndAnswer(List<QuestionAndAnswerBean> qAndA,Corporate corporate,University university) {
		
		QuestionAndAnswerResponseBean bean=new QuestionAndAnswerResponseBean();
		
		for(int i=0;i<qAndA.size();i++) {
			QuestionAndAnswerBean QAbean=new QuestionAndAnswerBean();
			QuestionANdanswer QA=new QuestionANdanswer();
			QAbean=qAndA.get(i);
			if(corporate!=null) {

				QAbean.setCorporate(corporate);
			}
			else if(university!=null) {
				QAbean.setUniversity(university);
			}
			String QType=QAbean.getType();
			if(QType.equals("QNA")) {
				createQNARecord(QAbean,QARepo,bean);				
			}
			else if(QType.equals("MCQ")) {
				createMCQRecord(QAbean,QARepo,bean);
				
				
			}else if(QType.equals("Input")) {
				createInputRecord(QAbean,QARepo,bean);
				
			}else if(QType.equals("Coding")) {
				String [] question=QAbean.getQuestion();
				QA.setQuestion(question[0]);
				QA.setType(QAbean.getType());
				QA.setCodingDescription(QAbean.getCodingDescription());
				String[] testCase=QAbean.getTestCase();
				if(corporate!=null) {
					QA.setCorporate(corporate);
				}
				else if(university!=null) {
					QA.setUniversity(university);
				}
				
				
				QA.setUID(UUID.randomUUID().toString());
				List<CodingAnswer> codAns=new ArrayList();
				for(int x=0;x<testCase.length;x++) {
					CodingAnswer ans=new CodingAnswer();
					ans.setQ_uid(QA.getUID());
					ans.setTestCases(testCase[x]);
					codAns.add(ans);
				}
				QA.setCodingAnswer(codAns);
				QARepo.save(QA);
				bean.setStatus(200);
				bean.setMessage("Data Saved Successfully!!");
			}else {
				bean.setStatus(200);
				bean.setMessage("Saving Data Failed!!");
			}
		}
		return bean;
	}


	private void createInputRecord(QuestionAndAnswerBean QAbean, QuestionAndAnswerRepository QARepo,
			QuestionAndAnswerResponseBean bean) {
		QuestionANdanswer QA=new QuestionANdanswer();
		String [] question=QAbean.getQuestion();
		QA.setQuestion(question[0]);
		QA.setType(QAbean.getType());
		if(QAbean.getCorporate()!=null) {
			QA.setCorporate(QAbean.getCorporate());
		}else if(QAbean.getUniversity()!=null) {
			QA.setUniversity(QAbean.getUniversity());
		}
		QA.setUID(UUID.randomUUID().toString());
		QARepo.save(QA);
		bean.setStatus(200);
		bean.setMessage("Data Saved Successfully!!");
		
	}


	private void createMCQRecord(QuestionAndAnswerBean QAbean, QuestionAndAnswerRepository QARepo,
			QuestionAndAnswerResponseBean bean) {
		QuestionANdanswer QA=new QuestionANdanswer();
		String [] question=QAbean.getQuestion();
		for(int j=0;j<question.length;j++) {
		QA.setQuestion(question[j]);
		QA.setType(QAbean.getType());
		QA.setUID(UUID.randomUUID().toString());
		String[] mcqAnswer=QAbean.getMcqAnswer();
		List<MCQAnswer> ans=new ArrayList();
		for(int k=0;k<mcqAnswer.length;k++) {
			MCQAnswer answer=new MCQAnswer();
			answer.setMcqAnswer(mcqAnswer[k]);
			answer.setQ_uid(QA.getUID());
			ans.add(answer);					
		}
		QA.setMcqAnswer(ans);
		if(QAbean.getCorporate()!=null) {
			QA.setCorporate(QAbean.getCorporate());
		}else if(QAbean.getUniversity()!=null) {
			QA.setUniversity(QAbean.getUniversity());
		}
		
		QA.setCorrectOption(QAbean.getAnswer());
		
		QARepo.save(QA);
		}
		bean.setStatus(200);
		bean.setMessage("Data Saved Successfully!!");
		
	}


	private void createQNARecord(QuestionAndAnswerBean QAbean, QuestionAndAnswerRepository QARepo2, QuestionAndAnswerResponseBean bean) {
		try {
		String [] question=QAbean.getQuestion();
		for(int j=0;j<question.length;j++) {
			QuestionANdanswer QAs=new QuestionANdanswer();
		QAs.setQuestion(question[j]);
		QAs.setType(QAbean.getType());
		if(QAbean.getCorporate()!=null) {
			QAs.setCorporate(QAbean.getCorporate());
		}else if(QAbean.getUniversity()!=null) {
			QAs.setUniversity(QAbean.getUniversity());
		}
		QAs.setUID(UUID.randomUUID().toString());
		QARepo.save(QAs);
		}
		bean.setStatus(200);
		bean.setMessage("Data Saved Successfully!!");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}


	public QuestionAndAnswerResponseBean updateQuestionAndAnswer(List<QuestionAndAnswerBean> qAndA,Corporate corporate) {
		QuestionAndAnswerResponseBean bean=new QuestionAndAnswerResponseBean();
		for(int i=0;i<qAndA.size();i++) {	
			QuestionAndAnswerBean QAbean=new QuestionAndAnswerBean();
			QAbean=qAndA.get(i);
			String QType=QAbean.getType();
			QAbean.setCorporate(corporate);
			if(QType.equals("QNA")) {
				QuestionANdanswer QABeanDetail=new QuestionANdanswer();
				QABeanDetail=QARepo.findByuID(QAbean.getUId());
				
				LongAnswer lAns=new LongAnswer();
				if(QABeanDetail==null) {
					bean.setStatus(207);
					bean.setMessage("Data Can not be Updated because it is not found!!");
					return bean;
				}else {
				String [] question=QAbean.getQuestion();
				QABeanDetail.setQuestion((question[0]==null || question[0].equals(""))?QABeanDetail.getQuestion():question[0]);
				QABeanDetail.setType((QAbean.getType()==null ||QAbean.getType().equals(""))?QABeanDetail.getType():QAbean.getType());
				QABeanDetail.setCorporate(QAbean.getCorporate());
				lAns.setId(QABeanDetail.getId());
				lAns.setLongAnswer(QAbean.getLongAnswer());
				lAns.setUid(QABeanDetail.getUID());
				QABeanDetail.setLongAnswer(lAns);
				QARepo.save(QABeanDetail);
				bean.setStatus(200);
				bean.setMessage("Data Updated Successfully!!");
				}
				
			}else if(QType.equals("MCQ")) {
				QuestionANdanswer QABeanDetail=new QuestionANdanswer();
				QABeanDetail=QARepo.findByuID(QAbean.getUId());
				if(QABeanDetail==null) {
					bean.setStatus(207);
					bean.setMessage("Data Can not be Updated because it is not found!!");
					return bean;
				}else {					
				String [] question=QAbean.getQuestion();
				QABeanDetail.setQuestion((question[0]==null || question[0].equals(""))?QABeanDetail.getQuestion():question[0]);
				QABeanDetail.setType((QAbean.getType()==null || QAbean.getType().equals(""))?QABeanDetail.getType():QAbean.getType());
				QABeanDetail.setCorporate(QAbean.getCorporate());
				List<MCQAnswer> mcqAns=mcqRepo.findAllByUid(QABeanDetail.getId().toString());
				List<MCQAnswer> mcq=QABeanDetail.getMcqAnswer();
				List<MCQAnswer> mcqAnswer=QAbean.getMcqAns();
				List<MCQAnswer> ans=new ArrayList();
				for(int k=0;k<mcq.size();k++) {
				MCQAnswer mcqA=mcq.get(k);
				MCQAnswer answer=new MCQAnswer();
				for(int l=0;l<mcqAnswer.size();l++) {
					MCQAnswer mcqAn=mcqAnswer.get(l);					
				if(mcqA.getId()==mcqAn.getId()) {					
					answer.setId(mcqAn.getId());
					answer.setMcqAnswer((mcqAn.getMcqAnswer()==null || mcqAn.getMcqAnswer().equals(""))?QABeanDetail.getMcqAnswer().get(k).getMcqAnswer():mcqAn.getMcqAnswer());					
					answer.setQ_uid(QABeanDetail.getUID());
					answer.setUid(QABeanDetail.getId().toString());
					ans.add(answer);	
					continue;
				}
				}
				answer.setId(mcqA.getId());
				answer.setMcqAnswer(mcqA.getMcqAnswer());					
				answer.setQ_uid(QABeanDetail.getUID());
				answer.setUid(QABeanDetail.getId().toString());
				ans.add(answer);
				}
				QABeanDetail.setMcqAnswer(ans);
				
				QARepo.save(QABeanDetail);
				bean.setStatus(200);
				bean.setMessage("Data Updated Successfully!!");
				
				
				}
			}else if(QType.equals("Input")) {
				QuestionANdanswer QABeanDetail=new QuestionANdanswer();
				QABeanDetail=QARepo.findByuID(QAbean.getUId());
				if(QABeanDetail==null) {
					bean.setStatus(207);
					bean.setMessage("Data Can not be Updated because it is not found!!");
					return bean;
				}else {
					InputAnswer answer=new InputAnswer();
				String [] question=QAbean.getQuestion();
				QABeanDetail.setQuestion((question[0]==null || question[0].equals(""))?QABeanDetail.getQuestion():question[0]);
				QABeanDetail.setType((QAbean.getType()==null || QAbean.getType().equals(""))?QABeanDetail.getType():QAbean.getType());
				QABeanDetail.setCorporate(QAbean.getCorporate());
				answer.setId(QABeanDetail.getId());
				answer.setInputAnswer(QAbean.getInputAnswer());
				answer.setUid(QABeanDetail.getUID());
				QABeanDetail.setInputAnswer(answer);
				QARepo.save(QABeanDetail);
				bean.setStatus(200);
				bean.setMessage("Data Updated Successfully!!");
				}
			}else if(QType.equals("Coding")) {
				QuestionANdanswer QABeanDetail=new QuestionANdanswer();
				QABeanDetail=QARepo.findByuID(QAbean.getUId());
				if(QABeanDetail==null) {
					bean.setStatus(207);
					bean.setMessage("Data Can not be Updated because it is not found!!");
					return bean;
				}else {
				String [] question=QAbean.getQuestion();
				QABeanDetail.setQuestion((question[0]==null || question[0].equals(""))?QABeanDetail.getQuestion():question[0]);
				QABeanDetail.setType((QAbean.getType()==null || QAbean.getType().equals(""))?QABeanDetail.getType():QAbean.getType());
				QABeanDetail.setCodingDescription((QAbean.getCodingDescription()==null || QAbean.getCodingDescription().equals(""))?QABeanDetail.getCodingDescription():QAbean.getCodingDescription());
				QABeanDetail.setCorporate(QAbean.getCorporate());
				QARepo.save(QABeanDetail);
				bean.setStatus(200);
				bean.setMessage("Data Updated Successfully!!");
				}
			}
		}
		return bean;
	}

	@Override
	public QuestionANdanswer getQNADetail(String qNA_id) {		
		QuestionANdanswer questionANdanswer =  QARepo.findByuIDAndStatusIsNullOrStatusNot(qNA_id,"deleted");
	return questionANdanswer;
	}

	@Override
	public QuestionAndAnswerResponseBean deleteQNADetail(String qNA_id) {
		QuestionANdanswer QABeanDetail=new QuestionANdanswer();
		QuestionAndAnswerResponseBean bean=new QuestionAndAnswerResponseBean();
		QABeanDetail=QARepo.findByuID(qNA_id);
		if(QABeanDetail==null) {
			bean.setStatus(207);
			bean.setMessage("Data Can not be deleted because it is not found!!");
			return bean;
		}else {
			QABeanDetail.setStatus("deleted");
		}
		QARepo.save(QABeanDetail);
		bean.setStatus(200);
		bean.setMessage("Data deleted successfully!!");
		return bean;
	}


	@Override
	public ResponseEntity<QuestionAndAnswerResponseBean> uploadFile(MultipartFile file,Corporate corporate,University university) {
		String message = "";
		int status=0;
		ExcelService fileService=new ExcelService();
		CSVService csvService=new CSVService();
	    if (ExcelHelper.hasExcelFormat(file)) {
	      try {
	    	  List<QuestionAndAnswerBean> tutorial= fileService.save(file,corporate, university);
	    	  CreateQuestionAndAnswer(tutorial,corporate, university);
	        status=200;
	        message = "Uploaded the file successfully: " + file.getOriginalFilename();
	        return ResponseEntity.status(HttpStatus.OK).body(new QuestionAndAnswerResponseBean(status,message));
	      } catch (Exception e) {
	    	 status=210; 
	        message = "Could not upload the file: " + file.getOriginalFilename() + "!";
	        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new QuestionAndAnswerResponseBean(status,message));
	      }
	    }
//	    -------------------------------csv not working --------------------------
//	    else  if (CSVHelper.hasCSVFormat(file)){
//	    	try {
//		    	  List<QuestionAndAnswerBean> tutorial= csvService.save(file);
//		    	  CreateQuestionAndAnswer(tutorial,corporate);
//		        status=200;
//		        message = "Uploaded the file successfully: " + file.getOriginalFilename();
//		        return ResponseEntity.status(HttpStatus.OK).body(new QuestionAndAnswerResponseBean(status,message));
//		      } catch (Exception e) {
//		    	 status=210; 
//		        message = "Could not upload the file: " + file.getOriginalFilename() + "!";
//		        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new QuestionAndAnswerResponseBean(status,message));
//		      }
//	    }
	    status=210;
	    message = "Please upload an excel file!";
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new QuestionAndAnswerResponseBean(status,message));
	}


	@Override
	public ResponseEntity<Resource> downloadFile(String type,Corporate corporate) {
		List<QuestionAndAnswerBean> tutorials = null;
		
		tutorials = getAllDetailByCorporate(corporate);
//		if (type.contains("CSV")) {
//			String filename = "QandA.csv";
//			ByteArrayInputStream in = CsvDownload.tutorialsToCSV(tutorials);
//			InputStreamResource files = new InputStreamResource(in);
//			return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
//					.contentType(MediaType.parseMediaType("application/csv")).body(files);
//		} else 
		if (type.contains("xlsx")) {
			String filename = "QandA.xlsx";
			ByteArrayInputStream in = ExcelDownload.tutorialsToExcel(tutorials);
			InputStreamResource files = new InputStreamResource(in);
			return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
					.contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(files);
		}else {
			String filename = "QandA.xlsx";
			String sheet="Questionaries";
			String[] HEADERs = {"question", "type", "mcqanswer", "codingdescription","testcase" };
			ByteArrayInputStream in = DummyExcel.tutorialsToExcel(tutorials,HEADERs,sheet);
			InputStreamResource files = new InputStreamResource(in);
			return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
					.contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(files);
		}
	}


	private List<QuestionAndAnswerBean> getAllDetailByCorporate(Corporate corporate) {
		List<QuestionANdanswer> tutorials = null;
		if(corporate==null) {
			tutorials =QARepo.findQandAForCorporate(corporate,"deleted");
		}
		else {
			tutorials = QARepo.findAll();
		}
		List<QuestionAndAnswerBean> tutorial =new ArrayList();
		for(QuestionANdanswer ans:tutorials) {
			QuestionAndAnswerBean bean=new QuestionAndAnswerBean();
			bean.setQuestion(new String[]{ans.getQuestion()});
			bean.setType(ans.getType());
			List<MCQAnswer> mcq=ans.getMcqAnswer();
			String[] ary=new String[mcq.size()];
			for(int i=0;i<mcq.size();i++) {
				ary[i]=mcq.get(i).getMcqAnswer();
			}
			bean.setMcqAnswer(ary);
			bean.setCodingDescription(ans.getCodingDescription());
			List<CodingAnswer> testcase=ans.getCodingAnswer();
			String[] testCases=new String[testcase.size()];
			for(int j=0;j<testcase.size();j++) {
				testCases[j]=testcase.get(j).getTestCases();
			}
			bean.setTestCase(testCases);
			bean.setCorporate(ans.getCorporate());
			tutorial.add(bean);
		}
		return tutorial;
	}


	@Override
	public ResponseEntity<Resource> downloadDummyFile(String type) {
		List<QuestionAndAnswerBean> tutorials=new ArrayList();
		if(type.equals("Coding")) {
			String filename = "QandA.xlsx";
			String sheet="Coding";
			String[] HEADERs = {"question","codingdescription","testcase"};
			ByteArrayInputStream in = DummyExcel.tutorialsToExcel(tutorials,HEADERs,sheet);
			InputStreamResource files = new InputStreamResource(in);
			return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
					.contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(files);
		}else if(type.equals("Input")) {
			String filename = "QandA.xlsx";
			String sheet="Input";
			String[] HEADERs = {"question"};
			ByteArrayInputStream in = DummyExcel.tutorialsToExcel(tutorials,HEADERs,sheet);
			InputStreamResource files = new InputStreamResource(in);
			return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
					.contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(files);
		}else if(type.equals("MCQ")) {
			String filename = "QandA.xlsx";
			String sheet="MCQ";
			String[] HEADERs = {"question", "mcqanswer"};
			ByteArrayInputStream in = DummyExcel.tutorialsToExcel(tutorials,HEADERs,sheet);
			InputStreamResource files = new InputStreamResource(in);
			return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
					.contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(files);
		}else if(type.equals("QNA")) {
			String filename = "QandA.xlsx";
			String sheet="QNA";
			String[] HEADERs = {"question" };
			ByteArrayInputStream in = DummyExcel.tutorialsToExcel(tutorials,HEADERs,sheet);
			InputStreamResource files = new InputStreamResource(in);
			return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
					.contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(files);
		}else
		return null;
	}
	
	@Override
	public List<QuestionANdanswer> getQNAByType(String type){
		return this.QARepo.findByType(type);
	}
	
	public List<QuestionANdanswer> getQNAForCorporate(Corporate corporate){
		return QARepo.findQandAForCorporate(corporate,"deleted");
	}




}
