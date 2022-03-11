package com.hirekarma.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
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
	
	@Query("select cr, s "
			+ "from ChatRoom cr "
			+ "inner join Student s on s.studentId = cr.studentId "
			+ "where cr.corporateId = :corporateId order by cr.updatedOn")
	List<Object[]> getAllChatRoomsForCorporate(@Param("corporateId") Long corporateId);
	
	@Query("select cr, c "
			+ "from ChatRoom cr "
			+ "inner join Corporate c on c.corporateId = cr.corporateId "
			+ "where cr.studentId =:studentId order by cr.updatedOn")
	List<Object[]> getAllChatRoomsForStudent(@Param("studentId") Long studentId);
	

}
