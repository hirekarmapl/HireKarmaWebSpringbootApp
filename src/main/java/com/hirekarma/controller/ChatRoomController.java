package com.hirekarma.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hirekarma.beans.ChatRoomBean;
import com.hirekarma.beans.Response;
import com.hirekarma.model.Blog;
import com.hirekarma.model.Corporate;
import com.hirekarma.model.Student;
import com.hirekarma.model.UserProfile;
import com.hirekarma.repository.CorporateRepository;
import com.hirekarma.repository.StudentRepository;
import com.hirekarma.repository.UserRepository;
import com.hirekarma.service.ChatRoomService;
import com.hirekarma.utilty.JwtUtil;
import com.hirekarma.utilty.Validation;

@RestController("chatRoomController")
@RequestMapping("/hirekarma/")
@CrossOrigin
public class ChatRoomController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ChatRoomController.class);
	
	@Autowired
	private ChatRoomService chatRoomService;
	
	@Autowired
	private JwtUtil jwtTokenUtil;
	
	@Autowired
	private CorporateRepository corporateRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private StudentRepository studentRepository;
	
	@PostMapping("/sendMessage")
	@PreAuthorize("hasAnyRole('student','corporate')")
	public ResponseEntity<Map<String, Object>> sendMessage(@ModelAttribute ChatRoomBean chatRoomBean,HttpServletRequest request) {
		LOGGER.debug("Inside ChatRoomController.sendMessage(-)");
		Map<String, Object> map = null;
		ResponseEntity<Map<String, Object>> responseEntity = null;
		String jwtToken = null;
		String authorizationHeader = null;
		String email=null;
		UserProfile userProfile = null;
		try {
			LOGGER.debug("Inside try block of ChatRoomController.sendMessage(-)");
			authorizationHeader = request.getHeader("Authorization");
			jwtToken = authorizationHeader.substring(7);
			if(chatRoomBean.getChatRoomId() != null) {
				email = jwtTokenUtil.extractUsername(jwtToken);
				userProfile = userRepository.findUserByEmail(email);
				if(userProfile != null) {
					if(userProfile.getUserType().equals("corporate")) {
						Corporate corporate = this.corporateRepository.findByEmail(email);
						chatRoomBean.setStudentId(corporate.getCorporateId());
						chatRoomBean.setCorporateId(null);
						chatRoomBean.setSenderType("corporate");
					}
					else {
						Student student = this.studentRepository.findByStudentEmail(email);
						chatRoomBean.setStudentId(student.getStudentId());
						chatRoomBean.setCorporateId(null);
						chatRoomBean.setSenderType("student");
					}
					map = chatRoomService.sendMessage(chatRoomBean);
					LOGGER.info("Message sent using ChatRoomController.sendMessage(-)");
				}
				else {
					map = new HashMap<String, Object>();
					map.put("status", "Bad Request");
					map.put("responseCode", 400);
					map.put("message", "Message sending failed!!!");
				}
			}
			else {
				map = new HashMap<String, Object>();
				map.put("status", "Bad Request");
				map.put("responseCode", 400);
				map.put("message", "Please provide chat room id!!!");
			}
			responseEntity = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
			return responseEntity;
		} catch (Exception e) {
			LOGGER.error("sending Message Failed in ChatRoomServiceImpl.sendMessage(-)");
			map = new HashMap<String, Object>();
			map.put("status", "Bad Request");
			map.put("responseCode", 400);
			map.put("message", "Message sending failed!!!");
			responseEntity = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
			return responseEntity;
		}
	}
	
	@GetMapping("/getMessagesByChatRoom")
	@PreAuthorize("hasAnyRole('student','corporate')")
	public ResponseEntity<Map<String, Object>> getMessagesByChatRoom(@RequestParam("chatRoomId") Long chatRoomId) {
		Map<String, Object> map = null;
		ResponseEntity<Map<String, Object>> responseEntity = null;
		try {
			LOGGER.debug("Inside try block of ChatRoomController.getMessagesByChatRoom(-)");
			map = chatRoomService.getMessagesByChatRoomId(chatRoomId);
			responseEntity = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
			return responseEntity;
		}
		catch (Exception e) {
			LOGGER.error("retriving of Messages Failed in ChatRoomServiceImpl.getMessagesByChatRoom(-)");
			map = new HashMap<String, Object>();
			map.put("status", "Bad Request");
			map.put("responseCode", 400);
			map.put("message", "Message sending failed!!!");
			responseEntity = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
			return responseEntity;
		}
	}
	
	@GetMapping("/getAllChatRoom")
	@PreAuthorize("hasAnyRole('student','corporate')")
	public ResponseEntity<Map<String, Object>> getAllChatRooms(@RequestHeader("Authorization")String token){
		Map<String, Object> map = null;
		try {
			String email = Validation.validateToken(token);
			UserProfile userProfile = this.userRepository.findUserByEmail(email);
			List<ChatRoomBean> chatRoomBeans = this.chatRoomService.getAllChatRooms(userProfile);
			map = new HashMap<String, Object>();
			map.put("status", "Success");
			map.put("responseCode", 200);
			map.put("data", chatRoomBeans);
			return  new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
			} catch (Exception e) {
				map = new HashMap<String, Object>();
				map.put("status", "Bad Request");
				map.put("responseCode", 400);
				map.put("message", "something went wrong!");
				return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
				
			}
	}
	
}
