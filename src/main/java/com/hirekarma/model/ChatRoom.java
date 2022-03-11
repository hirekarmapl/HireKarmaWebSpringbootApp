package com.hirekarma.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.sun.istack.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CHAT_ROOM")
@Data
@NoArgsConstructor
public class ChatRoom implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@NotNull
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CHAT_ROOM_ID")
	private Long chatRoomId;
	
	@Column(name = "STUDENT_ID")
	private Long studentId;
	
	@Column(name = "CORPORATE_ID")
	private Long corporateId;
	
	private Long jobApplyId;
	
	private Long internshipApplyId;
	
	@OneToMany
	@JoinColumn(updatable = false, insertable = false, name = "CHAT_ROOM_ID", referencedColumnName = "CHAT_ROOM_ID")
	private List<Message> messages;
	
	
	LocalDateTime createdOn = LocalDateTime.now();
	
	LocalDateTime updatedOn = LocalDateTime.now();

}
