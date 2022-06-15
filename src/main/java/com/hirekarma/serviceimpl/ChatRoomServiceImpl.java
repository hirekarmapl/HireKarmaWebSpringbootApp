package com.hirekarma.serviceimpl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirekarma.beans.ChatRoomBean;
import com.hirekarma.beans.ScreeningResponseBean;
import com.hirekarma.model.ChatRoom;
import com.hirekarma.model.Corporate;
import com.hirekarma.model.Job;
import com.hirekarma.model.Message;
import com.hirekarma.model.ScreeningResponse;
import com.hirekarma.model.Student;
import com.hirekarma.model.UserProfile;
import com.hirekarma.repository.ChatRoomRepository;
import com.hirekarma.repository.CorporateRepository;
import com.hirekarma.repository.MessageRepository;
import com.hirekarma.repository.ScreeningResponseRepository;
import com.hirekarma.repository.StudentRepository;
import com.hirekarma.service.ChatRoomService;

@Service("chatRoomServiceImpl")
public class ChatRoomServiceImpl implements ChatRoomService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CoporateUserServiceImpl.class);
	
	@Autowired
	private MessageRepository messageRepository;
	
	@Autowired
	private AWSS3Service awss3Service;
	
	@Autowired
	private ChatRoomRepository chatRoomRepository;
	
	@Autowired
	private CorporateRepository corporateRepository;
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private ScreeningResponseRepository screeningResponseRepository;
	
	@Override
	public void sendmessageToMultipleStudent(ChatRoomBean chatRoomBean) throws Exception{
		List<Message> messages = new ArrayList<Message>();
		List<ChatRoom> chatRooms =null;
		try{
			chatRooms =this.chatRoomRepository.findAllById(chatRoomBean.getChatRoomIds());
			if(chatRooms.size()!=chatRoomBean.getChatRoomIds().size()) {
				throw new Exception();
			}
		}
		catch(Exception e) {
			throw new Exception("invalid chatRoom ids	");
			
		}
		String attachmentUrl = null;
		if(chatRoomBean.getAttachment()!=null && !chatRoomBean.getAttachment().isEmpty()) {
			attachmentUrl = awss3Service.uploadFile(chatRoomBean.getAttachment());
		}
		for(ChatRoom c:chatRooms) {
			Map<String, Object> map = null;
			
			Message message = new Message();
			message.setChatRoomId(c.getChatRoomId());
			if(chatRoomBean.getAttachment() != null) {
				message.setAttachmentUrl(attachmentUrl);
			}
			message.setSenderType(chatRoomBean.getSenderType());
			message.setTxtMessage(chatRoomBean.getTxtMsg());
			message.setIsSeen(chatRoomBean.getIsSeen());
			
			messages.add(message);
		}
		messages = this.messageRepository.saveAll(messages);
		this.chatRoomRepository.updateUpdatedOn(LocalDateTime.now(), chatRoomBean.getChatRoomIds());
		System.out.println("message successfully sent");
	
	}
	
	@Override
	public Map<String, Object> sendMessage(ChatRoomBean chatRoomBean) {
		LOGGER.debug("Inside ChatRoomServiceImpl.sendMessage(-)");
		Map<String, Object> map = null;
		Message message = null;

		try {
			LOGGER.debug("Inside try block of ChatRoomServiceImpl.sendMessage(-)");
			//check chat room is already there or not
			
			message = new Message();
			message.setChatRoomId(chatRoomBean.getChatRoomId());
			if(chatRoomBean.getAttachment() != null) {
				message.setAttachmentUrl(awss3Service.uploadFile(chatRoomBean.getAttachment()));
			}
			message.setSenderType(chatRoomBean.getSenderType());
			message.setTxtMessage(chatRoomBean.getTxtMsg());
			message.setIsSeen(chatRoomBean.getIsSeen());
			messageRepository.save(message);
			ChatRoom chatRoom = this.chatRoomRepository.getById(chatRoomBean.getChatRoomId());
			chatRoom.setUpdatedOn(LocalDateTime.now());
			map = new HashMap<String, Object>();
			map.put("status", "Success");
			map.put("responseCode", 200);
			map.put("message", "Message Sent");
			LOGGER.info("Message sent using ChatRoomServiceImpl.sendMessage(-)");
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
	public void studentResponseOnScreeningQuestions(List<ScreeningResponseBean> screeningResponseBeans) throws Exception
	{
		List<ScreeningResponse> screeningResponses = new ArrayList<ScreeningResponse>();
		for(ScreeningResponseBean screeningResponseBean:screeningResponseBeans) {
			Optional<ScreeningResponse> optional = this.screeningResponseRepository.findById(screeningResponseBean.getScreeningResponseId());
			if(!optional.isPresent()) {
				throw new Exception("something wrong with your inputs!");
			}
			ScreeningResponse screeningResponse = optional.get();
			screeningResponse.setUserResponse(screeningResponseBean.getUserResponse());
			screeningResponses.add(screeningResponse);
		}
		this.screeningResponseRepository.saveAll(screeningResponses);
	}
	@Override
	public Map<String,Object> getMessagesAndScreeningQuestionByChatRoomId(Long chatRoomId){
		Map<String,Object> map = new HashMap<String, Object>();
//		List<Message> messages = null;
//		List<Object[]> screeningResponseAndScreeningQuestion =  screeningResponseRepository.getAllScreeningResponsesAndScreeningTableByChatRoomId(chatRoomId);
//		messages = messageRepository.getMessagesByChatRoomId(chatRoomId);
//		map.put("screeningResponse", screeningResponseAndScreeningQuestion);
//		map.put("messages", messages);
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
	
	public List<ChatRoomBean> getAllChatRooms(UserProfile userProfile){
		List<ChatRoomBean> chatRoomBeans = new ArrayList<>();

		if(userProfile.getUserType().equals("corporate")) {
			Corporate corporate = this.corporateRepository.findByEmail(userProfile.getEmail());
			List<Object[]> query = this.chatRoomRepository.getAllChatRoomsForCorporate(corporate.getCorporateId());
			for(Object[] o :query) {
				ChatRoomBean chatRoomBean = new ChatRoomBean();
				ChatRoom chatRoom = (ChatRoom)o[0];
				Student s = (Student) o[1];
				Job job = (Job)o[2];
				BeanUtils.copyProperties(chatRoom, chatRoomBean);
				chatRoomBean.setChatRoomId(chatRoom.getChatRoomId());
				chatRoomBean.setJob(job);
				chatRoomBean.setChatRoomId(chatRoom.getChatRoomId());		
				chatRoomBean.setStudent(s);
				chatRoomBeans.add(chatRoomBean);
			}
		}
		else {
			Student student = this.studentRepository.findByStudentEmail(userProfile.getEmail());
			List<Object[]> query = this.chatRoomRepository.getAllChatRoomsForStudent(student.getStudentId());
			for(Object[] o :query) {
				ChatRoomBean chatRoomBean = new ChatRoomBean();
				ChatRoom chatRoom = (ChatRoom)o[0];
				Corporate c = (Corporate) o[1];
				Job job = (Job)o[2];
				chatRoomBean.setJob(job);
				chatRoomBean.setChatRoomId(chatRoomBean.getChatRoomId());
				chatRoomBean.setCorporateId(chatRoom.getCorporateId());
				chatRoomBean.setStudentId(chatRoom.getStudentId());
				chatRoomBean.setChatRoomId(chatRoom.getChatRoomId());
				chatRoomBean.setCorporate(c);
				chatRoomBeans.add(chatRoomBean);
			}
		}
		
		
		return chatRoomBeans;
		
	}

}
