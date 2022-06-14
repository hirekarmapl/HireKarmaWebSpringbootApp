package com.hirekarma.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hirekarma.model.Corporate;
import com.hirekarma.model.Event;
import com.hirekarma.model.University;
@Repository
public interface EventRepository extends JpaRepository<Event,String> {

	List<Event> findByCorporate(Corporate corporate);
	
	List<Event> findByUniversity(University university);
	
	List<Event> findByUniversityIsNullAndCorporateIsNull();
}
