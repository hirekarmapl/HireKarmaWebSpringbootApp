package com.hirekarma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hirekarma.model.Meet;
@Repository
public interface MeetRepository extends JpaRepository<Meet, Long> {

}
