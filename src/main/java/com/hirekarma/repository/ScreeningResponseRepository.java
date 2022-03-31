package com.hirekarma.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hirekarma.model.ScreeningResponse;

@Repository("screeningResponseRepository")
public interface ScreeningResponseRepository extends JpaRepository<ScreeningResponse, Long>{

	@Query("select st,sr from ScreeningResponse sr "
			+ "left join ScreeningEntity st on st.screeningTableId = sr.screeningId "
			+ "left join ChatRoom cr on cr.jobApplyId = sr.jobApplyId "
			+ "where cr.chatRoomId =:chatRoomId order by sr.createdOn")
	List<Object[]> getAllScreeningResponsesAndScreeningTableByChatRoomId(@Param("chatRoomId") Long chatRoom);
}
