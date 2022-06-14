package com.hirekarma.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.sun.istack.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "MESSAGE")
@Entity
@Data
@NoArgsConstructor
public class Message implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@NotNull
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "MESSAGE_ID")
	private Long messageId;
	

	@Column(name = "CHAT_ROOM_ID")
	private Long chatRoomId;
	
	@Column(name = "TXT_MESSAGE")
	private String txtMessage;
	
	private String attachmentUrl;
	
	@Column(name = "SENDER_TYPE")
	private String senderType;
	
	@Column(name = "IS_SEEN")
	private String isSeen;
	
	@OneToMany(mappedBy = "message")
	List<ScreeningResponse> screeningResponses;
	
	
	
	private LocalDateTime createdOn = LocalDateTime.now();

}
