package com.hirekarma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hirekarma.model.Achievement;
@Repository
public interface AchievementRepository extends JpaRepository<Achievement, Integer> {

}
