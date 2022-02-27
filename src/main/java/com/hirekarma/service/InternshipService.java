package com.hirekarma.service;

import java.util.List;

import com.hirekarma.beans.InternshipApplyResponseBean;
import com.hirekarma.beans.InternshipBean;

public interface InternshipService {
	public InternshipBean insert(InternshipBean internshipBean, String token);
	public List<InternshipBean> findInternshipsByUserId(String token);
	public InternshipBean findInternshipById(Long internshipId, String token);
	public List<InternshipBean> deleteInternshipById(Long internshipId, String token);
	public InternshipBean updateInternshipById(InternshipBean internshipBean, String token);
	public void activateInternship(String token,Long internshipId,boolean active) throws Exception;
	
}
