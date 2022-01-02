package com.hirekarma.service;

import com.hirekarma.beans.StudentProfessionalDetailsBean;

public interface StudentProfDetailsService {
	public StudentProfessionalDetailsBean addStudentProfessionalDetails(StudentProfessionalDetailsBean professionalDetailsBean);
	public StudentProfessionalDetailsBean getStudentProffesionalDetailsByUserId(Long userId);
}
