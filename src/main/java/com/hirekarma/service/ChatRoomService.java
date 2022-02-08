package com.hirekarma.service;

import java.util.Map;

import com.hirekarma.beans.ChatRoomBean;

public interface ChatRoomService {
	
	public Map<String, Object> sendMessage(ChatRoomBean chatRoomBean);

}
