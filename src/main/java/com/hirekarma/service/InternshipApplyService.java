package com.hirekarma.service;

import org.json.simple.parser.ParseException;

import com.hirekarma.beans.InternshipApplyBean;

public interface InternshipApplyService {
	public InternshipApplyBean insert(InternshipApplyBean internshipApplyBean, String token) throws ParseException;
}
