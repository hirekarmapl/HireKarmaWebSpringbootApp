package com.hirekarma.serviceimpl;

import java.beans.Beans;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.json.simple.parser.ParseException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirekarma.beans.ProjectResponseBean;
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
	
	public ProjectResponseBean addProject(Project project,String token) throws Exception {
		String email = Validation.validateToken(token);
		UserProfile user =  userRepository.findByEmail(email, "student");
		if(user==null)
		{
			throw new Exception("user not found");
		}
		project.setUserProfile(user);
		Project projectSaved =  projectRepository.save(project);
		ProjectResponseBean responseBean = new ProjectResponseBean();
		BeanUtils.copyProperties(projectSaved, responseBean);
		return responseBean;
	}
	public List<ProjectResponseBean> copyListProjectToProjectBeans(List<Project> projects) {
		List<ProjectResponseBean> responseBeans = new ArrayList<>();
		for(Project p : projects) {
			ProjectResponseBean pr = new ProjectResponseBean();
			BeanUtils.copyProperties(p, pr);
			responseBeans.add(pr);
		}
		return responseBeans;
	}
	public List<ProjectResponseBean> addprojects(List<Project> projects,String token) throws Exception{
		String email = Validation.validateToken(token);
		UserProfile user = userRepository.findUserByEmail(email);
		List<ProjectResponseBean> responseBeans = new ArrayList<>();
		for(Project p : projects) {
			ProjectResponseBean pr = new ProjectResponseBean();
			BeanUtils.copyProperties(p, pr);
			responseBeans.add(pr);
			p.setUserProfile(user);
		}
		this.projectRepository.saveAll(projects);
		
		List<Project> projectsSaved =  projectRepository.saveAll(projects);

		return responseBeans;
	}
	public List<ProjectResponseBean> getProjects(String token) throws Exception {
		String email = Validation.validateToken(token);
		UserProfile userProfile = userRepository.findByEmail(email, "student");
		if(userProfile==null) {
			throw new Exception("user not found");
		}
		List<Project> projects =  userProfile.getProjects();
		List<ProjectResponseBean> responseBeans= copyListProjectToProjectBeans(projects);
		System.out.println(responseBeans);
		return responseBeans;
		
	}
	
	public Boolean deleteById(int id,String token) throws Exception {
		String email = Validation.validateToken(token);
		UserProfile userProfile = userRepository.findByEmail(email, "student");
		if(userProfile==null) {
			throw new Exception("user not found");
		}
		Optional<Project> projectOptional = projectRepository.findById(id);
		if(projectOptional.isEmpty()) {
			throw new Exception("no such project found");
		}
		Project project = projectOptional.get();
		
		if(project.getUserProfile().getUserId()!=userProfile.getUserId()) {
			throw new Exception("not authorized");
		}
		projectRepository.deleteById(id);
		return true;
	}


	
}
