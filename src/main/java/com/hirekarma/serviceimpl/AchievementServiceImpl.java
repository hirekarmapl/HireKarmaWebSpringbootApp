package com.hirekarma.serviceimpl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirekarma.model.Achievement;
import com.hirekarma.repository.AchievementRepository;
import com.hirekarma.service.AchievementService;
@Service("AchievementService")
public class AchievementServiceImpl implements AchievementService {

@Autowired
	AchievementRepository achievementRepository;
	@Override
	public Achievement updateAchievementByAdminById( Achievement achievement) throws Exception {
		System.out.println("Achievement delted all");
		this.achievementRepository.deleteAll(this.achievementRepository.findAll());
		return this.achievementRepository.save(achievement);
	}
	@Override
	public Achievement getAchievement() {
		List<Achievement> achievement = this.achievementRepository.findAll();
		return achievement.get(0);
	}

}
