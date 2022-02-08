package com.hirekarma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hirekarma.model.ChatRoom;

@Repository("chatRoomRepository")
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long>{

	@Query("select u.chatRoomId from ChatRoom u where u.studentId = ?1 and u.corporateId = ?2")
	Long getChatRoomFromRepo(Long studentId,Long corporateId);
}
