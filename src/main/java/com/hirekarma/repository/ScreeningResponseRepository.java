package com.hirekarma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hirekarma.model.ScreeningResponse;

@Repository("screeningResponseRepository")
public interface ScreeningResponseRepository extends JpaRepository<ScreeningResponse, Long>{

}
