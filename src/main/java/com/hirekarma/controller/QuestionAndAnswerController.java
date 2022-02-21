package com.hirekarma.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hirekarma.beans.QuestionAndAnswerBean;
import com.hirekarma.beans.QuestionAndAnswerResponseBean;
import com.hirekarma.model.QuestionANdanswer;
import com.hirekarma.service.QuestionAndANswerService;

@RestController("QuestionAndAnswerController")
@CrossOrigin
@RequestMapping("/hirekarma/")
public class QuestionAndAnswerController {
	private static final Logger LOGGER = LoggerFactory.getLogger(QuestionAndAnswerController.class);
	@Autowired
	QuestionAndANswerService QAService;

	@PostMapping("/saveQNA")
	public ResponseEntity<QuestionAndAnswerResponseBean> saveQNA(@RequestBody List<QuestionAndAnswerBean> QAndA) {
		QuestionAndAnswerResponseBean QAResp=new QuestionAndAnswerResponseBean();
		QAResp=QAService.CreateQuestionAndAnswer(QAndA);
		System.out.println("HEllo Controller");
		return new ResponseEntity<QuestionAndAnswerResponseBean>(QAResp,HttpStatus.OK);
	}
	
	@PutMapping("/updateQNA")
	public ResponseEntity<QuestionAndAnswerResponseBean> updateQNA(@RequestBody List<QuestionAndAnswerBean> QAndA) {
		QuestionAndAnswerResponseBean QAResp=new QuestionAndAnswerResponseBean();
		QAResp=QAService.updateQuestionAndAnswer(QAndA);
		System.out.println("HEllo Controller");
		return new ResponseEntity<QuestionAndAnswerResponseBean>(QAResp,HttpStatus.OK);
	}
	
	@GetMapping("/getQNA/{QNA_id}")
	public QuestionANdanswer getQNA(@PathVariable String QNA_id){
		QuestionANdanswer respDetail;
		respDetail=QAService.getQNADetail(QNA_id);		
		return respDetail;
	}
	
	@DeleteMapping("/deleteQNA/{QNA_id}")
	public QuestionAndAnswerResponseBean deleteQNA(@PathVariable String QNA_id){
		QuestionAndAnswerResponseBean respDetail=new QuestionAndAnswerResponseBean();
		respDetail=QAService.deleteQNADetail(QNA_id);		
		return respDetail;
	}
	 @PostMapping("/upload")
	  public ResponseEntity<QuestionAndAnswerResponseBean> uploadFile(@RequestParam("file") MultipartFile file) {
		 return QAService.uploadFile(file);
	 }
	 @GetMapping("/download/{type}")
	  public ResponseEntity<Resource> downloadloadFile(@PathVariable String type) {
		 return QAService.downloadFile(type);
	 }
	 @GetMapping("/downloadDummy/{type}")
	  public ResponseEntity<Resource> downloadloadDummyFile(@PathVariable String type) {
		 return QAService.downloadDummyFile(type);
	 }
}
