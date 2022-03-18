package com.hirekarma.beans;

import java.io.Serializable;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.hirekarma.model.Corporate;
import com.hirekarma.model.Student;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChatRoomBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long studentId;
	private Long corporateId;
	private Long chatRoomId;
	private String txtMsg;
	private MultipartFile attachment;
	private String senderType;
	private String isSeen;
	private Corporate corporate;
	private Student student;
//	image to be display on get all chatroom
	private String recieverimageUrl;
	private List<Long> chatRoomIds;
	
}
