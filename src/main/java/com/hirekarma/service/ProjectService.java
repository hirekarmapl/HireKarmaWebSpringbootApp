package com.hirekarma.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hirekarma.model.Project;

@Service
public interface ProjectService {
	public Project addProject(Project project,String token) throws Exception;
	public List<Project> getProjects(String token) throws Exception;
	public Boolean deleteById(int id,String token) throws Exception ;	
}
