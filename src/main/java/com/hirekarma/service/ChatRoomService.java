package com.hirekarma.service;

import java.util.List;
import java.util.Map;

import com.hirekarma.beans.ChatRoomBean;
import com.hirekarma.model.UserProfile;

public interface ChatRoomService {
	List<ChatRoomBean> getAllChatRooms(UserProfile userProfile);
	public Map<String, Object> sendMessage(ChatRoomBean chatRoomBean);
	public Map<String, Object> getMessagesByChatRoomId(Long chatRoomId);
	public void sendmessageToMultipleStudent(ChatRoomBean chatRoomBean) throws Exception;

}
