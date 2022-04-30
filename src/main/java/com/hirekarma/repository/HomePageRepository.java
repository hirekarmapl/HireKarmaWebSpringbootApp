package com.hirekarma.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hirekarma.model.Education;
import com.hirekarma.model.HomePage;
@Repository
public interface HomePageRepository extends JpaRepository<HomePage, String>{

	HomePage findByUrl(String url);
	HomePage findByUrlAndXpath(String url,String xpath);
}
