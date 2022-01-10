package com.hirekarma.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.hirekarma.beans.UserBean;
import com.hirekarma.model.UserProfile;

public interface StudentService {
//	public Student insert(Student student);
//	public StudentBean checkLoginCredentials(String email,String password);
//	public StudentBean updateStudentProfile(StudentBean studentBean);
//	public StudentBean findStudentById(Long studentId);
	
	public UserProfile insert(UserProfile student);
	public UserBean updateStudentProfile(UserBean studentBean);
	public UserBean findStudentById(Long studentId);
	public List<UserBean> getAllStudents();
	public List<UserBean> importStudentDataExcel(MultipartFile file);
}
