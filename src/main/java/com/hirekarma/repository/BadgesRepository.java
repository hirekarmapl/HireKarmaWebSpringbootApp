package com.hirekarma.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hirekarma.model.Badges;

@Repository("badgesRepository")
public interface BadgesRepository extends JpaRepository<Badges, Long> {

	@Query("select badgeId , badgeName  from Badges")
	List<Object[]> getBadgeDetails();

}
