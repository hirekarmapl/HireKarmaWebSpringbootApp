package com.hirekarma.beans;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChatRoomBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long studentId;
	private Long corporateId;
	private String txtMsg;
	private MultipartFile attachment;
	private String senderType;
	private String isSeen;
	
}
