package com.hirekarma.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.apache.http.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.api.client.util.DateTime;
import com.hirekarma.beans.BlogBean;
import com.hirekarma.beans.NoticeBean;
import com.hirekarma.beans.Response;
import com.hirekarma.model.Corporate;
import com.hirekarma.model.Notice;
import com.hirekarma.model.Student;
import com.hirekarma.model.University;
import com.hirekarma.model.UserProfile;
import com.hirekarma.repository.NoticeRepository;
import com.hirekarma.repository.UserRepository;
import com.hirekarma.service.NoticeService;
import com.hirekarma.utilty.Validation;

@RestController
@CrossOrigin
@RequestMapping("/hirekarma/admin/")
public class NoticeController {
	
	@Autowired
	UserRepository userRepository;

	@Autowired
	NoticeRepository noticeRepository;
	
	@Autowired
	NoticeService noticeService;
	
	@PreAuthorize("hasRole('admin')")
	@GetMapping("/notice/dummy")
	public Notice getDummy() {
		Notice notice = new Notice();
		
		return notice;
		
	}
	
	@RequestMapping(value = "/notice", method = RequestMethod.POST, consumes = "multipart/form-data")
	public ResponseEntity<Response> addNotice(@RequestPart("data") NoticeBean noticeBean, @RequestPart(value="file",required = false) MultipartFile file,@RequestHeader("Authorization")String token) {
		try {
			Map<String, Object> response = new HashMap<>();
			String email = Validation.validateToken(token);
			List<Object[]> userData = this.userRepository.findUserAndAssociatedEntity(email);
			UserProfile userProfile = (UserProfile) userData.get(0)[0];
			Corporate corporate = (Corporate) userData.get(0)[1];
			University university = (University) userData.get(0)[2];
			Student student = (Student) userData.get(0)[3];
			if (userProfile.getUserType().equals("university")) {
				 response = this.noticeService.addNoticeByUniversity(noticeBean, file,university);
			} else if (userProfile.getUserType().equals("admin")) {
				response = this.noticeService.addNoticeByAdmin(noticeBean, file);
			} else if (userProfile.getUserType().equals("corporate")) {
				response =  this.noticeService.addNoticeByCorporate(noticeBean, file,corporate);
			}else {
				throw new Exception("Bad Request");
			}
			return new ResponseEntity<Response>(new Response("success", 200, "", response, null), HttpStatus.OK);

		}
		catch(NoSuchElementException ne) {
			return new ResponseEntity(new Response("error",404, "NO SUCH ELEMENT", null, null),
					HttpStatus.NOT_FOUND);
		}
		catch (DateTimeParseException e) {	
			return new ResponseEntity(new Response("error",404, "Please check the inserted Date", null, null),
				HttpStatus.NOT_FOUND);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			return new ResponseEntity(new Response("error", 400, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
		
	}
	@DeleteMapping("/notice/{slug}")
	public ResponseEntity<Response> deleteNoticeByAdmin(@PathVariable("slug")String slug,@RequestHeader("Authorization")String token) {
		try {
			Map<String, Object> response = new HashMap<>();
			String email = Validation.validateToken(token);
			List<Object[]> userData = this.userRepository.findUserAndAssociatedEntity(email);
			UserProfile userProfile = (UserProfile) userData.get(0)[0];
			Corporate corporate = (Corporate) userData.get(0)[1];
			University university = (University) userData.get(0)[2];
			Student student = (Student) userData.get(0)[3];
			if (userProfile.getUserType().equals("university")) {
				noticeService.deleteNoticeByUniversity(slug);
			} else if (userProfile.getUserType().equals("admin")) {
				noticeService.deleteNoticeByAdmin(slug);
			} else if (userProfile.getUserType().equals("corporate")) {
				noticeService.deleteNoticeByCorporate(slug);
			}else {
				throw new Exception("Bad Request");
			}
			return new ResponseEntity<Response>(new Response("success", 200, "", response, null), HttpStatus.OK);

		}
		catch (Exception e) 
		{
			e.printStackTrace();
			return new ResponseEntity(new Response("error", 400, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
		
	}
	@GetMapping("/notice")
	public ResponseEntity<Response> getAllNoticeByAdmin(@RequestHeader("Authorization")String token){
		try {
			Map<String, Object> response = new HashMap<>();
			String email = Validation.validateToken(token);
			List<Object[]> userData = this.userRepository.findUserAndAssociatedEntity(email);
			UserProfile userProfile = (UserProfile) userData.get(0)[0];
			Corporate corporate = (Corporate) userData.get(0)[1];
			University university = (University) userData.get(0)[2];
			Student student = (Student) userData.get(0)[3];
			if (userProfile.getUserType().equals("university")) {
				response.put("notices", this.noticeService.getAllNoticeByUniversity(university));
			} else if (userProfile.getUserType().equals("admin")) {
				response.put("notices", this.noticeService.getAllNoticeByAdmin());
			} else if (userProfile.getUserType().equals("corporate")) {
				response.put("notices", this.noticeService.getAllNoticeByCorporate(corporate));
			}else {
				throw new Exception("Bad Request");
			}
			return new ResponseEntity<Response>(new Response("success", 200, "", response, null), HttpStatus.OK);

		}
		catch (Exception e) 
		{
			e.printStackTrace();
			return new ResponseEntity(new Response("error", 400, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
		
		
	}
	@PreAuthorize("hasRole('admin')")
	@GetMapping("/notice/{slug}")
	public Notice getNoticeByAdminBySlug(@PathVariable("slug")String slug) {
		return this.noticeService.getNoticeByAdminBySlug(slug);
	}
	
	@RequestMapping(value = "/notice/{slug}", method = RequestMethod.PUT, consumes = "multipart/form-data")
	public ResponseEntity<Response> updateNoticeByAdminBySlug(@PathVariable("slug")String slug,@RequestPart("data") NoticeBean noticeBean, @RequestPart("file") MultipartFile file,@RequestHeader("Authorization")String token) {
		try {
			Map<String, Object> response = new HashMap<>();
			String email = Validation.validateToken(token);
			List<Object[]> userData = this.userRepository.findUserAndAssociatedEntity(email);
			UserProfile userProfile = (UserProfile) userData.get(0)[0];
			Corporate corporate = (Corporate) userData.get(0)[1];
			University university = (University) userData.get(0)[2];
			Student student = (Student) userData.get(0)[3];
			if (userProfile.getUserType().equals("university")) {
				response.put("notices", this.noticeService.updateNoticeByUniversityBySlug(slug, noticeBean, file,university));
			} else if (userProfile.getUserType().equals("admin")) {
				response.put("notices", this.noticeService.updateNoticeByAdminBySlug(slug, noticeBean, file));
			} else if (userProfile.getUserType().equals("corporate")) {
				response.put("notices", this.noticeService.updateNoticeByCorporateBySlug(slug, noticeBean, file,corporate));
			}else {
				throw new Exception("Bad Request");
			}
			return new ResponseEntity<Response>(new Response("success", 200, "", response, null), HttpStatus.OK);
		} catch (IOException ioe) {
			return new ResponseEntity<Response>(new Response("success", 400, ioe.getMessage(), null, null), HttpStatus.BAD_REQUEST);
			
		}
		catch(Exception e) {
			return new ResponseEntity<Response>(new Response("success", 400, e.getMessage(), null, null), HttpStatus.BAD_REQUEST);
			
		}
	}
}
