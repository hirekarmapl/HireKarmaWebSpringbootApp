package com.hirekarma.service;

import com.hirekarma.beans.StudentBean;
import com.hirekarma.model.Student;

public interface StudentService {
	public Student insert(Student student);
	public StudentBean checkLoginCredentials(String email,String password);
	public StudentBean updateStudentProfile(StudentBean studentBean);
	public StudentBean findStudentById(Long studentId);
}
