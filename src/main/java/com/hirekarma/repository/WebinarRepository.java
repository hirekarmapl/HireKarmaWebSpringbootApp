package com.hirekarma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hirekarma.model.Webinar;
@Repository
public interface WebinarRepository extends JpaRepository<Webinar, String> {

}
