package com.hirekarma.service;

import java.util.List;
import java.util.Map;

import org.json.simple.parser.ParseException;
import org.springframework.web.multipart.MultipartFile;

import com.hirekarma.beans.UniversityJobShareToStudentBean;
import com.hirekarma.beans.UserBean;
import com.hirekarma.model.Education;
import com.hirekarma.model.Experience;
import com.hirekarma.model.Skill;
import com.hirekarma.model.UserProfile;

public interface StudentService {
//	public Student insert(Student student);
//	public StudentBean checkLoginCredentials(String email,String password);
//	public StudentBean updateStudentProfile(StudentBean studentBean);
//	public StudentBean findStudentById(Long studentId);
	
	public UserProfile insert(UserProfile student);
	public UserBean updateStudentProfile(UserBean studentBean, String token) throws Exception;
	public UserBean findStudentById(Long studentId);
	public List<UserBean> getAllStudents();
	public List<UserBean> importStudentDataExcel(MultipartFile file);
	public UniversityJobShareToStudentBean studentJobResponse(UniversityJobShareToStudentBean jobBean,String token);
	public List<?> jobDetails(String token) throws ParseException;
	public UserProfile addSkill(int id,String token) throws Exception;
	public Boolean addSkills(List<Skill> skills,String token) throws Exception;
	public Boolean updateSkills(List<Skill> skillIds,String token) throws Exception;
	public UserProfile addAllSkillsToStudent(List<Skill> skills,String token) throws Exception;
	public List<Skill> getAllSkillsOfStudent(String token) throws Exception;
	public UserProfile addAllExperienceToStudent(List<Experience> experience,String token) throws Exception;
	public List<Experience> getAllExperiencesOfStudent(String token) throws Exception;
	List<Education> addAllEducationToStudent(List<Education> education, String token) throws Exception;
	public List<Education> getAllEducationsOfStudent(String token) throws Exception;
	public Map<String,Object> getAllJobApplicationsByStudent(Long studentId);
	
	
	
//	this is better verison of above function
	public UserProfile insert2(String email,String password,String name);
	public UserBean updateStudentProfile2(UserBean userBean, String token) throws Exception;
}
