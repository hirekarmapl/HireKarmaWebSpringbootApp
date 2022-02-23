package com.hirekarma.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hirekarma.beans.ProjectResponseBean;
import com.hirekarma.model.Project;

@Service
public interface ProjectService {
	public ProjectResponseBean addProject(Project project,String token) throws Exception;
	public List<ProjectResponseBean> getProjects(String token) throws Exception;
	public Boolean deleteById(int id,String token) throws Exception ;	
	public List<ProjectResponseBean> addprojects(List<Project> projects,String token) throws Exception;
}
