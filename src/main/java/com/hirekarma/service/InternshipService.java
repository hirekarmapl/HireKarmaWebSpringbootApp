package com.hirekarma.service;

import java.util.List;

import com.hirekarma.beans.InternshipBean;

public interface InternshipService {
	public InternshipBean insert(InternshipBean internshipBean, String token);
	public List<InternshipBean> findInternshipsByUserId(String token);
	public InternshipBean findInternshipById(Long internshipId);
//	public List<InternshipBean> deleteInternshipById(Long internshipId);
	public InternshipBean updateInternshipById(InternshipBean internshipBean);
}
