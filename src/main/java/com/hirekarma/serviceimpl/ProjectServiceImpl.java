package com.hirekarma.serviceimpl;

import java.util.List;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirekarma.model.Project;
import com.hirekarma.model.UserProfile;
import com.hirekarma.repository.ProjectRepository;
import com.hirekarma.repository.UserRepository;
import com.hirekarma.service.ProjectService;
import com.hirekarma.utilty.Validation;
@Service("ProjectService")
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	ProjectRepository projectRepository;
	
	@Autowired
	UserRepository userRepository;
	
	public Project addProject(Project project,String token) throws Exception {
		String email = Validation.validateToken(token);
		UserProfile user =  userRepository.findByEmail(email, "student");
		if(user==null)
		{
			throw new Exception("user not found");
		}
		project.setUserProfile(user);
		return projectRepository.save(project);
	}
	
	
	public List<Project> getProjects(String token) throws Exception {
		String email = Validation.validateToken(token);
		UserProfile userProfile = userRepository.findByEmail(email, "student");
		if(userProfile==null) {
			throw new Exception("user not found");
		}
		List<Project> projects =  userProfile.getProjects();
		return projects;
		
	}
	
	public Boolean deleteById(int id,String token) throws Exception {
		String email = Validation.validateToken(token);
		UserProfile userProfile = userRepository.findByEmail(email, "student");
		if(userProfile==null) {
			throw new Exception("user not found");
		}
		Project project = projectRepository.getById(id);
		if(project.getUserProfile().getUserId()!=userProfile.getUserId()) {
			throw new Exception("not authorized");
		}
		projectRepository.deleteById(id);
		return true;
	}


	
}
