package com.hirekarma.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hirekarma.model.Client;

public interface ClientRepository extends JpaRepository<Client, Integer> {

	Client findByName(String name);
}
