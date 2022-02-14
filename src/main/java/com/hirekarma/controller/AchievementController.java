package com.hirekarma.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirekarma.model.Achievement;
import com.hirekarma.service.AchievementService;

@RestController

@RequestMapping("/")
public class AchievementController {

	@Autowired
	AchievementService achievementService;
	@PreAuthorize("hasRole('admin')")
	@PutMapping("/hirekarma/admin/achievement")
	public Achievement updateClientByAdminById(@RequestBody Achievement achievement) {
		try {
			return this.achievementService.updateAchievementByAdminById( achievement);
		} catch (Exception e) {
			return null;
		}
	}
	@GetMapping("/achievement")
	public Achievement getAchievement() {
		return this.achievementService.getAchievement();
	}
}