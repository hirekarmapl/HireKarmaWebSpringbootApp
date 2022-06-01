package com.hirekarma.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.hirekarma.beans.ScreeningBean;
import com.hirekarma.beans.ScreeningEntityParentBean;
import com.hirekarma.model.Corporate;
import com.hirekarma.model.University;
@Service
public interface ScreeningEntityParentService {
//	create
	Map<String, Object> createByCorporate(String title, Corporate corporate);
	Map<String, Object> createByUniversity(String title, University university);
	Map<String,Object> create(String title);
	
//	find
	Map<String,Object> findAllByCorporate(Corporate corporate);
	Map<String,Object> findAllByUniversity(University university);
	Map<String,Object> findAll();
	
//	add questions
	Map<String,Object> addQuestions(ScreeningEntityParentBean screeningEntityParentBean) throws Exception;
	Map<String, Object> addQuestionsByCorporate(ScreeningEntityParentBean screeningEntityParentBean,
			Corporate corporate) throws Exception;
	Map<String, Object> addQuestionsByUniversity(ScreeningEntityParentBean screeningEntityParentBean,
			University university) throws Exception;
	
//	deleting question from specific screening
	Map<String,Object> deleteQuestions(ScreeningEntityParentBean screeningEntityParentBean)throws Exception;
	Map<String, Object> deleteQuestionsByCorporate(ScreeningEntityParentBean screeningEntityParentBean,
			Corporate corporate) throws Exception;
	Map<String, Object> deleteQuestionsByUniversity(ScreeningEntityParentBean screeningEntityParentBean,
			University university) throws Exception;
	
//	deleting screeening
	Map<String,Object> delete(ScreeningEntityParentBean screeningEntityParentBean) throws Exception;
	Map<String,Object> deleteByCorporate(ScreeningEntityParentBean screeningEntityParentBean,Corporate corporate) throws Exception;
	Map<String,Object> deleteByUniversity(ScreeningEntityParentBean screeningEntityParentBean,University university) throws Exception;
	
//	send to students
	Map<String,Object> sendToStudents(ScreeningEntityParentBean screeningEntityParentBean) throws Exception;
	
}
