package com.hirekarma.service;

import java.util.List;

import com.hirekarma.beans.InternshipApplyResponseBean;
import com.hirekarma.beans.InternshipBean;
import com.hirekarma.model.Internship;

public interface InternshipService {
	public InternshipBean insert(InternshipBean internshipBean, String token) throws Exception;
	public List<InternshipBean> findInternshipsByUserId(String token);
	public InternshipBean findInternshipById(Long internshipId, String token);
	public List<InternshipBean> deleteInternshipById(Long internshipId, String token);
	public InternshipBean updateInternshipById(InternshipBean internshipBean, String token) throws Exception;
	public void activateInternship(String token,Long internshipId,boolean active) throws Exception;
	
}
