package com.hirekarma.service;

import java.util.List;

import org.json.simple.parser.ParseException;

import com.hirekarma.beans.InternshipApplyBean;
import com.hirekarma.beans.InternshipApplyResponseBean;

public interface InternshipApplyService {
	public InternshipApplyBean insert(InternshipApplyBean internshipApplyBean, String token) throws ParseException;
	public List<InternshipApplyResponseBean> getAllInternshipsForAStudent(Long studentId);
}
