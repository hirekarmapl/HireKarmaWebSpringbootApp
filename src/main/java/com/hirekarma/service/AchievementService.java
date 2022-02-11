package com.hirekarma.service;

import org.springframework.stereotype.Service;

import com.hirekarma.model.Achievement;
@Service
public interface AchievementService {

	Achievement updateAchievementByAdminById(Achievement achievement) throws Exception;
	 Achievement getAchievement();
}
