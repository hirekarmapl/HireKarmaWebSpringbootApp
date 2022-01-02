package com.hirekarma.beans;

import java.util.List;

public class StudentProfessionalDetailsBean {
	
	private List<StudentEducationDetailsBean> educationDetailsBeans;
	private List<StudentProfessionalDetailsBean> professionalDetailsBeans;
	
	public List<StudentEducationDetailsBean> getEducationDetailsBeans() {
		return educationDetailsBeans;
	}
	public void setEducationDetailsBeans(List<StudentEducationDetailsBean> educationDetailsBeans) {
		this.educationDetailsBeans = educationDetailsBeans;
	}
	public List<StudentProfessionalDetailsBean> getProfessionalDetailsBeans() {
		return professionalDetailsBeans;
	}
	public void setProfessionalDetailsBeans(List<StudentProfessionalDetailsBean> professionalDetailsBeans) {
		this.professionalDetailsBeans = professionalDetailsBeans;
	}
	
	@Override
	public String toString() {
		return "StudentProfessionalDetailsBean [educationDetailsBeans=" + educationDetailsBeans
				+ ", professionalDetailsBeans=" + professionalDetailsBeans + "]";
	}

}
