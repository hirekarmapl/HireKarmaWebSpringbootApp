package com.hirekarma.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirekarma.beans.ChatRoomBean;
import com.hirekarma.service.ChatRoomService;

@RestController("chatRoomController")
@RequestMapping("/api/")
public class ChatRoomController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ChatRoomController.class);
	
	@Autowired
	private ChatRoomService chatRoomService;
	
	@RequestMapping("/sendMessage")
	@PreAuthorize("hasAnyRole('student','corporate')")
	public ResponseEntity<Map<String, Object>> sendMessage(@ModelAttribute ChatRoomBean chatRoomBean,HttpServletRequest request) {
		LOGGER.debug("Inside ChatRoomController.sendMessage(-)");
		Map<String, Object> map = null;
		ResponseEntity<Map<String, Object>> responseEntity = null;
		try {
			LOGGER.debug("Inside try block of ChatRoomController.sendMessage(-)");
			map = chatRoomService.sendMessage(chatRoomBean);
			responseEntity = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
			return responseEntity;
		} catch (Exception e) {
			map = new HashMap<String, Object>();
			map.put("status", "Failed");
			map.put("responseCode", 500);
			map.put("message", "Message sending failed!!!");
			responseEntity = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
			return responseEntity;
		}
	}
}
