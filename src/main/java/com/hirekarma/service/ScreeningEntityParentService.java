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
	Map<String,Object> findAllByCorporate(Corporate corporate);
	Map<String,Object> findAllByUniversity(University university);
	Map<String,Object> findAll();
	Map<String,Object> addQuestions(ScreeningEntityParentBean screeningEntityParentBean) throws Exception;
	Map<String,Object> deleteQuestions(ScreeningEntityParentBean screeningEntityParentBean)throws Exception;
	Map<String,Object> create(String title);
	Map<String,Object> delete(ScreeningEntityParentBean screeningEntityParentBean) throws Exception;
	Map<String,Object> deleteByCorporate(ScreeningEntityParentBean screeningEntityParentBean,Corporate corporate) throws Exception;
	Map<String,Object> deleteByUniversity(ScreeningEntityParentBean screeningEntityParentBean,University university) throws Exception;
	Map<String, Object> addQuestionsByCorporate(ScreeningEntityParentBean screeningEntityParentBean,
			Corporate corporate) throws Exception;
	Map<String, Object> createByCorporate(String title, Corporate corporate);
	Map<String, Object> deleteQuestionsByCorporate(ScreeningEntityParentBean screeningEntityParentBean,
			Corporate corporate) throws Exception;
	Map<String, Object> addQuestionsByUniversity(ScreeningEntityParentBean screeningEntityParentBean,
			University university) throws Exception;
	Map<String, Object> deleteQuestionsByUniversity(ScreeningEntityParentBean screeningEntityParentBean,
			University university) throws Exception;
	Map<String, Object> createByUniversity(String title, University university);
	
	Map<String,Object> sendToStudents(ScreeningEntityParentBean screeningEntityParentBean) throws Exception;
	
}
