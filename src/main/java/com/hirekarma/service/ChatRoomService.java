package com.hirekarma.service;

import java.util.List;
import java.util.Map;

import com.hirekarma.beans.ChatRoomBean;
import com.hirekarma.beans.ScreeningResponseBean;
import com.hirekarma.model.Student;
import com.hirekarma.model.UserProfile;

public interface ChatRoomService {
	List<ChatRoomBean> getAllChatRooms(UserProfile userProfile);
	public Map<String, Object> sendMessage(ChatRoomBean chatRoomBean);
	public Map<String, Object> getMessagesByChatRoomId(Long chatRoomId);
	public void sendmessageToMultipleStudent(ChatRoomBean chatRoomBean) throws Exception;
	Map<String, Object> getMessagesAndScreeningQuestionByChatRoomId(Long chatRoomId);
	void studentResponseOnScreeningQuestions(List<ScreeningResponseBean> screeningResponseBeans)
			throws Exception;

}
