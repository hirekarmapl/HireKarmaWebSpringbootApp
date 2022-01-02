package com.hirekarma.beans;

import java.util.List;

public class StudentProfessionalDetailsBean {
	
	private List<StudentEducationDetailsBean> educationDetailsBeans;
	private List<StudentProfessionalExperienceBean> professionalExperienceBeans;
	
	public List<StudentEducationDetailsBean> getEducationDetailsBeans() {
		return educationDetailsBeans;
	}
	public void setEducationDetailsBeans(List<StudentEducationDetailsBean> educationDetailsBeans) {
		this.educationDetailsBeans = educationDetailsBeans;
	}
	public List<StudentProfessionalExperienceBean> getProfessionalExperienceBeans() {
		return professionalExperienceBeans;
	}
	public void setProfessionalExperienceBeans(List<StudentProfessionalExperienceBean> professionalExperienceBeans) {
		this.professionalExperienceBeans = professionalExperienceBeans;
	}
	
	@Override
	public String toString() {
		return "StudentProfessionalDetailsBean [educationDetailsBeans=" + educationDetailsBeans
				+ ", professionalExperienceBeans=" + professionalExperienceBeans + "]";
	}
}
