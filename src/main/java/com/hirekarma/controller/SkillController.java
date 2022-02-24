package com.hirekarma.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hirekarma.beans.Response;
import com.hirekarma.beans.SkillBean;
import com.hirekarma.model.Skill;
import com.hirekarma.repository.SkillRespository;
import com.hirekarma.service.SkillService;
import com.nimbusds.jose.shaded.json.writer.BeansMapper.Bean;

import ch.qos.logback.core.joran.util.beans.BeanUtil;

@RestController
@CrossOrigin
@RequestMapping("/hirekarma/skill")
public class SkillController {

	@Autowired
	SkillService skillService;

	@Autowired
	SkillRespository skillRespository;

	@PreAuthorize("hasRole('student')")
	@PostMapping("/addSkill")
	public ResponseEntity<Response> addSkill(@RequestBody Skill skill) {
		try {
			Skill skillSaved = this.skillService.addSkill(skill);
			return new ResponseEntity<Response>(new Response("success", 201, "added succesfully", skillSaved, null),
					HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasRole('student')")
	@PostMapping("/addAllSkill")
	public ResponseEntity<Response> addAllSkill(@RequestBody List<Skill> skills) {
		System.out.println("inside adddallSkill");
		try {
			List<Skill> skillsSaved = this.skillRespository.saveAll(skills);
			return new ResponseEntity<Response>(new Response("success", 201, "added succesfully", skillsSaved, null),
					HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('student')")
	@GetMapping("/dummy")
	public Skill getdummy() {
		System.out.println("inside dumy");
		return new Skill();
	}

	@PreAuthorize("hasRole('student')")
	@GetMapping("")
	public ResponseEntity<?> getSkills() {
		try {
			List<Skill> skills = skillRespository.findAll();
//			return skills;
			return new ResponseEntity<Response>(new Response("success", 200, "", skills, null), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('student')")
	@GetMapping("/{id}")
	public ResponseEntity<Response> getSkillById(@PathVariable("id") int skillId) {
		try {

			Optional<Skill> skill = skillRespository.findById(skillId);
			if (!skill.isPresent()) {
				throw new Exception("user not found");
			}

			return new ResponseEntity<Response>(new Response("success", 200, "", skill.get(), null), HttpStatus.OK);

		} catch (Exception e) {

			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Response> getSkills(@PathVariable("id") int skillId) {
		try {
			this.skillRespository.deleteById(skillId);
			return new ResponseEntity<Response>(new Response("success", 200, "deleted succesfully", null, null),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	}
}
