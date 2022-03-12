package com.hirekarma.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hirekarma.beans.QuestionAndAnswerBean;
import com.hirekarma.beans.QuestionAndAnswerResponseBean;
import com.hirekarma.beans.Response;
import com.hirekarma.model.Corporate;
import com.hirekarma.model.QuestionANdanswer;
import com.hirekarma.model.UserProfile;
import com.hirekarma.repository.CorporateRepository;
import com.hirekarma.repository.QuestionAndAnswerRepository;
import com.hirekarma.repository.UserRepository;
import com.hirekarma.service.QuestionAndANswerService;
import com.hirekarma.utilty.Validation;

@RestController("QuestionAndAnswerController")
@CrossOrigin
@RequestMapping("/hirekarma/")
public class QuestionAndAnswerController {
	private static final Logger LOGGER = LoggerFactory.getLogger(QuestionAndAnswerController.class);
	@Autowired
	QuestionAndANswerService QAService;
	
	@Autowired
	QuestionAndAnswerRepository questionAndAnswerRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	CorporateRepository corporateRepository;

	@PostMapping("/saveQNA")
	@PreAuthorize("hasAnyRole('admin','corporate')")
	public ResponseEntity<QuestionAndAnswerResponseBean> saveQNA(@RequestBody List<QuestionAndAnswerBean> QAndA,
			@RequestHeader("Authorization") String token) {
		try {

			String email = Validation.validateToken(token);
			UserProfile userProfile = userRepository.findUserByEmail(email);
			Corporate corporate = null;

			QuestionAndAnswerResponseBean QAResp = new QuestionAndAnswerResponseBean();
			if (userProfile.getUserType().equals("corporate")) {
				corporate = corporateRepository.findByEmail(email);
				QAResp = QAService.CreateQuestionAndAnswer(QAndA, corporate);
			} else if (userProfile.getUserType().equals("admin")) {
				QAResp = QAService.CreateQuestionAndAnswer(QAndA, corporate);
			} else {
				throw new Exception("unauthorized");
			}
			return new ResponseEntity<QuestionAndAnswerResponseBean>(QAResp, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<QuestionAndAnswerResponseBean>(new QuestionAndAnswerResponseBean(),
					HttpStatus.BAD_REQUEST);

		}

	}

	@PutMapping("/updateQNA")
	public ResponseEntity<QuestionAndAnswerResponseBean> updateQNA(@RequestBody List<QuestionAndAnswerBean> QAndA,
			@RequestHeader("Authorization") String token) {
		try {
			String email = Validation.validateToken(token);
			UserProfile userProfile = userRepository.findUserByEmail(email);
			Corporate corporate = null;

			QuestionAndAnswerResponseBean QAResp = new QuestionAndAnswerResponseBean();
			if (userProfile.getUserType().equals("corporate")) {
				corporate = corporateRepository.findByEmail(email);
				QAResp = QAService.updateQuestionAndAnswer(QAndA, corporate);
			} else if (userProfile.getUserType().equals("admin")) {
				QAResp = QAService.updateQuestionAndAnswer(QAndA, corporate);
			} else {
				throw new Exception("unauthorized");
			}
			return new ResponseEntity<QuestionAndAnswerResponseBean>(QAResp, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<QuestionAndAnswerResponseBean>(new QuestionAndAnswerResponseBean(),
					HttpStatus.BAD_REQUEST);
		}

	}

	@GetMapping("/getQNA/{QNA_id}")
	public QuestionANdanswer getQNA(@PathVariable String QNA_id) {
		QuestionANdanswer respDetail;
		respDetail = QAService.getQNADetail(QNA_id);
		return respDetail;
	}

	@GetMapping("/getQNA/type/{type}")
	public List<QuestionANdanswer> getQNAByType(@PathVariable String type) {
		return QAService.getQNAByType(type);
	}

	@DeleteMapping("/deleteQNA/{QNA_id}")
	public QuestionAndAnswerResponseBean deleteQNA(@PathVariable String QNA_id) {
		QuestionAndAnswerResponseBean respDetail = new QuestionAndAnswerResponseBean();
		respDetail = QAService.deleteQNADetail(QNA_id);
		return respDetail;
	}

	@PostMapping("/upload")
	public ResponseEntity<QuestionAndAnswerResponseBean> uploadFile(@RequestParam("file") MultipartFile file,
			@RequestHeader("Authorization") String token) {
		try {

			String email = Validation.validateToken(token);
			UserProfile userProfile = userRepository.findUserByEmail(email);
			Corporate corporate = null;

			QuestionAndAnswerResponseBean QAResp = new QuestionAndAnswerResponseBean();
			if (userProfile.getUserType().equals("corporate")) {
				corporate = corporateRepository.findByEmail(email);
				return QAService.uploadFile(file, corporate);
			} else if (userProfile.getUserType().equals("admin")) {
				return QAService.uploadFile(file, null);
			} else {
				throw new Exception("unauthorized");
			}
		} catch (Exception e) {
			return new ResponseEntity<QuestionAndAnswerResponseBean>(new QuestionAndAnswerResponseBean(),
					HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/download/{type}")
	public ResponseEntity<Resource> downloadloadFile(@PathVariable String type,@RequestHeader("Authorization")String token) throws ParseException {

			String email = Validation.validateToken(token);
			Corporate corporate = this.corporateRepository.findByEmail(email);
			return QAService.downloadFile(type,corporate);
		
		
	}

	@GetMapping("/downloadDummy/{type}")
	public ResponseEntity<Resource> downloadloadDummyFile(@PathVariable String type) {
		return QAService.downloadDummyFile(type);
	}
	
	@GetMapping("/QNA/questionaries")
	public ResponseEntity<Response> findQandAForCorporate(
			@RequestHeader("Authorization") String token) {
		try {
			String email = Validation.validateToken(token);
			UserProfile userProfile = userRepository.findUserByEmail(email);
			Corporate corporate = null;
			

			QuestionAndAnswerResponseBean QAResp = new QuestionAndAnswerResponseBean();
			if (userProfile.getUserType().equals("corporate")) {

				corporate = corporateRepository.findByEmail(email);
				System.out.println(corporate.getCorporateId());
				return new ResponseEntity<Response>(new Response("success", 201, "added succesfully", questionAndAnswerRepository.findQandAForCorporate(corporate), null),
						HttpStatus.OK);
			} else if (userProfile.getUserType().equals("admin")) {
				
				return new ResponseEntity<Response>(new Response("success", 201, "added succesfully", questionAndAnswerRepository.findAll(), null),
						HttpStatus.OK);
			} else {
				throw new Exception("unauthorized");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST,"", null, null),
				HttpStatus.BAD_REQUEST);
	}
}
