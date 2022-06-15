package com.hirekarma.repository;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hirekarma.model.ChatRoom;

@Repository("chatRoomRepository")
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long>{

	@Query("select u from ChatRoom u where u.studentId = ?1")
	ChatRoom getChatRoomByStudentId(Long studentId);
	
	@Query("select u from ChatRoom u where u.corporateId = ?1")
	ChatRoom getChatRoomByCorporateId(Long corporateId);
	
	@Query("select cr, s ,j "
			+ "from ChatRoom cr "
			+ "inner join Student s on s.studentId = cr.studentId "
			+ "inner join JobApply ja on cr.jobApplyId=ja.jobApplyId "
			+ "inner join Job j on ja.jobId = j.jobId "
			+ "where cr.corporateId = :corporateId order by cr.updatedOn desc")
	List<Object[]> getAllChatRoomsForCorporate(@Param("corporateId") Long corporateId);
	
	@Query("select cr, c ,j "
			+ "from ChatRoom cr "
			+ "inner join Corporate c on c.corporateId = cr.corporateId "
			+ "inner join JobApply ja on cr.jobApplyId=ja.jobApplyId "
			+ "inner join Job j on ja.jobId = j.jobId "
			+ "where cr.studentId =:studentId order by cr.updatedOn desc")
	List<Object[]> getAllChatRoomsForStudent(@Param("studentId") Long studentId);
	
	@Modifying
	@Transactional
	@Query("update ChatRoom c set c.updatedOn = :localDateTime where c.chatRoomId in (:chatRoomId) ")
	void updateUpdatedOn(@Param("localDateTime")LocalDateTime localDateTime,@Param("chatRoomId")List<Long> chatRoomId);
	

}
