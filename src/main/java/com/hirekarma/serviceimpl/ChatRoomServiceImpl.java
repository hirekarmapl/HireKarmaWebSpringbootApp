package com.hirekarma.serviceimpl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirekarma.beans.ChatRoomBean;
import com.hirekarma.model.Message;
import com.hirekarma.repository.MessageRepository;
import com.hirekarma.service.ChatRoomService;

@Service("chatRoomServiceImpl")
public class ChatRoomServiceImpl implements ChatRoomService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CoporateUserServiceImpl.class);
	
	@Autowired
	private MessageRepository messageRepository;
	
	@Override
	public Map<String, Object> sendMessage(ChatRoomBean chatRoomBean) {
		LOGGER.debug("Inside ChatRoomServiceImpl.sendMessage(-)");
		Map<String, Object> map = null;
		Message message = null;
		byte[] attachment = null;
		try {
			LOGGER.debug("Inside try block of ChatRoomServiceImpl.sendMessage(-)");
			//check chat room is already there or not
			if(chatRoomBean.getAttachment() != null) {
				attachment = chatRoomBean.getAttachment().getBytes();
			}
			message = new Message();
			message.setChatRoomId(chatRoomBean.getChatRoomId());
			message.setAttachment(attachment);
			message.setSenderType(chatRoomBean.getSenderType());
			message.setTxtMessage(chatRoomBean.getTxtMsg());
			message.setIsSeen(chatRoomBean.getIsSeen());
			messageRepository.save(message);
			map = new HashMap<String, Object>();
			map.put("status", "Success");
			map.put("responseCode", 200);
			map.put("message", "Message Sent");
			LOGGER.info("Message sent using ChatRoomServiceImpl.sendMessage(-)");
		}
		catch (IOException e) {
			LOGGER.error("sending Message Failed in ChatRoomServiceImpl.sendMessage(-)");
			e.printStackTrace();
			map = new HashMap<String, Object>();
			map.put("status", "Failed");
			map.put("responseCode", 500);
			map.put("message", "Message sending failed!!!");
		}
		catch (Exception e) {
			LOGGER.error("sending Message Failed in ChatRoomServiceImpl.sendMessage(-)");
			e.printStackTrace();
			map = new HashMap<String, Object>();
			map.put("status", "Failed");
			map.put("responseCode", 500);
			map.put("message", "Message sending failed!!!");
		}
		return map;
	}

	@Override
	public Map<String, Object> getMessagesByChatRoomId(Long chatRoomId) {
		LOGGER.debug("Inside ChatRoomServiceImpl.getMessagesByChatRoomId(-)");
		Map<String, Object> map = null;
		List<Message> messages = null;
		try {
			messages = messageRepository.getMessagesByChatRoomId(chatRoomId);
			map = new HashMap<String, Object>();
			map.put("status", "Success");
			map.put("responseCode", 200);
			map.put("data", messages);
			LOGGER.info("Message retrived using ChatRoomServiceImpl.getMessagesByChatRoomId(-)");
		}
		catch (Exception e) {
			LOGGER.error("retriving Message Failed in ChatRoomServiceImpl.getMessagesByChatRoomId(-)");
			e.printStackTrace();
			map = new HashMap<String, Object>();
			map.put("status", "Failed");
			map.put("responseCode", 500);
			map.put("message", "Message retriving failed!!!");
		}
		return map;
	}

}
