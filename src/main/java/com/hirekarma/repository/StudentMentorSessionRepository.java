package com.hirekarma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hirekarma.model.StudentMentorSession;
@Repository
public interface StudentMentorSessionRepository extends JpaRepository<StudentMentorSession, String> {

}
