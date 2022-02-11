package com.hirekarma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hirekarma.model.ChatRoom;

@Repository("chatRoomRepository")
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long>{

	@Query("select u from ChatRoom u where u.studentId = ?1")
	ChatRoom getChatRoomByStudentId(Long studentId);
	
	@Query("select u from ChatRoom u where u.corporateId = ?1")
	ChatRoom getChatRoomByCorporateId(Long corporateId);
}
