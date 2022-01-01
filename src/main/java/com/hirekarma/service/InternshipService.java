package com.hirekarma.service;

import java.util.List;

import com.hirekarma.beans.InternshipBean;

public interface InternshipService {
	public InternshipBean insert(InternshipBean internshipBean);
	public List<InternshipBean> findInternshipsByUserId(Long userId);
	public InternshipBean findInternshipById(Long internshipId);
	public List<InternshipBean> deleteInternshipById(Long internshipId);
	public InternshipBean updateInternshipById(InternshipBean internshipBean);
}
