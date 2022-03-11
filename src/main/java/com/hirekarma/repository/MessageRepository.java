package com.hirekarma.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hirekarma.model.Message;

@Repository("messageRepository")
public interface MessageRepository extends JpaRepository<Message, Long>{

	@Query("select u from Message u where u.chatRoomId = ?1 order by u.createdOn")
	List<Message> getMessagesByChatRoomId(Long chatRoomId);
	
}
