package com.hirekarma.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hirekarma.beans.QuestionAndAnswerBean;
import com.hirekarma.model.Corporate;
import com.hirekarma.model.QuestionANdanswer;
@Repository("QuestionAndAnswerRepository")
public interface QuestionAndAnswerRepository extends JpaRepository<QuestionANdanswer, Long> {

	QuestionANdanswer findByuID(String getuId);

	List<QuestionANdanswer> findAllByuID(String qNA_id);

	int deleteByuID(String qNA_id);

	@Query("SELECT q FROM QuestionANdanswer q WHERE q.uID= :qNA_id AND (q.status is null or status!=:string)")
	QuestionANdanswer findByuIDAndStatusIsNullOrStatusNot(String qNA_id, String string);
	
	List<QuestionANdanswer> findByType(String type);
	
	@Query("select q from QuestionANdanswer q where (q.corporate = :corporate or q.corporate = NULL)")
	List<QuestionANdanswer> findQandAForCorporate(@Param("corporate") Corporate corporate);

}
