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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirekarma.beans.ProjectResponseBean;
import com.hirekarma.beans.Response;
import com.hirekarma.model.Project;
import com.hirekarma.model.Skill;
import com.hirekarma.repository.ProjectRepository;
import com.hirekarma.service.ProjectService;

@RestController
@CrossOrigin
@RequestMapping("/hirekarma/")
public class ProjectController {

	@Autowired
	ProjectService projectService;
	
	@Autowired
	ProjectRepository projectRepository;
	
	@PreAuthorize("hasRole('student')")
	@PostMapping("/project/addProject")
	public ResponseEntity<Response> addProject(@RequestHeader(value = "Authorization") String token,@RequestBody Project project) {
		try {
			ProjectResponseBean projectSaved =  this.projectService.addProject(project, token);
			
			return new ResponseEntity<Response>(new Response("success", 201, "added succesfully", projectSaved, null),
					HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
		
	}

	@PreAuthorize("hasRole('student')")
	@PostMapping("/project/addProjects")
	public ResponseEntity<Response> addProjects(@RequestHeader(value = "Authorization") String token,@RequestBody List<Project> projects) {
		try {
			 List<ProjectResponseBean> projectResponses =  this.projectService.addprojects(projects, token);
			
			return new ResponseEntity<Response>(new Response("success", 201, "added succesfully", projectResponses,null ),
					HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	}
	@PreAuthorize("hasRole('student')")
	@GetMapping("/dummy")
	public Project getdummy() {
		return new Project();
	}

	@PreAuthorize("hasRole('student')")
	@GetMapping("/projects")
	public ResponseEntity<?> getProjects() {
		try {
			List<Project> projects = this.projectRepository.findAll();
//			return skills;
			return new ResponseEntity<Response>(new Response("success", 200, "", projects, null), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('student')")
	@GetMapping("/project/{id}")
	public ResponseEntity<Response> getProjectById(@PathVariable("id") int projectId) {
		try {

			Optional<Project> project = projectRepository.findById(projectId);
			if (!project.isPresent()) {
				throw new Exception("user not found");
			}

			return new ResponseEntity<Response>(new Response("success", 200, "", project.get(), null), HttpStatus.OK);

		} catch (Exception e) {

			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasRole('student')")
	@GetMapping("/user/project")
	public ResponseEntity<Response> getProjectsOfSpecificUser(@RequestHeader(value = "Authorization") String token) {
		try {
			List<ProjectResponseBean> projects = this.projectService.getProjects( token);		

			return new ResponseEntity<Response>(new Response("success", 200, "", null, projects), HttpStatus.OK);

		} catch (Exception e) {

			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	}

	

	@PreAuthorize("hasRole('student')")
	@DeleteMapping("/user/project/{id}")
	public ResponseEntity<Response> deleteProjectById(@RequestHeader(value = "Authorization") String token,@PathVariable("id") int projectId) {
		try {
			this.projectService.deleteById(projectId, token);
			return new ResponseEntity<Response>(new Response("success", 200, "deleted succesfully", null, null),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(new Response("error", HttpStatus.BAD_REQUEST, e.getMessage(), null, null),
					HttpStatus.BAD_REQUEST);
		}
	}
}
