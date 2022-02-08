package com.hirekarma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hirekarma.model.Message;

@Repository("messageRepository")
public interface MessageRepository extends JpaRepository<Message, Long>{

}
